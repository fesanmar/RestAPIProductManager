package com.felipesantacruz.productmanager.upload;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service("fileSystemStorageService")
public class FileSystemStorageService implements StorageService
{

	private final Path rootLocation;

	public FileSystemStorageService(@Value("${upload.root-location}") String path) 
	{
		this.rootLocation = Paths.get(path);
	}
	
	@Override
	public void init()
	{
		try
		{
			Files.createDirectories(rootLocation);
		} catch (IOException e)
		{
			throw new StorageException("Could not initialize storage", e);
		}
	}

	@Override
	public String store(MultipartFile file)
	{
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		String extension = StringUtils.getFilenameExtension(filename);
		String justFilename = filename.replace("." + extension, "");
		String storedFilename = System.currentTimeMillis() + "_" + justFilename + "." + extension;

		if (file.isEmpty())
			throw new StorageException("Failed to store empty file " + filename);
		if (filename.contains("..")) // Security check
			throw new StorageException("Cannot store file with relative path outside current directory " + filename);

		try (InputStream inputStream = file.getInputStream())
		{
			Files.copy(inputStream, rootLocation.resolve(storedFilename), StandardCopyOption.REPLACE_EXISTING);
			return storedFilename;
		} catch (IOException e)
		{
			throw new StorageException("Failed to store file " + filename, e);
		}
	}

	@Override
	public Stream<Path> loadAll()
	{
		try
		{
			return Files.walk(rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(rootLocation::relativize);
		} catch (IOException e)
		{
			throw new StorageException("Failed to read stored files", e);
		}
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
	public Path load(String filename)
	{
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename)
	{
		try
		{
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			
			if (resource.exists() || resource.isReadable())
				return resource;
			else
				throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);
		} catch (MalformedURLException e)
		{
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void delete(String filename)
	{
		String justFilename = StringUtils.getFilename(filename);
		try
		{
			Path file = load(justFilename);
			Files.delete(file);
		} catch (IOException e)
		{
			throw new StorageException("Failed to delete file", e);
		}
		
	}

	@Override
	public void deleteAll()
	{
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

}
