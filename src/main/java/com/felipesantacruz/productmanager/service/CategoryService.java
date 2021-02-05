package com.felipesantacruz.productmanager.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.felipesantacruz.productmanager.dto.CategoryDTO;
import com.felipesantacruz.productmanager.dto.converter.CategoryDTOConverter;
import com.felipesantacruz.productmanager.error.DeleteNotCompleteForConstraintException;
import com.felipesantacruz.productmanager.model.Category;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService extends AbstractCategoryService
{
	private final CategoryDTOConverter dtoConverter;
	private final ProductService productService;

	@Override
	public Category createCategory(CategoryDTO newCategory)
	{
		return save(dtoConverter.convertFromDTO(newCategory));
	}

	@Override
	public Optional<Category> editCategory(Long id, CategoryDTO edit)
	{
		return findById(id).map(category ->
		{
			category.setName(edit.getName());
			return Optional.of(save(category));
		}).orElse(Optional.empty());
	}

	@Override
	public Optional<CategoryDTO> removeCategory(Long id)
	{
		return findById(id).map(category ->
		{
			if (productService.isCategoryBeingUsed(category))
				throw new DeleteNotCompleteForConstraintException();
			CategoryDTO dto = dtoConverter.convertToDTO(category);
			delete(category);
			return Optional.of(dto);
		}).orElse(Optional.empty());
	}

}
