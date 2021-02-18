package com.felipesantacruz.productmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.felipesantacruz.productmanager.dto.ReadOrderDTO;
import com.felipesantacruz.productmanager.dto.converter.OrderDtoConverter;
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

}
