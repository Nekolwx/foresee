package com.foresee.ftcsp.order.rest.ui.front;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.foresee.ftcsp.common.core.page.PageQueryParam;
import com.foresee.ftcsp.order.service.IOrdBillService;

/**
 * <pre>
 * 账单后台服务。
 * </pre>
 *
 * @author mowentao@foresee.com.cn
 * @date 2017年8月21日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
@RestController("/front/billService")
@RequestMapping("/front/billService")
public class OrdBillResource {
	
	@Resource
    private IOrdBillService billService;
	
	/**
     * 账单分页查询
     * @author mowentao@foresee.com.cn
     * @date 2017年8月21日
     * TODO。
     * @param param
     * @return Object
     */
    @RequestMapping(value = "queryBillPage", method = RequestMethod.POST)
    public Object queryBillPage(@RequestBody PageQueryParam param){
        return billService.queryBillList(param);
    }
}
