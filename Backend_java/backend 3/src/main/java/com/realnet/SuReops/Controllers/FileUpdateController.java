package com.realnet.SuReops.Controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.SuReops.Repos.GetFileRepository;
import com.realnet.SuReops.entity.FileDetails;
import com.realnet.SuReops.entity.GetFile;
import com.realnet.entitybuilder.response.EntityResponse;

@RestController
@RequestMapping("/sureops/fileupdate")
public class FileUpdateController {

	@Autowired
	private GetFileRepository fileRepository;

	@Value("${projectPath}")
	private String projectPath;

	@PutMapping("/updatefile/{id}")
	public ResponseEntity<?> updatefile(@PathVariable Long id,@RequestBody FileDetails file1) throws IOException {

		Optional<GetFile> fGetFile = fileRepository.findById(id);
		if(fGetFile.isPresent()) {

		String file_path = fGetFile.get().getFile_path();

		String newpath = projectPath.concat(file_path);

		StringBuilder builder = new StringBuilder();

		builder.append(file1.getText());
		builder.append("\n");
		
//		creating files
		File file = new File(newpath);

//		Writing files

		FileWriter fr = new FileWriter(file.getAbsoluteFile());
		BufferedWriter br = new BufferedWriter(fr);
		br.write(builder.toString());
		br.close();
		
		
		FileDetails fileDetails = new FileDetails();
		String codee = builder.substring(0, builder.lastIndexOf("\n")); // remove last line break

		fileDetails.setText(codee.toString());

		return new ResponseEntity<FileDetails>(fileDetails, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new EntityResponse("Not Found"), HttpStatus.NOT_FOUND);

		}

	}

}
