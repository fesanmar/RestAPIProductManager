package com.felipesantacruz.productmanager.service;

import com.felipesantacruz.productmanager.model.Order;
import com.felipesantacruz.productmanager.model.Product;
import com.felipesantacruz.productmanager.repo.OrderRepository;

public abstract class AbstractOrderService extends BaseService<Order, Long, OrderRepository>
{
	public abstract boolean anyOrderContainsProduct(Product p);
}
