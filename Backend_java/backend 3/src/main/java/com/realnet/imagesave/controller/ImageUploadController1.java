//package com.realnet.imagesave.controller;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.realnet.fnd.entity.Success;
//import com.realnet.fnd.entity.SuccessPojo;
//import com.realnet.imagesave.Repositoy.Rn_ImageUpload_Repository;
//import com.realnet.imagesave.entity.Rn_ImageUpload_Details;
//import com.realnet.imagesave.service.ImageUploadService;
//import com.realnet.utils.Constant;
//
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping(value = "/image/upload", produces = MediaType.APPLICATION_JSON_VALUE)
//public class ImageUploadController1 {
//	
//	
//		
//	@Autowired
//	private Rn_ImageUpload_Repository imageRepository;
//	
//	@Autowired
//	private ImageUploadService imageUploadService;
//	
//	@Value("${projectPath}")
//	private String projectPath;
//	
//	@Value("${imagePath}")
//	private String imagePath;
//	
//	
//	
//	
//	
//	@ApiOperation(value = "Add new Image")
//	@PostMapping(value = "/image_upload")
//	public ResponseEntity<?> uploadImage(
//			@RequestParam(value = "image", required = true) MultipartFile image)  {
//	
//		
////		    Uploading Images
//	
//
//		
////		Changing the directory to store file 
//			StringBuilder newDir =imageUploadService.directoryName();
//		
//		
////			Changing Name of the Image
//			String imageName = image.getOriginalFilename();
//			String ext = imageName.substring(imageName.lastIndexOf("."));
//			Date d = new Date();	
//			String addString = "_" +d.getTime() ;
//			String imageReplace =imageName.replaceFirst(ext,addString);
//			String newName = imageReplace + ext;
//			
//			imageUploadService.uploadImages(image, newDir);
//			
////			saving Data
//			
//			String path = newDir.toString();
//			System.out.println(path);
//			Rn_ImageUpload_Details imageDetails = new Rn_ImageUpload_Details();
//			imageDetails.setImage_name(imageName);
//			imageDetails.setImage_address(path);
//			imageDetails.setImage_string(newName);
//			imageRepository.save(imageDetails);
//			
//
//			return new ResponseEntity<String>("Image Uploaded Successfully", HttpStatus.CREATED);
//		
//			
//		}
//	
//	
//	
//	
//}
