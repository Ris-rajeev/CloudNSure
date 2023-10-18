//package com.realnet.imagesave.service;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.Date;
//import java.util.Properties;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.realnet.exceptions.StorageException;
//
//
//import lombok.extern.slf4j.Slf4j;
//
//
//@Slf4j
//@Service
//public class ImageUploadService {
//
//	//@Value("${projectPath}")
//	//private String projectPath;
//
//	@Value("${imagePath}")
//	private String imagePath;
//
//	
//	
//	public void uploadImages(MultipartFile image, StringBuilder location) {
//		if (image.isEmpty()) {
//			throw new StorageException("Failed to store empty file");
//		}
//		
//		// create path for every new File
//		// File fileLoc = new File(location);
//		
//		
//		try {
//	
//			String imageName = image.getOriginalFilename();
//			String ext = imageName.substring(imageName.lastIndexOf("."));
//			Date d = new Date();	
//			String addString = "_" +d.getTime() ;
//			String imageReplace =imageName.replaceFirst(ext,addString);
//			String newName = imageReplace + ext;
//			System.out.println(imageReplace);
//			
//			InputStream is = image.getInputStream();
//			Files.copy(is, Paths.get(location + newName), StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			String msg = String.format("Failed to store file %s", image.getName());
//			log.info(msg);
//			throw new StorageException("Failed to store file ", e);
//		}
//
//		
//		
//	}
//	
//	public StringBuilder directoryName() {
//		
//		StringBuilder Dir=new StringBuilder();
//		Properties properties = System.getProperties();
//		Dir.append(properties);
//		StringBuilder directory=new StringBuilder();
//		StringBuilder newDir=new StringBuilder();
//		directory.append(Dir.reverse());
//		int i=0,c=0;
//		i=directory.charAt(c);
//		System.out.println(directory.charAt(7)+"  " +i);
//		while(i!=92) {
//			c++;
//			i=directory.charAt(c);	
//		}
//		
//		newDir.append(directory,c+1,directory.length());
//		newDir=newDir.reverse();
//		newDir.append(imagePath);			
//		
//		return newDir;
//	}
//	
//	
//	
//}
