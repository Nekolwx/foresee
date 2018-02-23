package com.foresee.ftcsp.order.service;

import com.foresee.ftcsp.order.auto.model.OrdExcelTemp;

import java.util.Map;

/**
 * <pre>
 * TODO
 * </pre>
 *
 * @author liweixin@foresee.com.cn
 * @version 1.00.00
 *          <p>
 *          <pre>
 *          修改记录
 *          修改后版本:     修改人：  修改日期:     修改内容:
 *              </pre>
 * @date 2017/11/22
 */
public interface IBatchExcelService {

    /**
     * 异步解析Excel
     * @param fileKey
     * @param fileName
     * @param excelId
     * @return
     */
    public boolean handleOrderFromExcel(String fileKey,String fileName,Long excelId,String taskId);

    /**
     * 收到解析的通知，往临时库插入改Excel文件的相关信息
     * @param ordExcelTemp
     * @return
     */
    public boolean insertExcelTemp(OrdExcelTemp ordExcelTemp);

    /**
     * 根据excel文件id处理已经插入的临时数据
     * @param excelId
     * @return
     */
    public Map handleDataByExcelId(Long excelId,String taskId) throws Exception;



    void validTempData(Long excelId);
}
