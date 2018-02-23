package com.foresee.ftcsp.order.manual.dto;

import java.io.Serializable;
import java.util.List;

public class OrdChildDTO implements Serializable {
	
	/*子订单基本信息*/
	 private OrdOrderDTO ordOrderChildTDO;
	 /*子订单商品信息List*/
    private List<OrdBillItemDTO> OrdBillItemDTOList;

	public OrdOrderDTO getOrdOrderChildTDO() {
		return ordOrderChildTDO;
	}

	public void setOrdOrderChildTDO(OrdOrderDTO ordOrderChildTDO) {
		this.ordOrderChildTDO = ordOrderChildTDO;
	}

	public List<OrdBillItemDTO> getOrdBillItemDTOList() {
		return OrdBillItemDTOList;
	}

	public void setOrdBillItemDTOList(List<OrdBillItemDTO> ordBillItemDTOList) {
		OrdBillItemDTOList = ordBillItemDTOList;
	}
    
    
    
}