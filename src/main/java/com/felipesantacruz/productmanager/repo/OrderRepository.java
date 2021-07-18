package com.felipesantacruz.productmanager.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.felipesantacruz.productmanager.model.Order;
import com.felipesantacruz.productmanager.user.model.UserEntity;

public interface OrderRepository extends JpaRepository<Order, Long> 
{
	Page<Order> findByCustomer(UserEntity userEntity, Pageable pageable);
}
