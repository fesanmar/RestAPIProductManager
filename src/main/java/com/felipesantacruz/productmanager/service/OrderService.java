package com.felipesantacruz.productmanager.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.felipesantacruz.productmanager.dto.EditOrderDto;
import com.felipesantacruz.productmanager.dto.ReadOrderDTO;
import com.felipesantacruz.productmanager.dto.WriteOrderDto;
import com.felipesantacruz.productmanager.dto.converter.OrderDtoConverter;
import com.felipesantacruz.productmanager.model.Order;
import com.felipesantacruz.productmanager.model.Product;
import com.felipesantacruz.productmanager.repo.OrderRowRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService extends AbstractOrderService
{
	private final OrderRowRepository orderRowRepository;
	private final OrderDtoConverter orderDtoConverter;

	@Override
	public boolean anyOrderContainsProduct(Product p)
	{
		return !orderRowRepository.findByProduct(p).isEmpty();
	}

	@Override
	public Page<ReadOrderDTO> findAllAsDto(Pageable p)
	{
		return findAll(p).map(orderDtoConverter::convertToDto);
	}

	@Override
	public Optional<ReadOrderDTO> findByIdAsDto(Long id)
	{
		return findById(id).map(this::convertToOptionalDto)
						   .orElse(Optional.empty());
	}

	private Optional<ReadOrderDTO> convertToOptionalDto(Order o)
	{
		return Optional.of(orderDtoConverter.convertToDto(o));
	}

	@Override
	public Optional<Order> save(WriteOrderDto dto)
	{
		return orderDtoConverter.convertFromDto(dto).map(this::save);
	}
	
	@Override
	public Optional<Order> edit(Long id, EditOrderDto dto)
	{
		return findById(id).map(order -> save(editOrderWithOther(order, dto)));
	}

	private Order editOrderWithOther(Order order, EditOrderDto dto)
	{
		order.setCustomer(dto.getCustomer());
		return order;
	}

}
