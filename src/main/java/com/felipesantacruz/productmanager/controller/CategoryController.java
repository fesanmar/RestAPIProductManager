package com.felipesantacruz.productmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipesantacruz.productmanager.dto.category.CategoryDTO;
import com.felipesantacruz.productmanager.dto.converter.CategoryDTOConverter;
import com.felipesantacruz.productmanager.error.CategoryNotFoundException;
import com.felipesantacruz.productmanager.error.DeleteNotCompleteForConstraintException;
import com.felipesantacruz.productmanager.error.WriteCategoryNotValidException;
import com.felipesantacruz.productmanager.model.Category;
import com.felipesantacruz.productmanager.repo.CategoryRespository;
import com.felipesantacruz.productmanager.repo.ProductRepository;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CategoryController
{
	private final CategoryRespository categoryRespository;
	private final ProductRepository productRepository;
	private final CategoryDTOConverter dtoConverter;

	@ApiOperation(value = "Get an all categorys's list", notes = "Provides a list with every category")
	@GetMapping("/category")
	public List<Category> fetchAll()
	{
		return categoryRespository.findAll();
	}

	@ApiOperation(value = "Get a category by its ID", notes = "Provides every category's detail by its ID")
	@GetMapping("/category/{id}")
	public Category findById(@PathVariable Long id)
	{
		return categoryRespository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
	}

	@ApiOperation(value = "Creates a new category", notes = "Creates a new category, stores it, and returns its details")
	@PostMapping("/category")
	public ResponseEntity<Category> create(@RequestBody CategoryDTO newCategory)
	{
		throwExceptionIfDtoIsNotValid(newCategory);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(categoryRespository.save(dtoConverter.convertFromDTO(newCategory)));
	}

	private void throwExceptionIfDtoIsNotValid(CategoryDTO newCategory)
	{
		if (newCategory.getName() == null)
			throw new WriteCategoryNotValidException();
	}

	@ApiOperation(value = "Updates a category's details", notes = "Edit the category whose ID is passed in the path")
	@PutMapping("/category/{id}")
	public Category edit(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO)
	{
		throwExceptionIfDtoIsNotValid(categoryDTO);
		return categoryRespository.findById(id).map(category ->
		{
			category.setName(categoryDTO.getName());
			return categoryRespository.save(category);
		}).orElseThrow(WriteCategoryNotValidException::new);
	}

	@ApiOperation(value = "Removes a category", notes = "Removes the category whose ID is passed in the path. This operation will not be completed if any product is using the category to be removed")
	@DeleteMapping("/category/{id}")
	public ResponseEntity<?> remove(@PathVariable Long id)
	{
		return categoryRespository.findById(id).map(category ->
		{
			if (!productRepository.findByCategory(category).isEmpty())
				throw new DeleteNotCompleteForConstraintException();
			categoryRespository.delete(category);
			return ResponseEntity.noContent().build();
		}).orElseThrow(() -> new CategoryNotFoundException(id));
	}
}
