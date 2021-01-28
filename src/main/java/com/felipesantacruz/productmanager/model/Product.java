package com.felipesantacruz.productmanager.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Product
{
	@ApiModelProperty(value = "Product's ID", dataType = "long", example = "1", position = 1)
	@Id @GeneratedValue
	private Long id;

	@ApiModelProperty(value = "Product's name", dataType = "string", example = "Very fat book", position = 2)
	private String name;

	@ApiModelProperty(value = "Product's price", dataType = "float", example = "12.36", position = 3)
	private float price;
	
	@ApiModelProperty(value = "Product's image", dataType = "string[]", example = "[\"http://www.mydomain.com/files/12345-image.jpg\"]", position = 4)
	@ElementCollection
	private List<String> images;
	
	@ApiModelProperty(value = "Product's category", dataType = "Category", position = 5)
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
