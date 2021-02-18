package com.felipesantacruz.productmanager.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.felipesantacruz.productmanager.dto.view.OrderViews.Dto;
import com.felipesantacruz.productmanager.dto.view.OrderViews.DtoWithPriceImageCategory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReadOrderRowDto
{
	@ApiModelProperty(value = "Product's name", dataType = "string", example = "Very fat book", position = 1)
	@JsonView(Dto.class)
	private String productName;
	
	@ApiModelProperty(value = "Product's image", dataType = "string", example = "[\"http://www.mydomain.com/files/12345-image.jpg\"]", position = 2)
	@JsonView(DtoWithPriceImageCategory.class)
	private String productImage;
	
	@ApiModelProperty(value = "Product's category", dataType = "string", example = "Books", position = 3)
	@JsonView(DtoWithPriceImageCategory.class)
	private String productCategoryName;
	
	@ApiModelProperty(value = "Product's price", dataType = "float", example = "12.36", position = 4)
	@JsonView(DtoWithPriceImageCategory.class)
	private float price;
	
	@ApiModelProperty(value = "Product's quantity", dataType = "int", example = "2", position = 5)
	@JsonView(Dto.class)
	private int quantity;
	
	@ApiModelProperty(value = "Order row subtotal", dataType = "float", example = "24.72", position = 6)
	@JsonView(Dto.class)
	private float subTotal;
}
