package com.orderManagement.orderManagement.dto;

import java.util.List;

public class OmStockMovementModel {

	private List<OmStockItemMovementModel> itemList;

	public List<OmStockItemMovementModel> getItemList() {
		return itemList;
	}

	public void setItemList(List<OmStockItemMovementModel> itemList) {
		this.itemList = itemList;
	}
	
	
}
