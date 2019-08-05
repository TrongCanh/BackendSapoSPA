package com.sapo.model;

import java.util.List;

public class ItemList {
	private List<Item> items;
	private Boolean more;
	private String request_id;
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public Boolean getMore() {
		return more;
	}
	public void setMore(Boolean more) {
		this.more = more;
	}
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	
}
