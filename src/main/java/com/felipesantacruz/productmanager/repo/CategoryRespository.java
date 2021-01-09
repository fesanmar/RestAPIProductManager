package com.felipesantacruz.productmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felipesantacruz.productmanager.model.Category;

public interface CategoryRespository extends JpaRepository<Category, Long>
{

}
