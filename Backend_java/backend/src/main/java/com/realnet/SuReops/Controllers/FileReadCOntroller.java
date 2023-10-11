package com.realnet.SuReops.Controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.SuReops.Repos.GetFileRepository;
import com.realnet.SuReops.entity.FileDetails;
import com.realnet.SuReops.entity.GetFile;
import com.realnet.fnd.entity.Error;
import com.realnet.fnd.entity.ErrorPojo;
import com.realnet.utils.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/sureops/getfile")
public class FileReadCOntroller {

	@Autowired
	private GetFileRepository fileRepository;

	@Value("${projectPath}")
	private String projectPath;

	@GetMapping("/getfile/{id}")
	public ResponseEntity<?> fileread(@PathVariable Long id) {

		GetFile param = fileRepository.findById(id).orElseThrow(null);

		String staticPath = param.getFile_path(); // in a same folder
//		String moved_address_string =
//				"C:/Users/Dell/Desktop/26 AUG/CNS-Portal-ashwini0807_30Sept/backend/sureOps/script_files_1663908314239/test_1663908314239.script";

//		"/src/main/resources/extracted-files/TEST_f_1662568747795/test.html";

//		String staticFileName = "SE_".concat(name_string);
//		String staticPath = moved_address_string;
//		staticPath = staticPath.substring(0, staticPath.lastIndexOf("/"));
//		staticPath = staticPath.concat("/static_code/"); // static code folder
//		staticPath = staticPath.concat(staticFileName); // static filde name
//		System.out.println(staticPath);

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

	

	@GetMapping("/get_allfile/{profile_id}")
	public List<GetFile> getdetails(@PathVariable Long profile_id) {
		List<GetFile> file = fileRepository.findTopByOrderByd(profile_id);
		return file;
	}
}
