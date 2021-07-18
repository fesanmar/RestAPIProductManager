package com.felipesantacruz.productmanager.user.service;

import static java.lang.String.format;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService
{
	private final UserEntityService userEntityService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		return userEntityService.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username.concat(" not found.")));
	}
	
	public UserDetails loadUserById(Long id) throws UsernameNotFoundException
	{
		return userEntityService.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException((format("Not found user with id: %d.", id))));
	}

}
