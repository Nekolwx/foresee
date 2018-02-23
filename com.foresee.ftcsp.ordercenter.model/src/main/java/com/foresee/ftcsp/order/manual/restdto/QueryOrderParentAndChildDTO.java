/**
 * Copyright(c) Foresee Science & Technology Ltd.
 */
package com.foresee.ftcsp.order.manual.restdto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author weichunlei@foresee.com.cn
 * @date 2017年8月21日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class QueryOrderParentAndChildDTO {
	
	@NotNull(message = "订单Id不能为空")
	private Long orderId; //必须  订单Id

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

}
