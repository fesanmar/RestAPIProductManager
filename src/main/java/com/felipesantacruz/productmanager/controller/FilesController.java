package com.felipesantacruz.productmanager.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipesantacruz.productmanager.upload.StorageService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FilesController
{
	private static final Logger logger = LoggerFactory.getLogger(FilesController.class);
	@Qualifier("fileSystemStorageService")
	private final StorageService storageService;
	
	@ApiOperation(value = "Get a file by its name", notes = "Provides file resource its name")
	@GetMapping("/files/{filename:.+}")
	public ResponseEntity<Resource> serveFile(@PathVariable String filename, HttpServletRequest request)
	{
		Resource file = storageService.loadAsResource(filename);
		
		String contentType = null;
		try
		{
			contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
		} catch (IOException ioe)
		{
			logger.info("Could not determine file type.");
		}
		
		if(contentType == null)
            contentType = "application/octet-stream"; // Generic bytes raw
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.body(file);
	}
}
