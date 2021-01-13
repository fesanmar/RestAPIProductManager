package com.felipesantacruz.productmanager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felipesantacruz.productmanager.model.Category;
import com.felipesantacruz.productmanager.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>
{
	List<Product> findByCategory(Category c);
}
