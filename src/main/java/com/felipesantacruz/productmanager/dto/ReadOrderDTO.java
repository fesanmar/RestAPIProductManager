package com.felipesantacruz.productmanager.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class ReadOrderDTO
{
	@ApiModelProperty(value = "Orders's ID", dataType = "long", example = "1", position = 1)
	private long id;
	
	@ApiModelProperty(value = "Customer's name", dataType = "string", example = "John Doe", position = 2)
	private String customer;
	
	@ApiModelProperty(value = "Order's timestamp", dataType = "string", example = "2020/01/18 20:01:32", position = 3)
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	private LocalDateTime date;
	
	@ApiModelProperty(value = "Order rows", dataType = "ReadOrderRowDto[]" , position = 4)
	private ReadOrderRowDto[] rows;
	
}
