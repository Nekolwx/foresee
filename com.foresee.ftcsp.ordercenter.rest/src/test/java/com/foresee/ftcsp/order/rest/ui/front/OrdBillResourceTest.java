package com.foresee.ftcsp.order.rest.ui.front;

import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.order.manual.dto.OrdBillDTO;
import com.foresee.ftcsp.order.manual.restdto.QueryBillPageDTO;
import com.foresee.ftcsp.order.rest.BaseTest;
import org.junit.Test;
import org.springframework.boot.context.embedded.LocalServerPort;

/**
 * <pre>
 * TODO
 * </pre>
 *
 * @author huangzhao@foresee.com.cn
 * @version 1.00.00
 * @date 2017年08月14日
 *       <p>
 * 
 *       <pre>
 * 修改记录
 * 修改后版本: 修改人： 修改日期: 修改内容:
 *       </pre>
 */
public class OrdBillResourceTest extends BaseTest {

    @LocalServerPort
    private int port;


    @Test
    public void queryBillPage() throws Exception {
    	QueryBillPageDTO queryBillPageDTO = new QueryBillPageDTO();
    	/*queryBillPageDTO.setOrderCode("11");
    	queryBillPageDTO.setCusName("新会区会城妙手阁早餐店");
    	queryBillPageDTO.setTaxIdentificationNumber("44078219831130281701");
    	queryBillPageDTO.setInvoiceStatus(1);
    	queryBillPageDTO.setPayWay(1);
    	queryBillPageDTO.setGoodsSku("000002");*/
        RestResponse post = testRestTemplate.postForEntity("/back/billService/queryBillPage", queryBillPageDTO, RestResponse.class).getBody();
        System.out.println(post);
    }
    
    @Test
    public void queryBillDetail() throws Exception {
    	OrdBillDTO ordBillDTO = new OrdBillDTO();
    	ordBillDTO.setBillId(1L);
        RestResponse post = testRestTemplate.postForEntity("/back/billService/queryBillDetail", ordBillDTO, RestResponse.class).getBody();
        System.out.println(post);
    }
 

}