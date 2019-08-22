package com.sapo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AutoPrice {
	@Id
	@GeneratedValue
	private Long id;
	private Date date;
	private double price;
	private Long itemid;
	private Long rivalid;
	private double oldPrice;

	public double getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(double oldPrice) {
		this.oldPrice = oldPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public Long getRivalid() {
		return rivalid;
	}

	public void setRivalid(Long rivalid) {
		this.rivalid = rivalid;
	}

	public AutoPrice(Date date, double price,double oldPrice, Long itemid, Long rivalid) {
		super();
		this.date = date;
		this.oldPrice=oldPrice;
		this.price = price;
		this.itemid = itemid;
		this.rivalid = rivalid;
	}

	public AutoPrice() {
		super();
		// TODO Auto-generated constructor stub
	}

}
