package com.felipesantacruz.productmanager.user.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.felipesantacruz.productmanager.user.dto.CreateUserDto;
import com.felipesantacruz.productmanager.user.dto.GetUserDto;
import com.felipesantacruz.productmanager.user.dto.UserDtoConverter;
import com.felipesantacruz.productmanager.user.model.UserEntity;
import com.felipesantacruz.productmanager.user.service.UserEntityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController
{
	private final UserEntityService userEntityService;
	private final UserDtoConverter userDtoConverter;
	
	@PostMapping("")
	public ResponseEntity<GetUserDto> newUser(@Valid @RequestBody CreateUserDto newUserDto)
	{
		try
		{
			return ResponseEntity
					.status(CREATED)
					.body(userEntityService.newUser(newUserDto));			
		} catch (DataIntegrityViolationException ex) // If newUser already exist
		{
			throw new ResponseStatusException(BAD_REQUEST, newUserDto.getUsername().concat(" already exists."));
		}
	}
	
	@GetMapping("/me")
	public ResponseEntity<GetUserDto> me(@AuthenticationPrincipal UserEntity userEntity)
	{
		return ResponseEntity.ok(userDtoConverter.toGetUserDto(userEntity));
	}
}
