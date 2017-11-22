package com.hand.dto;

import java.util.List;

public class JsGridResult<T> {

	private List<T> data;

	private String itemsCount;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getItemsCount() {
		return itemsCount;
	}

	public void setItemsCount(String itemsCount) {
		this.itemsCount = itemsCount;
	}

}
