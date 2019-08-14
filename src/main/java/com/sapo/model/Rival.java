package com.sapo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Rival {
	@Id
	@GeneratedValue
	private Long id;
	private Long rival;
	@ManyToOne
	@JoinColumn(name = "item_id")
	@JsonBackReference
	private Item item;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRival() {
		return rival;
	}

	public void setRival(Long rival) {
		this.rival = rival;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Rival() {
		super();
		// TODO Auto-generated constructor stub
	}

}
