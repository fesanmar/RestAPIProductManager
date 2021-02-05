package com.felipesantacruz.productmanager.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;
import com.felipesantacruz.productmanager.dto.ProductDTO;
import com.felipesantacruz.productmanager.dto.WriteProductDTO;
import com.felipesantacruz.productmanager.dto.validator.WriteProductDTOValidator;
import com.felipesantacruz.productmanager.dto.view.ProductViews;
import com.felipesantacruz.productmanager.error.APIError;
import com.felipesantacruz.productmanager.error.ProductNotFoundException;
import com.felipesantacruz.productmanager.error.WriterProductDTONotValidException;
import com.felipesantacruz.productmanager.model.Product;
import com.felipesantacruz.productmanager.service.AbstractProductService;
import com.felipesantacruz.productmanager.util.pagination.PaginationLinksUtils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductController
{
	private final AbstractProductService productService;
	private final WriteProductDTOValidator writeProductDTOValidator;
	private final PaginationLinksUtils paginationLinksUtils;
	
	@ApiOperation(value = "Get an all products's list. A filter can be applied wih the queries args name and price", 
			notes = "Provides a list with every products if name and price queries are not setted. \n"
					+ "name query: Returns every product whose name conatains the name arg (case insensitive)\n"
					+ "price query: Returns every product whose price is losser or equal to the price arg.\n"
					+ "Both queries can be combined.")
	@JsonView(ProductViews.DtoWithPrice.class)
	@GetMapping("/product")
	public ResponseEntity<Page<ProductDTO>> fetchAllWithArgs(
			@RequestParam(name = "name", required = false) Optional<String> name, 
			@RequestParam(name = "price", required = false) Optional<Float> price,
			@PageableDefault Pageable pageable, HttpServletRequest request)
	{
		Page<ProductDTO> productsPage = productService.findByArgsAsDto(name, price, pageable);
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
		return ResponseEntity.ok()
				.header("link", paginationLinksUtils.createLinkHeader(productsPage, uriBuilder))
				.body(productsPage);
	}
	
	@ApiOperation(value = "Get a product by its ID", notes = "Provides every product's detail by its ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = Product.class),
			@ApiResponse(code = 404, message = "Not Found", response = APIError.class),
			@ApiResponse(code = 404, message = "Internal Server Error", response = APIError.class)
	})
	@GetMapping("/product/{id}")
	public Product findById(@ApiParam(value = "Product's ID", required = true, type = "long") @PathVariable Long id)
	{
		return productService.findById(id)
				.orElseThrow(() -> new ProductNotFoundException(id));
	}
	
	@ApiOperation(value = "Creates a new product", notes = "Creates a new product, stores it, and returns its details")
	@PostMapping("/product")
	public ResponseEntity<Product> create(@RequestBody WriteProductDTO newProduct)
	{
		throwBadRequestIfDTOIsNotValid(newProduct);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(productService.createProduct(newProduct));
	}
	
	@ApiOperation(value = "Attach file to a product", notes = "Attach a file to the product provided by its ID")
	@PatchMapping("/product/{id}/files/add")
	public Product addFiles(@PathVariable Long id, @RequestParam("file") MultipartFile[] files)
	{
		return productService.findById(id)
				.map(p -> productService.attachFilesToProduct(files, p))
				.orElseThrow(() -> new ProductNotFoundException(id));
	}
	
	@ApiOperation(value = "Dettach a file from a product", notes = "Dettach a file to the product provided by its ID")
	@PatchMapping("/product/{id}/files/remove")
	public Product removeFiles(@PathVariable Long id, 
			@RequestParam(name = "files", required = true) String[] files)
	{
		return productService.findById(id)
				.map(p -> productService.detachFilesFromProduct(files, p))
				.orElseThrow(() -> new ProductNotFoundException(id));
	}

	private void throwBadRequestIfDTOIsNotValid(WriteProductDTO newProduct)
	{
		if (!writeProductDTOValidator.isValid(newProduct))
			throw new WriterProductDTONotValidException();
	}
	
	@ApiOperation(value = "Updates a product's details", notes = "Edit the product whose ID is passed in the path")
	@PutMapping("/product/{id}")
	public Product edit(@RequestBody WriteProductDTO editedProduct, @PathVariable Long id)
	{
		throwBadRequestIfDTOIsNotValid(editedProduct);
		return productService.editProduct(id, editedProduct)
				.orElseThrow(() -> new ProductNotFoundException(id));
	}
	
	@ApiOperation(value = "Removes a product", notes = "Removes the product whose ID is passed in the path")
	@DeleteMapping("/product/{id}")
	public ResponseEntity<Object> remove(@PathVariable Long id)
	{
		return productService.findById(id).map(p -> {
			productService.delete(p);
			return ResponseEntity.noContent().build();
		}).orElseThrow(() -> new ProductNotFoundException(id));
	}
}
