package com.felipesantacruz.productmanager.security.jwt;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.felipesantacruz.productmanager.security.jwt.model.JwtUserResponse;
import com.felipesantacruz.productmanager.security.jwt.model.LoginRequest;
import com.felipesantacruz.productmanager.user.dto.CreateUserDto;
import com.felipesantacruz.productmanager.user.dto.GetUserDto;
import com.felipesantacruz.productmanager.user.dto.UserDtoConverter;
import com.felipesantacruz.productmanager.user.model.UserEntity;
import com.felipesantacruz.productmanager.user.service.UserEntityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController
{
	private final AuthenticationManager authenticationManager;
	private final UserEntityService userEntityService;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserDtoConverter converter;
	
	@PostMapping("/new")
	public ResponseEntity<GetUserDto> newUser(@Valid @RequestBody CreateUserDto newUserDto)
	{
		try
		{
			UserEntity user = userEntityService.newUser(newUserDto);
			login(new LoginRequest(user.getUsername(), user.getPassword()));
			
			return ResponseEntity
					.status(CREATED)
					.body(converter.toGetUserDto(user));			
		} catch (DataIntegrityViolationException ex) // If newUser already exist
		{
			throw new ResponseStatusException(BAD_REQUEST, newUserDto.getUsername().concat(" already exists."));
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtUserResponse> login(@Valid @RequestBody LoginRequest loginRequest)
	{
		Authentication authentication = 
				authenticationManager
					.authenticate(
							new UsernamePasswordAuthenticationToken(
									loginRequest.getUsername(),
									loginRequest.getPassword()
									));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserEntity userEntity = (UserEntity) authentication.getPrincipal();
		String jwtToken = jwtTokenProvider.generateToken(authentication);
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body(converter.toJwtUserResponse(userEntity, jwtToken));
	}
	
	@GetMapping("/me")
	public ResponseEntity<GetUserDto> me(@AuthenticationPrincipal UserEntity userEntity)
	{
		return ResponseEntity.ok(converter.toGetUserDto(userEntity));
	}
}
