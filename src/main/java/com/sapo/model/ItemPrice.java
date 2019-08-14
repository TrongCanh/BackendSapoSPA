package com.sapo.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class ItemPrice {
	@Id
	@GeneratedValue
	private Long id;
	@UpdateTimestamp
	private LocalDateTime updateDateTime;
	private float price;
	@ManyToOne
	@JoinColumn(name = "item_id")
	@JsonBackReference
	private Item item;
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}


}
