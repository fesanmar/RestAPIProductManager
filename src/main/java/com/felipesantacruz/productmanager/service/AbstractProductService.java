package com.felipesantacruz.productmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.felipesantacruz.productmanager.dto.product.ProductDTO;
import com.felipesantacruz.productmanager.dto.product.WriteProductDTO;
import com.felipesantacruz.productmanager.model.Category;
import com.felipesantacruz.productmanager.model.Product;
import com.felipesantacruz.productmanager.repo.ProductRepository;

public abstract class AbstractProductService extends BaseService<Product, Long, ProductRepository>
{
	public abstract Product createProduct(WriteProductDTO newProduct);
	
	public abstract Optional<Product> editProduct(Long id, WriteProductDTO edit);
	
	public abstract List<ProductDTO> findAllAsDto();
	
	public abstract Page<ProductDTO> findAllAsDto(Pageable pageable);
	
	public abstract Page<ProductDTO> findByArgsAsDto(final Optional<String> name, final Optional<Float> price, Pageable pageable);
	
	public abstract Product attachFilesToProduct(MultipartFile[] files, Product p);
	
	public abstract Product detachFilesFromProduct(String[] files, Product p);
	
	public abstract boolean isCategoryBeingUsed(Category c);
	
	
}
