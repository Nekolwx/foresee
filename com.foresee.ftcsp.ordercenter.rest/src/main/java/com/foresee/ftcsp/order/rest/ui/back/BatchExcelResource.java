package com.foresee.ftcsp.order.rest.ui.back;

import com.foresee.ftcsp.common.core.id.IdGenerator;
import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.common.core.util.Loggers;
import com.foresee.ftcsp.ftcspmvc.annotation.Var;
import com.foresee.ftcsp.order.auto.model.OrdExcelTemp;
import com.foresee.ftcsp.order.manual.restdto.ImportBatchDTO;
import com.foresee.ftcsp.order.rest.url.BatchExcel;
import com.foresee.ftcsp.order.service.IBatchExcelService;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <pre>
 * 解析excel导入数据请求控制器
 * </pre>
 *
 * @author liweixin@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *          修改后版本:     修改人：  修改日期:     修改内容:
 *              </pre>
 * @date 2017/11/21
 */

@RestController
public class BatchExcelResource {

    private final Logger logger = Loggers.make();

    @Autowired
    IdGenerator idGenerator;

    @Autowired
    private IBatchExcelService iBatchExcelService;

    @RequestMapping(value = BatchExcel.importBatch,method= RequestMethod.POST)
    public Object importBatch(@Var @Valid  ImportBatchDTO importBatchDTO){
        OrdExcelTemp ordExcelTemp = new OrdExcelTemp();
        Long excelId = idGenerator.getLong();
        ordExcelTemp.setExcelId(excelId);
        ordExcelTemp.setFileName(importBatchDTO.getFileName());
        ordExcelTemp.setFilePath(importBatchDTO.getFilePath());
        ordExcelTemp.setOperator(importBatchDTO.getUserId());
        ordExcelTemp.setImportTime(new Date());
        iBatchExcelService.insertExcelTemp(ordExcelTemp);
        logger.info("获取到订单Excel文件key{key},开始异步进行解析",importBatchDTO.getFileName());
        Thread parseT = new Thread(()->{
            iBatchExcelService.handleOrderFromExcel(importBatchDTO.getFilePath(),importBatchDTO.getFileName(),excelId,importBatchDTO.getTaskId());
        });
        parseT.start();
        return RestResponse.successMessage("收到文件key！");
    }

    @RequestMapping(value = BatchExcel.handleOrderTemp,method= RequestMethod.POST)
    public Object handleOrderTemp(@Var @NotNull Long excelId, @NotBlank String taskId){
        iBatchExcelService.validTempData(excelId);
        try {
            iBatchExcelService.handleDataByExcelId(excelId, taskId);
        } catch (Exception e) {
            return RestResponse.error("失败");
        }
        return RestResponse.successMessage("ok");
    }



}
