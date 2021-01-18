package com.felipesantacruz.productmanager.dto.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.felipesantacruz.productmanager.dto.product.ProductDTO;
import com.felipesantacruz.productmanager.dto.product.WriteProductDTO;
import com.felipesantacruz.productmanager.model.Product;
import com.felipesantacruz.productmanager.repo.CategoryRespository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductDTOConverter
{
	private final ModelMapper modelMapper;
	private final CategoryRespository categoryRespository;
	
	public ProductDTO convertToDTO(Product p)
	{
		return modelMapper.map(p, ProductDTO.class);
	}
	
	public Product convertFromDTO(WriteProductDTO dto)
	{
		return Product.builder()
				.name(dto.getName())
				.price(dto.getPrice())
				.image(dto.getImage())
				.category(categoryRespository.findById(dto.getCategoryId()).orElse(null))
				.build();
	}
}
