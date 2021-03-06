package com.felipesantacruz.productmanager.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;
import com.felipesantacruz.productmanager.dto.ReadOrderDTO;
import com.felipesantacruz.productmanager.dto.WriteOrderDto;
import com.felipesantacruz.productmanager.dto.validator.DTOValidator;
import com.felipesantacruz.productmanager.dto.view.OrderViews.Dto;
import com.felipesantacruz.productmanager.dto.view.OrderViews.DtoWithPriceImageCategory;
import com.felipesantacruz.productmanager.error.APIError;
import com.felipesantacruz.productmanager.error.exception.OrderNotFoundException;
import com.felipesantacruz.productmanager.error.exception.ProductNotFoundException;
import com.felipesantacruz.productmanager.error.exception.WriterOrderDTONotValidException;
import com.felipesantacruz.productmanager.model.Order;
import com.felipesantacruz.productmanager.service.OrderService;
import com.felipesantacruz.productmanager.user.model.UserEntity;
import com.felipesantacruz.productmanager.user.model.UserRole;
import com.felipesantacruz.productmanager.util.pagination.PaginationLinksUtils;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController
{
	private final OrderService orderService;
	private final PaginationLinksUtils paginationLinksUtils;
	private final DTOValidator<WriteOrderDto> wirteOrderDtoValidator;
	
	@ApiOperation(value = "Get an all orders's list.", notes = "Provides a list with every order ever.")
	@JsonView(DtoWithPriceImageCategory.class)
	@GetMapping("")
	public ResponseEntity<Page<ReadOrderDTO>> fetchAll(@PageableDefault Pageable pageable, HttpServletRequest request,
			@AuthenticationPrincipal UserEntity userEntity)
	{
		Page<ReadOrderDTO> orders = null;
		orders = findOrderForAccurateRole(userEntity, pageable);
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
		return ResponseEntity.ok()
							 .header("link", paginationLinksUtils.createLinkHeader(orders, uriBuilder))
							 .body(orders);
	}

	private Page<ReadOrderDTO> findOrderForAccurateRole(UserEntity userEntity, Pageable pageable)
	{
		if (userEntity.getRoles().contains(UserRole.ADMIN))
			return orderService.findAllAsDto(pageable);
		else
			return orderService.findAllByCustomer(userEntity, pageable);
	}
	
	@ApiOperation(value = "Get an order by its ID", notes = "Provides every orders's detail by its ID")
	@JsonView(Dto.class)
	@GetMapping("/{id}")
	public ReadOrderDTO findById(@PathVariable Long id)
	{
		return orderService.findByIdAsDto(id)
						   .orElseThrow(() -> new OrderNotFoundException(id));
	}
	
	@ApiOperation(value = "Creates a new order", notes = "Creates a new order, stores it, and returns its details")
	@PostMapping("")
	public ResponseEntity<Order> create(@RequestBody WriteOrderDto newOrder, @AuthenticationPrincipal UserEntity user)
	{
		throwBadRequestIfDTOIsNotValid(newOrder);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(orderService.save(newOrder, user).orElseThrow(ProductNotFoundException::new));
	}
	
	@ApiOperation(value = "Edits an existing order's customer", notes = "Edit the order's customer whose ID is passed in the path")
	@PutMapping("/{id}")
	public ResponseEntity<Order> edit(@PathVariable Long id, @AuthenticationPrincipal UserEntity user)
	{
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(orderService.edit(id, user).orElseThrow(ProductNotFoundException::new));
	}
	
	private void throwBadRequestIfDTOIsNotValid(WriteOrderDto dto)
	{
		throwBadRequestIf(!wirteOrderDtoValidator.isValid(dto));
	}
	
	private void throwBadRequestIf(boolean flag)
	{
		if (flag)
			throw new WriterOrderDTONotValidException();
	}
	
	@ApiOperation(value = "Removes an order", notes = "Removes the order whose ID is passed in the path, and all its rows.")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> remove(@PathVariable Long id, @AuthenticationPrincipal UserEntity user)
	{
		if (user.getRoles().contains(UserRole.ADMIN))
		{
			return orderService.findById(id).map(order ->
			{
				orderService.delete(order);
				return ResponseEntity.noContent().build();
				
			}).orElseThrow(() -> new OrderNotFoundException(id));
		}
		else
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
								 .body(new APIError(HttpStatus.UNAUTHORIZED, "Only ADMIN can remove order."));
	}
}
