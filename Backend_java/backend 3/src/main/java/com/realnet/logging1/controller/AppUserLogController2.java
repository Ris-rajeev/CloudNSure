package com.realnet.logging1.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.SuReops.entity.FileDetails;
import com.realnet.SuReops.entity.GetFile;
import com.realnet.fnd.entity.Error;
import com.realnet.fnd.entity.ErrorPojo;
import com.realnet.logging1.entity.AppUserLog;
import com.realnet.logging1.repository.AppUserLogginRepository;
import com.realnet.logging1.service.LoggingService;
import com.realnet.utils.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/log2")
public class AppUserLogController2 {
	private LoggingService loggingService;
	
	@Value("${projectPath}")
	private String projectPath;
	
	@Autowired
	private AppUserLogginRepository logginRepository;

	@Autowired
	public AppUserLogController2(LoggingService loggingService) {
		super();
		this.loggingService = loggingService;
	}

	@GetMapping("/downloadLog/{filename}")
	public ResponseEntity<?> sendFile(@PathVariable("filename") String filename, HttpServletRequest request)
			throws Exception {
		// AppUser a = appUserServiceImpl.getByUserName(username).orElse(null);
		Resource resource = loggingService.loadFileAsResource(filename);
		if (resource != null) {
			String contentType = null;
			try {
				contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			} catch (IOException ex) {
				throw new Exception("Could not find log file for file: " + filename);
			}
			// Fallback to the default content type if type could not be determined
			if (contentType == null) {
				contentType = "application/octet-stream";
			}
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		} else {
			return new ResponseEntity<>("File could not be loaded", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/fileread/{id}")
	public ResponseEntity<?> fileread(@PathVariable Long id) {

		AppUserLog param = logginRepository.findById(id).orElseThrow(null);

		String staticPath =File.separator+"logs"+File.separator+ param.getLogFileName(); // in a same folder


		// projectPath
		String filePath = projectPath.concat(staticPath);

		StringBuilder code = new StringBuilder();
		File file = null;
		try {
			file = new File(filePath);
			String fileName = file.getName();
			log.info("Static File Name : {}", fileName);

			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				code.append(line + "\n");
			}
			fr.close();
			br.close();
		} catch (IOException e) {
			log.debug("IO Exception Handled...");
			log.error(e.getMessage());
			e.getMessage();
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.FILE_OPERATION_API_TITLE);
			error.setMessage(Constant.FILE_NOT_FOUND_EXCEPTION);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}
		String string = code.toString();
		String codee = code.substring(0, code.lastIndexOf("\n")); // remove last line break
		FileDetails fileDetails = new FileDetails();
		fileDetails.setId(id);
		fileDetails.setText(codee);
		return new ResponseEntity<FileDetails>(fileDetails, HttpStatus.OK);
	}

}
