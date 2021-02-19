package com.felipesantacruz.productmanager.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.felipesantacruz.productmanager.dto.EditOrderDto;
import com.felipesantacruz.productmanager.dto.ReadOrderDTO;
import com.felipesantacruz.productmanager.dto.WriteOrderDto;
import com.felipesantacruz.productmanager.model.Order;
import com.felipesantacruz.productmanager.model.Product;
import com.felipesantacruz.productmanager.repo.OrderRepository;

public abstract class AbstractOrderService extends BaseService<Order, Long, OrderRepository>
{
	public abstract boolean anyOrderContainsProduct(Product p);
	
	public abstract Page<ReadOrderDTO> findAllAsDto(Pageable p);
	
	public abstract Optional<ReadOrderDTO> findByIdAsDto(Long id);
	
	public abstract Optional<Order> save(WriteOrderDto dto);

	public abstract Optional<Order> edit(Long id, EditOrderDto dto);
	
}
