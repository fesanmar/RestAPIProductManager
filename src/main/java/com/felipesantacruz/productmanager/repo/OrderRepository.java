package com.felipesantacruz.productmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felipesantacruz.productmanager.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> { }
