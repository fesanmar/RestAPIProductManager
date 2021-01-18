package com.felipesantacruz.productmanager.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CategoryController
{
	private final CategoryRespository categoryRespository;
	private final ProductRepository productRepository;
	private final CategoryDTOConverter dtoConverter;

	@GetMapping("/category")
	public List<CategoryDTO> fetchAll()
	{
		return categoryRespository.findAll().stream().map(dtoConverter::convertToDTO).collect(Collectors.toList());
	}

	@GetMapping("/category/{id}")
	public Category findById(@PathVariable Long id)
	{
		return categoryRespository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
	}

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