package com.felipesantacruz.productmanager.dto.validator;

import org.springframework.stereotype.Service;

import com.felipesantacruz.productmanager.dto.product.WriteProductDTO;
import com.felipesantacruz.productmanager.repo.CategoryRespository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WriteProductDTONotNullValidator implements WriteProductDTOValidator
{

	private final CategoryRespository categoryRepository;
	
	@Override
	public boolean isValid(WriteProductDTO dto)
	{
		return notBlank(dto.getName()) 
				&& categoryRepository.existsById(dto.getCategoryId());
	}

	private boolean notBlank(String str)
	{
		return str != null && !str.trim().isEmpty();
	}

}
