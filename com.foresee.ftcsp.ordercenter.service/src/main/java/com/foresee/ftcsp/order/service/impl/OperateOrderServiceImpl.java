package com.foresee.ftcsp.order.service.impl;

import com.foresee.ftcsp.common.core.dict.FtcspDictHolder;
import com.foresee.ftcsp.common.core.id.IdGenerator;
import com.foresee.ftcsp.order.auto.dao.OrdOrderMapper;
import com.foresee.ftcsp.order.auto.dao.OrdPayFlowingMapper;
import com.foresee.ftcsp.order.auto.dao.OrdPayFlowingRefMapper;
import com.foresee.ftcsp.order.auto.model.OrdOrder;
import com.foresee.ftcsp.order.auto.model.OrdPayFlowing;
import com.foresee.ftcsp.order.auto.model.OrdPayFlowingRef;
import com.foresee.ftcsp.order.bill.service.IBillAssistService;
import com.foresee.ftcsp.order.common.SignDTO;
import com.foresee.ftcsp.order.constant.BillConstant;
import com.foresee.ftcsp.order.constant.OrderConstant;
import com.foresee.ftcsp.order.constant.PayConstant;
import com.foresee.ftcsp.order.manual.dto.OrdBillDTO;
import com.foresee.ftcsp.order.manual.dto.OrdOrderDTO;
import com.foresee.ftcsp.order.manual.dto.OrdOrderStatusDTO;
import com.foresee.ftcsp.order.manual.dto.response.CommonResPonseDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryOrderDTO;
import com.foresee.ftcsp.order.service.*;
import com.foresee.ftcsp.order.util.CommonUtil;
import com.foresee.ftcsp.ordercenter.api.dto.UpdateOrderByDeductionsSuccess;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
/**
 * Created by kshijun on 2018-01-31.
 */
@Service
public class OperateOrderServiceImpl implements IOperateOrderService{

    @Resource
    private FtcspDictHolder ftcspDictHolder;

    @Resource
    private IOrderService orderService;

    @Autowired
    private IdGenerator idGenerator;

    @Resource
    private IOrdBillService billService;

    @Resource
    private OrdPayFlowingMapper ordPayFlowingMapper;

    @Resource
    private OrdPayFlowingRefMapper ordPayFlowingRefMapper;

    @Resource
    private OrdOrderMapper ordOrderMapper;

    @Resource
    private IObjectAssemblyService objectAssemblyService;

    @Resource
    private IMessageProducerService messageProducerService;

    @Resource
    private IBillAssistService billAssistService;

    @Override
    @Transactional
    public CommonResPonseDTO updateOrderByDeductions(UpdateOrderByDeductionsSuccess updateOrderInfo) {
        CommonResPonseDTO cmmonResPonse = new CommonResPonseDTO();

        //校验入参
        if(!validateParam(updateOrderInfo)){
            cmmonResPonse.setFlag(false);
            cmmonResPonse.setMessage("参数[orderId]与参数[businessOrderNo]不能同时为空");
            return cmmonResPonse;
        }

        //校验签名
        SignDTO signDto = new SignDTO();
        signDto.setSign(updateOrderInfo.getSign());
        signDto.setPayTime(updateOrderInfo.getPayTime());
        signDto.setUpdateUser(updateOrderInfo.getUpdateUser());
        if(!CommonUtil.ifValidParam(signDto,ftcspDictHolder.getDictItemValue("order_sign","ordercenterSign"))){
            cmmonResPonse.setFlag(false);
            cmmonResPonse.setMessage("签名校验失败");
            return cmmonResPonse;
        }

        // 更新状态、订单金额
        this.updateOrder(updateOrderInfo);

        // 产生流水
        this.createPayFlowing(updateOrderInfo);

        // 扣费成功
        if(updateOrderInfo.getPayStatus() == OrderConstant.PAY_STATUS_PAID){
            // 产生账单
            this.createBill(updateOrderInfo);
            // 通知订单可用
            messageProducerService.sendOrderOperateData(OrderConstant.ORDER_TYPE_ENABLED,updateOrderInfo.getOrderId());
        }else{
            // 通知订单不可用
            messageProducerService.sendOrderOperateData(OrderConstant.ORDER_TYPE_DISABLED,updateOrderInfo.getOrderId());
        }

        cmmonResPonse.setFlag(true);
        cmmonResPonse.setMessage("更新成功");
        return cmmonResPonse;
    }

