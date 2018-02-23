/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.service.impl;

import com.foresee.ftcsp.common.core.dict.FtcspDictHolder;
import com.foresee.ftcsp.common.core.exception.FtcspRuntimeException;
import com.foresee.ftcsp.common.core.model.Event;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.common.core.rest.client.FtcspRestClient;
import com.foresee.ftcsp.common.core.util.Jackson;
import com.foresee.ftcsp.goods.api.inner.GoodsApi;
import com.foresee.ftcsp.mq.message.impl.JobHandleMessage;
import com.foresee.ftcsp.mq.producer.IMessageProducer;
import com.foresee.ftcsp.order.auto.model.OrdOrder;
import com.foresee.ftcsp.order.constant.AccountConstant;
import com.foresee.ftcsp.order.constant.QueueConstants;
import com.foresee.ftcsp.order.constant.SignEncryptConstant;
import com.foresee.ftcsp.order.manual.dao.OrdOrderExtMapper;
import com.foresee.ftcsp.order.manual.restdto.BusinessHandleDataDTO;
import com.foresee.ftcsp.order.manual.restdto.GetPayParamDTO;
import com.foresee.ftcsp.order.manual.restdto.PreRechargeDTO;
import com.foresee.ftcsp.order.service.IMessageProducerService;
import com.foresee.ftcsp.order.util.PaySign;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * <pre>
 * 异步创建订单发送数据接口实现类。
 * </pre>
 *
 * @author huangzekun@foresee.com.cn
 * @date 2017年9月25日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@Service
@Transactional
public class MessageProducerServiceImpl implements IMessageProducerService {
    
    private Logger              LOGGER       = Logger.getLogger(this.getClass());
    
    @Autowired
    GoodsApi                    goodsApi;
    
    @Resource
    private OrdOrderExtMapper ordOrderExtMapper;
    
    @Autowired
    private IMessageProducer producer;
    
    @Resource
    private FtcspDictHolder     ftcspDictHolder;

    @Override
    public Object sendOrderData(GetPayParamDTO payParamDTO) {
        
        //根据skuId查询存不存在商品
        /*RestResponse<PdtGoodsSku> response = goodsApi.queryIfExistByGoodsSkuId(payParamDTO.getSkuId());
        if(response.getBody() == null || StringUtils.isEmpty(response.getBody().getGoodsId())){
            //如果不存在商品，直接返回，不再去异步创建订单
            LOGGER.info("不存在商品，不创建订单");
            return null;
        //goodsId为上述步骤查询出来的
        payParamDTO.setGoodsId(response.getBody().getGoodsId());
        }*/
        
        
        //查询是否已存在订单
        OrdOrder order = ordOrderExtMapper.selectByBusinessOrderNoAndChannel(payParamDTO.getPayBizOrderId(),
                Integer.parseInt(payParamDTO.getSource()));
        if (order != null){
            //如果查询已存在订单，直接返回，不再去异步创建订单
            LOGGER.info("订单已存在，不创建订单");
            return null;
        }
        JobHandleMessage<GetPayParamDTO> jobHandleMessage = new JobHandleMessage<GetPayParamDTO>();
        jobHandleMessage.setObject(payParamDTO);
        producer.send(QueueConstants.ORDER_CREATE_ORDER_EXCHANGE,QueueConstants.ORDER_CREATE_ORDER_QUEUE,jobHandleMessage);
        return null;
    }

