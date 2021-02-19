package com.felipesantacruz.productmanager.dto.validator;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.felipesantacruz.productmanager.dto.WriteOrderDto;

@Service
public class WirteOrderDtoValidator implements DTOValidator<WriteOrderDto>
{

	@Override
	public boolean isValid(WriteOrderDto dto)
	{
		return customerIsPresent(dto) && Arrays.stream(dto.getOrderRows())
											   .map(row -> row.getQuantity() > 0)
											   .reduce((acc, actual) -> acc && actual)
											   .orElse(false);
	}

	private boolean customerIsPresent(WriteOrderDto dto)
	{
		String customer = dto.getCustomer();
		return customer != null && !customer.isBlank();
	}

}
