package com.felipesantacruz.productmanager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "order_row")
public class OrderRow
{
	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	private float price;
	private int quantity;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	public float getSubtotal()
	{
		return price * quantity;
	}
}
