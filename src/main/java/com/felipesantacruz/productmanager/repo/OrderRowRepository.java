package com.felipesantacruz.productmanager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felipesantacruz.productmanager.model.OrderRow;
import com.felipesantacruz.productmanager.model.Product;

public interface OrderRowRepository extends JpaRepository<OrderRow, Long>
{
	List<OrderRow> findByProduct(Product p);
}
