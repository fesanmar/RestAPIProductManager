package com.felipesantacruz.productmanager.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipesantacruz.productmanager.dto.WriteProductDTO;
import com.felipesantacruz.productmanager.dto.ProductDTO;
import com.felipesantacruz.productmanager.dto.converter.ProductDTOConverter;
import com.felipesantacruz.productmanager.model.Product;
import com.felipesantacruz.productmanager.repo.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductController
{
	private final ProductRepository productRepository;
	private final ProductDTOConverter productDTOConverter;
	
	@GetMapping("/product")
	public List<ProductDTO> fetchAll()
	{
		return productRepository
				.findAll()
				.stream()
				.map(productDTOConverter::convertToDTO)
				.collect(Collectors.toList());
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id)
	{
		// This is like sending an OK if product was found and 404 otherwise
		return ResponseEntity.of(productRepository.findById(id));
	}
	
	@PostMapping("/product")
	public ResponseEntity<Product> create(@RequestBody WriteProductDTO newProduct)
	{
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(productRepository.save(productDTOConverter.convertFromDTO(newProduct)));
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<Product> edit(@RequestBody WriteProductDTO editedProduct, @PathVariable Long id)
	{
		Product productEdit = productDTOConverter.convertFromDTO(editedProduct);
		return productRepository.findById(id).map(p -> 
		{
			p.setName(productEdit.getName());
			p.setPrice(productEdit.getPrice());
			p.setCategory(productEdit.getCategory());
			return ResponseEntity.ok(productRepository.save(p));
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> remove(@PathVariable Long id)
	{
		return productRepository.findById(id).map(p -> {
			productRepository.delete(p);
			return ResponseEntity.noContent().build();
		}).orElse(ResponseEntity.notFound().build());
	}
}
