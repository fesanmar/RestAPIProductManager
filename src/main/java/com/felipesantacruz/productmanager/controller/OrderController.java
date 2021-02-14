package com.felipesantacruz.productmanager.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.felipesantacruz.productmanager.model.Order;
import com.felipesantacruz.productmanager.service.OrderService;
import com.felipesantacruz.productmanager.util.pagination.PaginationLinksUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController
{
	private final OrderService orderService;
	private final PaginationLinksUtils paginationLinksUtils;
	
	@GetMapping("")
	public ResponseEntity<Page<Order>> fetchAll(@PageableDefault Pageable pageable, HttpServletRequest request)
	{
		Page<Order> orders = orderService.findAll(pageable);
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
		return ResponseEntity.ok()
				.header("link", paginationLinksUtils.createLinkHeader(orders, uriBuilder))
				.body(orders);
	}
}
