package com.felipesantacruz.productmanager.upload;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.felipesantacruz.productmanager.controller.FilesController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Primary
public class UriStringProviderStorageServiceDecorator implements StorageService
{
	@Qualifier("fileSystemStorageService")
	private final StorageService storageService;
	
	@Override
	public void init()
	{
		storageService.init();
	}

	@Override
	public String store(MultipartFile file)
	{
		return MvcUriComponentsBuilder
				.fromMethodName(FilesController.class, "serveFile", storageService.store(file), null)
				.build()
				.toUriString();
	}
	
	@Override
	public String[] store(MultipartFile[] files)
	{ 
		return Arrays.stream(files)
				.filter(f -> !f.isEmpty())
				.map(this::store)
				.toArray(String[]::new);
	}

	@Override
	public Stream<Path> loadAll()
	{
		return storageService.loadAll();
	}

	@Override
	public Path load(String filename)
	{
		return storageService.load(filename);
	}

	@Override
	public Resource loadAsResource(String filename)
	{
		return storageService.loadAsResource(filename);
	}

	@Override
	public void delete(String filename)
	{
		storageService.delete(filename);
	}

	@Override
	public void deleteAll()
	{
		storageService.deleteAll();
	}

}
