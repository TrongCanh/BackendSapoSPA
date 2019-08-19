package com.sapo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Rival {
	@Id
	private long id;
	private long itemid;
	private long shopid;
	private long opponent;	//shop
	private long rival;		//item
	private boolean auto;
	private double price;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getItemid() {
		return itemid;
	}
	public void setItemid(long itemid) {
		this.itemid = itemid;
	}
	public long getOpponent() {
		return opponent;
	}
	public void setOpponent(long opponent) {
		this.opponent = opponent;
	}
	public long getRival() {
		return rival;
	}
	public void setRival(long rival) {
		this.rival = rival;
	}
	public boolean isAuto() {
		return auto;
	}
	public void setAuto(boolean auto) {
		this.auto = auto;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getShopid() {
		return shopid;
	}
	public void setShopid(long shopid) {
		this.shopid = shopid;
	}
	
	public Rival() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Rival(long itemid, long shopid, long opponent, long rival) {
		super();
		this.itemid = itemid;
		this.shopid = shopid;
		this.opponent = opponent;
		this.rival = rival;
	}
	
}
