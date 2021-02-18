package com.felipesantacruz.productmanager.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;
import com.felipesantacruz.productmanager.dto.ReadOrderDTO;
import com.felipesantacruz.productmanager.dto.view.OrderViews.Dto;
import com.felipesantacruz.productmanager.dto.view.OrderViews.DtoWithPriceImageCategory;
import com.felipesantacruz.productmanager.error.OrderNotFoundException;
import com.felipesantacruz.productmanager.service.OrderService;
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
	
	@ApiOperation(value = "Get an all orders's list.", notes = "Provides a list with every order ever.")
	@JsonView(DtoWithPriceImageCategory.class)
	@GetMapping("")
	public ResponseEntity<Page<ReadOrderDTO>> fetchAll(@PageableDefault Pageable pageable, HttpServletRequest request)
	{
		Page<ReadOrderDTO> orders = orderService.findAllAsDto(pageable);
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
		return ResponseEntity.ok()
							 .header("link", paginationLinksUtils.createLinkHeader(orders, uriBuilder))
							 .body(orders);
	}
	
	@ApiOperation(value = "Get an order by its ID", notes = "Provides every orders's detail by its ID")
	@JsonView(Dto.class)
	@GetMapping("/{id}")
	public ReadOrderDTO findById(@PathVariable Long id)
	{
		return orderService.findByIdAsDto(id)
						   .orElseThrow(() -> new OrderNotFoundException(id));
	}
}
