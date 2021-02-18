package com.felipesantacruz.productmanager.dto.converter;

import java.util.Arrays;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.felipesantacruz.productmanager.dto.ReadOrderDTO;
import com.felipesantacruz.productmanager.dto.ReadOrderRowDto;
import com.felipesantacruz.productmanager.dto.WriteOrderDto;
import com.felipesantacruz.productmanager.dto.WriteOrderRowDto;
import com.felipesantacruz.productmanager.model.Order;
import com.felipesantacruz.productmanager.model.OrderRow;
import com.felipesantacruz.productmanager.service.ProductService;

import lombok.RequiredArgsConstructor;

@Component @RequiredArgsConstructor
public class OrderDtoConverter
{
	private final ModelMapper modelMapper;
	private final ProductService productService;
	
	public Optional<Order> convertFromDto(WriteOrderDto dto)
	{
		final Order order = Order.builder()
						   .customer(dto.getCustomer())
						   .build();
		Arrays.stream(dto.getOrderRows())
			  .map(OrderDtoConverter.this::convertFromDto)
			  .filter(Optional::isPresent)
			  .map(Optional::get)
			  .forEach(order::addOrderRow);
		if (hasSameRows(dto, order))
			return Optional.of(order);
		return Optional.empty();
	}

	private boolean hasSameRows(WriteOrderDto dto, Order order)
	{
		return order.getOrderRows().size() == dto.getOrderRows().length;
	}
	
	public Optional<OrderRow> convertFromDto(WriteOrderRowDto dto)
	{
		return productService.findById(dto.getProductId()).map(p -> Optional.of(OrderRow.builder()
																			.product(p)
																			.price(dto.getPrice())
																			.quantity(dto.getQuantity())
																			.build())
															   ).orElse(Optional.empty());
	}
	
	public ReadOrderDTO convertToDto(Order o)
	{
		ReadOrderRowDto[] orderRowsDto = getOrderRowsDto(o);
		return ReadOrderDTO.builder()
						   .id(o.getId())
						   .customer(o.getCustomer())
						   .date(o.getDate())
						   .rows(orderRowsDto)
						   .total((float) Arrays.stream(orderRowsDto).mapToDouble(ReadOrderRowDto::getSubTotal).sum())
						   .build();
	}

	private ReadOrderRowDto[] getOrderRowsDto(Order o)
	{
		return o.getOrderRows().stream()
				   				 .map(OrderDtoConverter.this::convertToDto)
				   				 .toArray(ReadOrderRowDto[]::new);
	}
	
	public ReadOrderRowDto convertToDto(OrderRow or)
	{
		return modelMapper.map(or, ReadOrderRowDto.class);
	}
}
