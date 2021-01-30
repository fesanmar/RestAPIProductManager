package com.felipesantacruz.productmanager.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.felipesantacruz.productmanager.dto.converter.ProductDTOConverter;
import com.felipesantacruz.productmanager.dto.product.ProductDTO;
import com.felipesantacruz.productmanager.dto.product.WriteProductDTO;
import com.felipesantacruz.productmanager.model.Category;
import com.felipesantacruz.productmanager.model.Product;
import com.felipesantacruz.productmanager.upload.StorageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService extends AbstractProductService
{
	private final ProductDTOConverter productDTOConverter;
	private final StorageService storageService;
	
	@Override
	public Product createProduct(WriteProductDTO newProduct)
	{
		return save(productDTOConverter.convertFromDTO(newProduct));
	}
	
	@Override
	public Optional<Product> editProduct(Long id, WriteProductDTO edit)
	{
		Product productEdit = productDTOConverter.convertFromDTO(edit);
		return findById(id).map(p -> 
		{
			p.setName(productEdit.getName());
			p.setPrice(productEdit.getPrice());
			p.setCategory(productEdit.getCategory());
			return Optional.of(save(p));
		}).orElse(Optional.empty());
	}
	
	@Override
	public List<ProductDTO> findAllAsDto()
	{
		return findAll()
				.stream()
				.map(productDTOConverter::convertToDTO)
				.collect(Collectors.toList());
	}
	
	@Override
	public Page<ProductDTO> findAllAsDto(Pageable pageable)
	{
		return findAll(pageable)
				.map(productDTOConverter::convertToDTO);
	}
	
	@Override
	public Product attachFilesToProduct(MultipartFile[] files, Product p)
	{
		Arrays.stream(storageService.store(files)).forEach(p::addImage);
		return save(p);
	}
	
	@Override
	public Product detachFilesFromProduct(String[] files, Product p)
	{
		Consumer<String> removeImageFromProduct = p::removeImage;
		Consumer<String> removeImageFromStorage = storageService::delete;
		Consumer<String> removeImage = removeImageFromProduct.andThen(removeImageFromStorage);
		
		Arrays.stream(files)
			.filter(p::hasImage)
			.forEach(removeImage);
		return save(p);
	}
	
	@Override
	public void delete(Product p)
	{
		p.getImages().stream().forEach(storageService::delete);
		super.delete(p);
	}

	@Override
	public boolean isCategoryBeingUsed(Category c)
	{
		return !repository.findByCategory(c).isEmpty();
	}
}