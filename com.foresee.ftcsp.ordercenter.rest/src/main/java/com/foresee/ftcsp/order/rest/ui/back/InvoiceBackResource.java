/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.foresee.ftcsp.order.rest.ui.back;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.foresee.ftcsp.common.core.page.PageQueryParam;

import com.foresee.ftcsp.order.service.IOrdBillService;
import com.foresee.ftcsp.order.service.IOrdInvoiceService;
import com.foresee.ftcsp.order.service.IOrderService;

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author chenzexin@foresee.com.cn
 * @date 2017年8月30日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@RestController("/back/orderService")
@RequestMapping("/back/orderService")
public class InvoiceBackResource {
    private Logger        LOGGER = Logger.getLogger(this.getClass());

    @Resource
    private IOrdInvoiceService ordInvoiceService;
    
    /**
     * 发票分页查询
     * @author chenzexin@foresee.com.cn
     * @date 2017年8月30日
     * @param param
     * @return Object
     */
    @RequestMapping(value="/queryOrdInvoiceList",method=RequestMethod.POST)
    public Object queryOrdInvoiceList(@RequestBody PageQueryParam param){
       return ordInvoiceService.queryOrdInvoiceList(param);
    }
  

}
