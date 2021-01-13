package com.felipesantacruz.productmanager.dto.validator;

import com.felipesantacruz.productmanager.dto.product.WriteProductDTO;

public interface WriteProductDTOValidator
{
	boolean isValid(WriteProductDTO dto);
}