    /**
     * 校验参数
     * @param updateOrderInfo
     * @return
     */
    public boolean validateParam(UpdateOrderByDeductionsSuccess updateOrderInfo){
        if(updateOrderInfo.getOrderId() == null || updateOrderInfo.getOrderId() == 0L){
            if(StringUtil.isBlank(updateOrderInfo.getBusinessOrderNo())){
                return false;
            }
            if(updateOrderInfo.getChannel() == null){
                return false;
            }
            QueryOrderDTO queryOrder = new QueryOrderDTO();
            queryOrder.setBusinessOrderNo(updateOrderInfo.getBusinessOrderNo());
            queryOrder.setChannel(Integer.toString(updateOrderInfo.getChannel()));
            OrdOrderDTO order = orderService.queryOrderByBusinessNoAndChannel(queryOrder);
            updateOrderInfo.setOrderId(order.getOrderId());
            updateOrderInfo.setPayAmount(order.getPayAmount());
            return true;
        }else{
            OrdOrder order = ordOrderMapper.selectByPrimaryKey(updateOrderInfo.getOrderId());
            updateOrderInfo.setBusinessOrderNo(order.getBusinessOrderNo());
            updateOrderInfo.setChannel(order.getChannel());
            updateOrderInfo.setPayAmount(order.getPayAmount());
            return true;
        }
    }

    /**
     * 更新订单状态、服务状态、支付状态、订单价钱
     * @param updateOrderInfo
     */
    private void updateOrder(UpdateOrderByDeductionsSuccess updateOrderInfo){
        if(updateOrderInfo.getPayStatus() == OrderConstant.PAY_STATUS_PAID){
            OrdOrderStatusDTO orderStatus = new OrdOrderStatusDTO();
            orderStatus.setOrderStatus(OrderConstant.ORDER_STATUS_SERVICING);
            orderStatus.setPayStatus(OrderConstant.PAY_STATUS_PAID);
            orderStatus.setServiceStatus(OrderConstant.SERVICE_STATUS_SERVICING);
            orderStatus.setPayAmount(updateOrderInfo.getPayAmount().add(updateOrderInfo.getCurrentPayAmount()));
            orderService.updateOrderSatusByOrdSatus(orderStatus,updateOrderInfo.getOrderId());
        }else{
            OrdOrderStatusDTO orderStatus = new OrdOrderStatusDTO();
            orderStatus.setOrderStatus(OrderConstant.ORDER_STATUS_STOP);
            orderStatus.setPayStatus(OrderConstant.PAY_STATUS_FAIL);
            orderStatus.setServiceStatus(OrderConstant.SERVICE_STATUS_STOP);
            orderService.updateOrderSatusByOrdSatus(orderStatus,updateOrderInfo.getOrderId());
        }

    }

    /**
     * 封装创建账单
     * @param updateOrderInfo
     */
    private void createBill(UpdateOrderByDeductionsSuccess updateOrderInfo){
        // 账单创建
        OrdBillDTO billDTO = new OrdBillDTO();
        billDTO.setBillId(idGenerator.getLong());
        billDTO.setPayId(updateOrderInfo.getPayId());
        billDTO.setOrderId(updateOrderInfo.getOrderId());
        billDTO.setBillType(BillConstant.BILL_TYPE_VOLUME);
        billDTO.setPayStatus(BillConstant.BILL_PAY_STATUS_ALREADY);
        billDTO.setBillAmount(updateOrderInfo.getCurrentPayAmount());
        Date payTime = new Date(Long.valueOf(updateOrderInfo.getPayTime()));
        billDTO.setPayTime(payTime);
        billAssistService.setBillTime(billDTO,updateOrderInfo.getChannel());
        //this.setBillTime(billDTO,updateOrderInfo);
        billService.createBill(billDTO);

        // 账单项创建
    }