    @Override
    public Object sendRechargeData(PreRechargeDTO preRechargeDTO) {
        
        Map<String, String> param = new HashMap<String, String>();
        
        String timeStamp = String.valueOf(new Date().getTime());
        
        
        param.put("version", "2.0.0");
        param.put("timeStamp",timeStamp);
        param.put("appId", AccountConstant.APPID);
        param.put("accountType", "1");
        param.put("ownerId", preRechargeDTO.getOwnerId());
        param.put("ownerType", preRechargeDTO.getOwnerType());
        param.put("currency", preRechargeDTO.getCurrency());
        param.put("outTradeNo", preRechargeDTO.getOutTradeNo());
        param.put("outPayNo", preRechargeDTO.getOutPayNo());
        param.put("rechargeWay", preRechargeDTO.getRechargeWay());
        param.put("certificate", preRechargeDTO.getCertificate());
        param.put("subject", preRechargeDTO.getSubject());
        param.put("amount", preRechargeDTO.getAmount());
        param.put("givenAmount", preRechargeDTO.getGivenAmount());
        param.put("rechargeType", preRechargeDTO.getRechargeType());
        param.put("rechargeDate", preRechargeDTO.getRechargeDate());
        param.put("partyAccountNo", preRechargeDTO.getPartyAccountNo());
        param.put("partyAccountName", preRechargeDTO.getPartyAccountName());
        param.put("remark", preRechargeDTO.getRemark());
        param.put("payeeAccountNo", preRechargeDTO.getPayeeAccountNo());
        
        String  signKey = SignEncryptConstant.RechargeCoinPaySalt;
        String sign = PaySign.genSign(param, signKey);
        param.put("sign", sign);
        
        JobHandleMessage<Map> jobHandleMessage = new JobHandleMessage<Map>();
        jobHandleMessage.setObject(param);
        producer.send(QueueConstants.ORDER_CREATE_ORDER_EXCHANGE,QueueConstants.PRE_RECHARGE_QUEUE,jobHandleMessage);
        return "充值请求消息已发送";
    }

    @Override
    public Object sendBusinessHandleData(BusinessHandleDataDTO dto, String msgId) {
        String producerAppId = ftcspDictHolder.getDictItemValue("sync_orderstatus", "appId");
        String producerAppDesKey = ftcspDictHolder.getDictItemValue("sync_orderstatus", "appDesKey");
        String producerAppSignKey = ftcspDictHolder.getDictItemValue("sync_orderstatus", "appSignKey");
        String url = ftcspDictHolder.getDictItemValue("sync_orderstatus", "url");
        String queueId = ftcspDictHolder.getDictItemValue("sync_orderstatus", "queueId");
        
        FtcspRestClient c = new FtcspRestClient(producerAppId, producerAppDesKey,producerAppSignKey);
        List<BusinessHandleDataDTO> list = new ArrayList<>();
        list.add(dto);
        Map<String,Object> map = new HashMap<>();
        map.put("goodsOrds",list);
        Map<String,Object> m = new HashMap<String, Object>();
        m.put("queueId", queueId);
        m.put("appId", "000101");
        m.put("msgId", msgId);
        m.put("body", map);
        c.post(url,Jackson.toJson(m) );
        LOGGER.debug(c.getResponseContent());
        RestResponse restResponse=Jackson.fromJson(c.getResponseContent(), RestResponse.class);
        if(restResponse.isError()){
            throw new FtcspRuntimeException("09030034", (Object)("写入队列事件失败："+restResponse.getHead().getErrorMsg()));
        }
        return restResponse;
    }

    @Override
    public Object sendOrderOperateData(String eventType,Long orderId) {
        String producerAppId = ftcspDictHolder.getDictItemValue("sync_orderstatus", "appId");
        String producerAppDesKey = ftcspDictHolder.getDictItemValue("sync_orderstatus", "appDesKey");
        String producerAppSignKey = ftcspDictHolder.getDictItemValue("sync_orderstatus", "appSignKey");
        String url = ftcspDictHolder.getDictItemValue("sync_orderstatus", "url");
        String queueId = ftcspDictHolder.getDictItemValue("sync_orderstatus", "OrderOperateQueueId");
        FtcspRestClient c = new FtcspRestClient(producerAppId, producerAppDesKey,producerAppSignKey);
        Event event = new Event();
        event.setEventType(eventType);
        event.setEntityName("order");
        event.setEntityId(orderId+"");
        Map<String,Object> m = new HashMap<>();
        m.put("queueId", queueId);
        m.put("appId", "000101");
        m.put("msgId", event.getEventId());
        m.put("body", event);
        c.post(url,Jackson.toJson(m) );
        LOGGER.debug(c.getResponseContent());
        RestResponse restResponse=Jackson.fromJson(c.getResponseContent(), RestResponse.class);
        if(restResponse.isError()){
            throw new FtcspRuntimeException("09030034", (Object)("写入队列事件失败："+restResponse.getHead().getErrorMsg()));
        }
        return restResponse;
    }
}
