package com.sapo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Item {
	@Id
	private Long item_id;
	@Column(nullable = true)
	private Long itemid;
	@Column(nullable = true)
	private Long shopid;
	@Column(nullable = true)
	private String status;
	@Column(nullable = true)
	private String item_sku;
	@Column(nullable = true)
	private String name;
	@Column(nullable = true)
	private String description;
	@Column(nullable = true)
	private String[] images;
	@Column(nullable = true)
	private float price;
	@Column(nullable = true)
	private int stock;
	@Column(nullable = true)
	private int create_time;
	@Column(nullable = true)
	private int update_time;
	@Column(nullable = true)
	private float weight;
	@Column(nullable = true)
	private Long category_id;
	@Column(nullable = true)
	private float original_price;
//	@Column(nullable = true)
//	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "item")
//    @JsonIgnore
//	private Set<Variation> variations = new HashSet<Variation>();
	@Column(nullable = true)
    private float rating_star;
	@Column(nullable = true)
	private int cmt_count;
	@Column(nullable = true)
	private int sales;
	@Column(nullable = true)
	private int views;
	@Column(nullable = true)
	private int likes;
	@Column(nullable = true)
	private int days_to_ship;
	public Long getItem_id() {
		return item_id;
	}
	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}
	public Long getShopid() {
		return shopid;
	}
	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getItem_sku() {
		return item_sku;
	}
	public void setItem_sku(String item_sku) {
		this.item_sku = item_sku;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getCreate_time() {
		return create_time;
	}
	public void setCreate_time(int create_time) {
		this.create_time = create_time;
	}
	public int getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(int update_time) {
		this.update_time = update_time;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public Long getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}
	public float getOriginal_price() {
		return original_price;
	}
	public void setOriginal_price(float original_price) {
		this.original_price = original_price;
	}
//	public Set<Variation> getVariations() {
//		return variations;
//	}
//	public void setVariations(Set<Variation> variations) {
//		this.variations = variations;
//	}
	public float getRating_star() {
		return rating_star;
	}
	public void setRating_star(float rating_star) {
		this.rating_star = rating_star;
	}
	public int getCmt_count() {
		return cmt_count;
	}
	public void setCmt_count(int cmt_count) {
		this.cmt_count = cmt_count;
	}
	public int getSales() {
		return sales;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getDays_to_ship() {
		return days_to_ship;
	}
	public void setDays_to_ship(int days_to_ship) {
		this.days_to_ship = days_to_ship;
	}
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
		
}