    /**
     * 封装流水跟绑定流水与订单关系
     * @param updateOrderInfo
     */
    private void createPayFlowing(UpdateOrderByDeductionsSuccess updateOrderInfo){
        // 支付流水创建
        OrdPayFlowing payFlowing = new OrdPayFlowing();
        Long payId = idGenerator.getLong();
        updateOrderInfo.setPayId(payId);
        payFlowing.setPayId(payId);
        payFlowing.setPayNo(objectAssemblyService.generatePayNo());
        payFlowing.setThreePartyNo(updateOrderInfo.getThreePartyNo());
        if(updateOrderInfo.getPayStatus() == OrderConstant.PAY_STATUS_PAID){
            payFlowing.setPayState(PayConstant.FLOWING_PAY_STATUS_SUCCESS);
        }else{
            payFlowing.setPayState(PayConstant.FLOWING_PAY_STATUS_FAIL);
        }
        if(StringUtil.isBlank(updateOrderInfo.getPayAppId())){
            String appId = ftcspDictHolder.getDictItemValue("pay_appId_channel", updateOrderInfo.getChannel()+"");
            payFlowing.setPayAppId(appId);
        }else{
            payFlowing.setPayAppId(updateOrderInfo.getPayAppId());
        }
        payFlowing.setPayer(updateOrderInfo.getPayer());
        payFlowing.setPayWay(updateOrderInfo.getPayWay());
        payFlowing.setCreateUser(updateOrderInfo.getUpdateUser());
        payFlowing.setReceiptSide(updateOrderInfo.getReceiptSide());
        payFlowing.setPayerAccount(updateOrderInfo.getPayerAccount());
        payFlowing.setPayAmount(updateOrderInfo.getCurrentPayAmount());
        payFlowing.setReceiptAccount(updateOrderInfo.getReceiptAccount());
        Date payTime = new Date(Long.valueOf(updateOrderInfo.getPayTime()));
        payFlowing.setPayTime(payTime);
        ordPayFlowingMapper.insertSelective(payFlowing);

        // 关系绑定
        OrdPayFlowingRef payFlowingRef = new OrdPayFlowingRef();
        payFlowingRef.setPayId(payId);
        payFlowingRef.setOrderPayId(idGenerator.getLong());
        payFlowingRef.setOrderId(updateOrderInfo.getOrderId());
        payFlowingRef.setCreateUser(updateOrderInfo.getUpdateUser());
        ordPayFlowingRefMapper.insertSelective(payFlowingRef);
    }

    /**
     * 设置账单时间
     * @param billDTO
     * @param updateOrderInfo
     */
/*    private void setBillTime(OrdBillDTO billDTO,UpdateOrderByDeductionsSuccess updateOrderInfo){
        switch(updateOrderInfo.getChannel()){
            case 12: // 金财管家代账版,需要把账单时间设置到上一个月的第一天跟最后一天
                // 设置开始时间为上个月1号
                Calendar billStartTime=Calendar.getInstance();
                billStartTime.add(Calendar.MONTH, -1);
                billStartTime.set(Calendar.DAY_OF_MONTH,1);
                billDTO.setBillStartTime(billStartTime.getTime());
                // 设置结束时间为上个月最后一天
                Calendar billEndTime = Calendar.getInstance();
                billEndTime.set(Calendar.DAY_OF_MONTH,0);
                billDTO.setBillEndTime(billEndTime.getTime());
                // 设置为不需要开票
                billDTO.setInvoiceStatus(BillConstant.BILL_INVOICE_STATUS_NOT_NEED);
                break;
            default:
                // 设置为未开票，账单时间根据具体需求补充
                billDTO.setInvoiceStatus(BillConstant.BILL_INVOICE_STATUS_NOT_BLUE);
        }

    }*/
}
