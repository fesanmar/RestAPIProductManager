package com.felipesantacruz.productmanager.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.felipesantacruz.productmanager.dto.ProductDTO;
import com.felipesantacruz.productmanager.dto.WriteProductDTO;
import com.felipesantacruz.productmanager.dto.converter.ProductDTOConverter;
import com.felipesantacruz.productmanager.dto.validator.WriteProductDTOValidator;
import com.felipesantacruz.productmanager.error.APIError;
import com.felipesantacruz.productmanager.error.ProductNotFoundException;
import com.felipesantacruz.productmanager.error.WriterProductDTONotValidException;
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
	private final WriteProductDTOValidator writeProductDTOValidato;
	
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
	public Product findById(@PathVariable Long id)
	{
		return productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException(id));
	}
	
	@PostMapping("/product")
	public ResponseEntity<Product> create(@RequestBody WriteProductDTO newProduct)
	{
		throwBadRequestIfDTOIsNotValid(newProduct);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(productRepository.save(productDTOConverter.convertFromDTO(newProduct)));
	}

	private void throwBadRequestIfDTOIsNotValid(WriteProductDTO newProduct)
	{
		if (!writeProductDTOValidato.isValid(newProduct))
			throw new WriterProductDTONotValidException();
	}
	
	@PutMapping("/product/{id}")
	public Product edit(@RequestBody WriteProductDTO editedProduct, @PathVariable Long id)
	{
		throwBadRequestIfDTOIsNotValid(editedProduct);
		Product productEdit = productDTOConverter.convertFromDTO(editedProduct);
		return productRepository.findById(id).map(p -> 
		{
			p.setName(productEdit.getName());
			p.setPrice(productEdit.getPrice());
			p.setCategory(productEdit.getCategory());
			return productRepository.save(p);
		}).orElseThrow(() -> new ProductNotFoundException(id));
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> remove(@PathVariable Long id)
	{
		return productRepository.findById(id).map(p -> {
			productRepository.delete(p);
			return ResponseEntity.noContent().build();
		}).orElseThrow(() -> new ProductNotFoundException(id));
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<APIError> handleProductNotFount(ProductNotFoundException e)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(APIError.with(HttpStatus.NOT_FOUND, e));
	}
	
	@ExceptionHandler({ JsonMappingException.class, WriterProductDTONotValidException.class })
	public ResponseEntity<APIError> handleJsonMappingException(RuntimeException e)
	{
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(APIError.with(HttpStatus.BAD_REQUEST, e));
	}
}
