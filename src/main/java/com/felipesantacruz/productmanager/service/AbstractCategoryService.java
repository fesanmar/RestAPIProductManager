package com.felipesantacruz.productmanager.service;

import java.util.Optional;

import com.felipesantacruz.productmanager.dto.CategoryDTO;
import com.felipesantacruz.productmanager.error.exception.DeleteNotCompleteForConstraintException;
import com.felipesantacruz.productmanager.model.Category;
import com.felipesantacruz.productmanager.repo.CategoryRespository;

public abstract class AbstractCategoryService extends BaseService<Category, Long, CategoryRespository>
{
	public abstract Category createCategory(CategoryDTO newCategory);
	
	public abstract Optional<Category> editCategory(Long id, CategoryDTO edit);
	
	/**
	 * Finds a category by its <code>id</code> and removes it if possible.
	 * @param id
	 * @return an Optional with the removed category's dto, or an empty one if couldn't be found
	 * @throws DeleteNotCompleteForConstraintException if any product is using the category to be removed
	 */
	public abstract Optional<CategoryDTO> removeCategory(Long id);
}
