package com.foresee.ftcsp.order.rest.url;

/**
 * <pre>
 * excel导入请求url常量类
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
public interface BatchExcel {
    String importBatch = "/back/orderExcelService/import";

    String outImportBatch =  "/outter/orderExcelService/import";

    String handleOrderTemp = "/back/batchExcelService/handleOrderTemp";
}
