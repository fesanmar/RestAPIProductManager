package com.felipesantacruz.productmanager.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Product
{
	@Id @GeneratedValue
	private Long id;

	private String name;

	private float price;
	
	@ElementCollection
	private List<String> images;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	public void addImage(String image)
	{
		images.add(image);
	}
	
	public boolean hasImage(String image)
	{
		return images.contains(image);
	}
	
	public void removeImage(String image)
	{
		images.remove(image);
	}
}
