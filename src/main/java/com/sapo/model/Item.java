package com.sapo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Item {
	@Id
	private Long itemid;
	@Column(nullable = true)
	private String[] images;
	@Column(nullable = true)
	private float price;
	@Column(nullable = true)
	private float rating_star;
	@Column(nullable = true)
	private int[] rating_count;
	@JsonIgnore
	@Transient
	@Column(nullable = true)
	private Item_rating item_rating;
	@Column(nullable = true)
	private int historical_sold;
	@Column(nullable = true)
	private float price_max;
	@Column(nullable = true)
	private float price_min;
	@Column(nullable = true)
	private String discount;
	@Column(nullable = true)
	private int stock;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "item")
	@JsonIgnore
	private Set<Category> categories = new HashSet<Category>();

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getRating_star() {
		return rating_star;
	}

	public void setRating_star(float rating_star) {
		this.rating_star = rating_star;
	}

	public int[] getRating_count() {
		return rating_count;
	}

	public void setRating_count(int[] rating_count) {
		this.rating_count = rating_count;
	}

	public Item_rating getItem_rating() {
		return item_rating;
	}

	public void setItem_rating(Item_rating item_rating) {
		this.item_rating = item_rating;
	}

	public int getHistorical_sold() {
		return historical_sold;
	}

	public void setHistorical_sold(int historical_sold) {
		this.historical_sold = historical_sold;
	}

	public float getPrice_max() {
		return price_max;
	}

	public void setPrice_max(float price_max) {
		this.price_max = price_max;
	}

	public float getPrice_min() {
		return price_min;
	}

	public void setPrice_min(float price_min) {
		this.price_min = price_min;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

}
