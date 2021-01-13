package com.felipesantacruz.productmanager.dto.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.felipesantacruz.productmanager.dto.category.CategoryDTO;
import com.felipesantacruz.productmanager.model.Category;

import lombok.RequiredArgsConstructor;

@Component @RequiredArgsConstructor
public class CategoryDTOConverter
{
	private final ModelMapper modelMapper;
	
	public CategoryDTO convertToDTO(Category c)
	{
		return modelMapper.map(c, CategoryDTO.class);
	}
	
	public Category convertFromDTO(CategoryDTO dto)
	{
		Category c = new Category();
		c.setName(dto.getName());
		return c;
	}
}
