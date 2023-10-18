package com.realnet.codeextractor.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.codeextractor.entity.Build_controller;
import com.realnet.codeextractor.entity.FileDetails;
import com.realnet.codeextractor.repository.BuildControllerrepo;
import com.realnet.fnd.entity.Error;
import com.realnet.fnd.entity.ErrorPojo;
import com.realnet.fnd.entity.Success;
import com.realnet.fnd.entity.SuccessPojo;
import com.realnet.utils.Constant;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/codeextractor/builderfile")
public class BuilderFilecontroller {

	private static final Logger log = LoggerFactory.getLogger(BuilderFilecontroller.class);

	@Value("${projectPath}")
	private String projectPath;

	@Autowired
	private BuildControllerrepo buildControllerrepo;

	@GetMapping("/get_allfile/{id}")
	public ResponseEntity<?> getdetails(@PathVariable Integer id) {
		List<Build_controller> file = buildControllerrepo.findallbybcfid(id);
		if (file.isEmpty()) {
			return new ResponseEntity<>("file is empty", HttpStatus.BAD_REQUEST);

		} else {
			return new ResponseEntity<>(file, HttpStatus.OK);

		}
	}

	@GetMapping("/readfile/{id}")
	public ResponseEntity<?> fileread(@PathVariable Integer id) {

		Build_controller l = buildControllerrepo.findById(id).orElseThrow(null);

		String filepath = l.getFilepath(); // in a same folder
		filepath = filepath.replace(projectPath, "");
		filepath = projectPath.concat(filepath);

		String filename = l.getApi_endpoint()+".java";

		String filePath = filepath + filename;

		// projectPath

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

			return new ResponseEntity<>("error", HttpStatus.EXPECTATION_FAILED);
		}
		String string = code.toString();
		String codee = code.substring(0, code.lastIndexOf("\n")); // remove last line break
		FileDetails fileDetails = new FileDetails();
		fileDetails.setId(id);
		fileDetails.setText(codee);
		return new ResponseEntity<>(code, HttpStatus.OK);
	}

	@ApiOperation(value = "Save File Data")
	@PostMapping(value = "/savefile")
	public ResponseEntity<?> codeSaveInFile(@Valid @RequestBody FileDetails fileDetails) {

		int id = fileDetails.getId();
		String code = fileDetails.getText();
		log.debug("updated code : {}", code);

		Build_controller l = buildControllerrepo.findById(id).orElseThrow(null);
		String fileName = l.getApi_endpoint()+".java";
		String filepath = l.getFilepath(); // in a same folder
		filepath = filepath.replace(projectPath, "");
		filepath = projectPath.concat(filepath)+fileName;

		File file = null;
		try {
			file = new File(filepath);
			String fileType = FilenameUtils.getExtension(fileName);
			log.info("File Name : {}", fileName);
			log.info("File Type : {} ", fileType);

			BufferedWriter bw = new BufferedWriter(new FileWriter(file, false)); // OVER WRITE FILE
			bw.write(code);
			bw.close();
		} catch (FileNotFoundException e) {
			log.debug("File Not Found Exception Handled...");
			log.error(e.getMessage());
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.FILE_OPERATION_API_TITLE);
			error.setMessage(Constant.FILE_NOT_FOUND_EXCEPTION);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		} catch (IOException e) {
			log.error(e.getMessage());
			log.debug("IOException Handled...");
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.FILE_OPERATION_API_TITLE);
			error.setMessage(Constant.FILE_CODE_SAVE_FAILURE);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}

		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.FILE_OPERATION_API_TITLE);
		success.setMessage(Constant.FILE_CODE_SAVE_SUCCESSFULLY);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);
	}

	@GetMapping("/getbyid/{id}")
	public ResponseEntity<?> getbyid(@PathVariable Integer id) {
		Build_controller file = buildControllerrepo.findById(id).get();

		return new ResponseEntity<>(file, HttpStatus.OK);

	}

	// delete file
	@DeleteMapping("/deletefile/{id}")
	public ResponseEntity<?> deletefile(@PathVariable Integer id) throws IOException {

		Build_controller l = buildControllerrepo.findById(id).orElseThrow(null);
		String fileName = l.getApi_endpoint()+".java";
		String filepath = l.getFilepath(); // in a same folder
		filepath = filepath.replace(projectPath, "");
		filepath = projectPath.concat(filepath)+fileName;

		// projectPath

		File file = null;

		file = new File(filepath);
		boolean delete = file.delete();

		if (delete) {
			buildControllerrepo.delete(l);

			return new ResponseEntity<>("file deleted", HttpStatus.OK);

		} else {
			return new ResponseEntity<>("file not deleted", HttpStatus.BAD_REQUEST);

		}
	}

}
