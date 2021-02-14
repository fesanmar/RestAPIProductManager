package com.felipesantacruz.productmanager.service;

import org.springframework.stereotype.Service;

import com.felipesantacruz.productmanager.model.Product;
import com.felipesantacruz.productmanager.repo.OrderRowRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService extends AbstractOrderService
{
	private final OrderRowRepository orderRowRepository;

	@Override
	public boolean anyOrderContainsProduct(Product p)
	{
		return !orderRowRepository.findByProduct(p).isEmpty();
	}

}
