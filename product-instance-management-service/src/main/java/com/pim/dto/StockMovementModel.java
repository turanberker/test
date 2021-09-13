package com.pim.dto;

import java.util.List;

public class StockMovementModel {

	private List<StockItemMovementModel> itemList;

	public List<StockItemMovementModel> getItemList() {
		return itemList;
	}

	public void setItemList(List<StockItemMovementModel> itemList) {
		this.itemList = itemList;
	}
	
	
}
