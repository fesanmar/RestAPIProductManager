package com.felipesantacruz.productmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felipesantacruz.productmanager.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>
{

}
