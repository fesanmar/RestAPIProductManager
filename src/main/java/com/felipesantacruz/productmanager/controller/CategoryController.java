package com.felipesantacruz.productmanager.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.felipesantacruz.productmanager.dto.CategoryDTO;
import com.felipesantacruz.productmanager.error.exception.CategoryNotFoundException;
import com.felipesantacruz.productmanager.error.exception.WriteCategoryNotValidException;
import com.felipesantacruz.productmanager.model.Category;
import com.felipesantacruz.productmanager.service.AbstractCategoryService;
import com.felipesantacruz.productmanager.util.pagination.PaginationLinksUtils;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CategoryController
{
	private final AbstractCategoryService categoryService;
	private final PaginationLinksUtils paginationLinksUtils;

	@ApiOperation(value = "Get an all categorys's list", notes = "Provides a list with every category")
	@GetMapping("/category")
	public ResponseEntity<Page<Category>> fetchAll(@PageableDefault Pageable pageable, HttpServletRequest request)
	{
		Page<Category> page = categoryService.findAll(pageable);
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
		String uris = paginationLinksUtils.createLinkHeader(page, uriBuilder);
		return ResponseEntity.ok().header("link", uris).body(page);
	}

	@ApiOperation(value = "Get a category by its ID", notes = "Provides every category's detail by its ID")
	@GetMapping("/category/{id}")
	public Category findById(@PathVariable Long id)
	{
		return categoryService.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
	}

	@ApiOperation(value = "Creates a new category", notes = "Creates a new category, stores it, and returns its details")
	@PostMapping("/category")
	public ResponseEntity<Category> create(@RequestBody CategoryDTO newCategory)
	{
		throwExceptionIfDtoIsNotValid(newCategory);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(categoryService.createCategory(newCategory));
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
		return categoryService.editCategory(id, categoryDTO)
				.orElseThrow(WriteCategoryNotValidException::new);
	}

	@ApiOperation(value = "Removes a category", notes = "Removes the category whose ID is passed in the path. This operation will not be completed if any product is using the category to be removed")
	@DeleteMapping("/category/{id}")
	public ResponseEntity<?> remove(@PathVariable Long id)
	{
		return categoryService.removeCategory(id)
				.map(deletedDto -> ResponseEntity.noContent().build())
				.orElseThrow(() -> new CategoryNotFoundException(id));
	}
}
