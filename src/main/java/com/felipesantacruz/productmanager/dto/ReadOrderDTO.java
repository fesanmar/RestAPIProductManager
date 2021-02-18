package com.felipesantacruz.productmanager.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.felipesantacruz.productmanager.dto.view.OrderViews.Dto;
import com.felipesantacruz.productmanager.dto.view.OrderViews.DtoWithPriceImageCategory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class ReadOrderDTO
{
	@ApiModelProperty(value = "Orders's ID", dataType = "long", example = "1", position = 1)
	@JsonView(Dto.class)
	private long id;
	
	@ApiModelProperty(value = "Customer's name", dataType = "string", example = "John Doe", position = 2)
	@JsonView(Dto.class)
	private String customer;
	
	@ApiModelProperty(value = "Order's timestamp", dataType = "string", example = "2020/01/18 20:01:32", position = 3)
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	@JsonView(Dto.class)
	private LocalDateTime date;
	
	@ApiModelProperty(value = "Order rows", dataType = "ReadOrderRowDto[]" , position = 4)
	@JsonView(Dto.class)
	private ReadOrderRowDto[] rows;
	
	@ApiModelProperty(value = "Order total amount", dataType = "float" , position = 5)
	@JsonView(DtoWithPriceImageCategory.class)
	private float total;
	
}
