package com.foresee.ftcsp.order.rest.ui.back;

import com.foresee.ftcsp.common.core.rest.RestResponse;
import com.foresee.ftcsp.order.rest.BaseTest;
import com.foresee.ftcsp.order.rest.url.BatchExcel;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
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
public class BatchExcelResourceTest extends BaseTest {

    @Test
    public void importBatch() throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("fileKey", "111");
        map.put("fileName", "222");
        ResponseEntity<RestResponse> responseEntity =
                testRestTemplate.postForEntity(BatchExcel.importBatch, map, RestResponse.class);
        Object body = responseEntity.getBody().getBody();
        System.out.println(body);
    }

}
