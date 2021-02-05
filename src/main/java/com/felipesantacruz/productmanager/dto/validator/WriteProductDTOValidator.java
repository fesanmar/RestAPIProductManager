package com.felipesantacruz.productmanager.dto.validator;

import com.felipesantacruz.productmanager.dto.WriteProductDTO;

public interface WriteProductDTOValidator
{
	boolean isValid(WriteProductDTO dto);
}
