package com.felipesantacruz.productmanager.security.jwt.model;

import java.util.Set;

import com.felipesantacruz.productmanager.user.dto.GetUserDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class JwtUserResponse extends GetUserDto
{
	private String token;
	
	@Builder(builderMethodName = "jwtUserResponseBuilder")
	public JwtUserResponse(String username, String avatar, String fullName, String email, Set<String> roles, String token)
	{
		super(username, avatar, fullName, email, roles);
		this.setToken(token);
	}
}
