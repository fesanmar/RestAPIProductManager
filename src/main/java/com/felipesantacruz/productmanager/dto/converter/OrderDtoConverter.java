package com.felipesantacruz.productmanager.dto.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.felipesantacruz.productmanager.dto.ReadOrderDTO;
import com.felipesantacruz.productmanager.dto.ReadOrderRowDto;
import com.felipesantacruz.productmanager.model.Order;
import com.felipesantacruz.productmanager.model.OrderRow;

import lombok.RequiredArgsConstructor;

@Component @RequiredArgsConstructor
public class OrderDtoConverter
{
	private final ModelMapper modelMapper;
	 
	
	public ReadOrderRowDto convertToDto(OrderRow or)
	{
		return modelMapper.map(or, ReadOrderRowDto.class);
	}
	
	public ReadOrderDTO convertToDto(Order o)
	{
		return ReadOrderDTO.builder()
						   .id(o.getId())
						   .customer(o.getCustomer())
						   .date(o.getDate())
						   .rows(o.getOrderRows().stream()
								   				 .map(OrderDtoConverter.this::convertToDto)
								   				 .toArray(ReadOrderRowDto[]::new))
						   .build();
	}
}
