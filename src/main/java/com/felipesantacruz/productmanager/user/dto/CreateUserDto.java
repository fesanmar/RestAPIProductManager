package com.felipesantacruz.productmanager.user.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CreateUserDto
{
	@NotBlank(message = "Username shouldn't be blank")
	@Length(min = 3)
	private String username;
	private String avatar;
	@NotBlank(message = "Password shouldn't be blank")
	@Length(min = 8)
	private String password;
	private String password2;
}
