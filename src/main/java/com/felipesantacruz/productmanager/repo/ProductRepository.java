package com.felipesantacruz.productmanager.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.felipesantacruz.productmanager.model.Category;
import com.felipesantacruz.productmanager.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>
{
	List<Product> findByCategory(Category c);
	
	Page<Product> findByNameContainsIgnoreCase(String name, Pageable pageable);
}
