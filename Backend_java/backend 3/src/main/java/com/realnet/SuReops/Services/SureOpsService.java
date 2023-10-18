package com.realnet.SuReops.Services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.SuReops.Repos.GetFileRepository;
import com.realnet.SuReops.entity.GetFile;
import com.realnet.entitybuilder.response.EntityResponse;
import com.realnet.fnd.entity.Rn_Project_Setup;
import com.realnet.fnd.repository.Rn_ProjectSetup_Repository;
import com.realnet.workflow.Entites.Workflow_Line;
import com.realnet.workflow.Entites.Workflow_table;
import com.realnet.workflow.repository.W_repository;
import com.realnet.workflow.repository.Workflow_lineRepository;

@Service
public class SureOpsService {

	@Autowired
	private GetFileRepository fileRepository;

	@Autowired
	private Rn_ProjectSetup_Repository projectSetupRepository;

	@Autowired
	private Workflow_lineRepository workflow_lineRepository;

	@Autowired
	private W_repository tableRepository;

	@Value("${projectPath}")
	private String projectPath;

	public void Createonefile(Rn_Project_Setup prj, Workflow_Line workflowline,Long profile_id) throws UnsupportedEncodingException {

//		Appending data to Sting builder object

		Date d = new Date();
		String addString = "_" + d.getTime();

//		Parsing Json data 

		List<String> keys = new ArrayList<>();

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(workflowline.getModel());
		JsonArray Array = element.getAsJsonArray();

		for (JsonElement ar : Array) {

			JsonObject obj = ar.getAsJsonObject();
//			Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
//			for (Map.Entry<String, JsonElement> entry : entries) {
//				String key = entry.getKey();
//				String keystr = key.toString();
//				String value = entry.getValue().getAsString();
//				keys.add(value);
//
//				
//				if (keystr.equals("workflow")) {
			JsonElement workflowvalue = obj.get("workflow");
			String value = workflowvalue.getAsString();

			Workflow_table workflow_table = tableRepository.findByWorkflow_name(value);
			Workflow_Line workflow = workflow_lineRepository.findByWorkflow_table_id(workflow_table.getId());
			CreateFiles(prj, workflow,profile_id);

//				}			

//			}
		}

	}

//	Creating files like yaml, shell SCript ,docker File

	public ResponseEntity<?> CreateFiles(Rn_Project_Setup prj, Workflow_Line workflow,Long profile_id)
			throws UnsupportedEncodingException {

//		Appending data to Sting builder object

		HashMap<String, String> json = new HashMap<>();

		String PRJ_NAME = prj.getProjectName();
		String portNumber = prj.getPortNumber();

//		Parsing Json data 

		List<String> keys = new ArrayList<>();

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(workflow.getModel());
		JsonArray Array = element.getAsJsonArray();

		int i = 1;
		for (JsonElement ar : Array) {
			JsonObject obj = ar.getAsJsonObject();
			Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
			for (Map.Entry<String, JsonElement> entry : entries) {
				String key = entry.getKey();
				String string = key.toString();
				String value = entry.getValue().getAsString();
				keys.add(value);

				if (value.equals("Yamel File")) {
					System.out.println("yaml data");
					yml(workflow.getModel(), PRJ_NAME, portNumber,profile_id);
					i++;
				} else if (value.equalsIgnoreCase("Shell Script")) {
					System.out.println("script data");
					script(workflow.getModel(), PRJ_NAME, portNumber,profile_id);
					i++;
				} else if (value.equalsIgnoreCase("Docker File")) {
					System.out.println("docker data");
					docker(workflow.getModel(), PRJ_NAME, portNumber,profile_id);
					i++;
				}

			}
		}
		if (i == 1) {
			Createonefile(prj, workflow,profile_id);

		}

		return new ResponseEntity<HashMap<String, String>>(json, HttpStatus.CREATED);
	}

//      CREATE YML FILE
//		if (file_type.equals("yaml")) {
	public void yml(String str, String prj, String portNumber,Long profile_id) {
		Date d = new Date();
		String addString = "_" + d.getTime();
		String file_text = "yaml_test" + addString + ".yaml";
		StringBuilder yamldata = new StringBuilder();

		List<String> keys = new ArrayList<>();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);
		JsonArray Array = element.getAsJsonArray();

		for (JsonElement ar : Array) {
			JsonObject obj = ar.getAsJsonObject();
			Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
			for (Map.Entry<String, JsonElement> entry : entries) {
				String key = entry.getKey();
				String string = key.toString();
				String value = entry.getValue().getAsString();
				keys.add(value);

				if (key.equals("script")) {
//					yamldata.append("PRJ_NAME= " + prj + " \nDOCKER_USER=< DOCKER_USER >\r\n"
//							+ "DOCKER_PASS=< DOCKER_PASS >\r\n" + "DOCKER_URL=< DOCKER_URL >\r\n" + "RUN_PORT= "
//							+ portNumber);
					yamldata.append("\n" + value);
				}

			}
		}

//		// TEMPORARY PUT DATA
//		yamldata.append("PRJ_NAME= " + prj + " \nDOCKER_USER=< DOCKER_USER >\r\n" + "DOCKER_PASS=< DOCKER_PASS >\r\n"
//				+ "DOCKER_URL=< DOCKER_URL >\r\n" + "RUN_PORT= " + portNumber);
		try {

			// Creating Folder

			String ref_path = "/SureOps" + File.separator + "yaml_files" + addString;

			String yamlDir = projectPath + ref_path;
			File Dir = new File(yamlDir);
			if (!Dir.exists()) {
				Dir.mkdir();
			}

			System.out.println(yamlDir);

			// creating files
//			String dir2 = projectPath + "/yaml_files" + addString + "/" + file_text;
			String dir2 = yamlDir + "/" + file_text;

			File file = new File(dir2);

			if (!file.exists()) {
				file.createNewFile();
			}
			System.out.println("file created successfully");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(yamldata.toString());
			bw.close();

			// SAVE FILENAME AND FILE PATH
			GetFile getFile = new GetFile();
			getFile.setFile_name(file_text);
			getFile.setProfile_id(profile_id);
			getFile.setFile_path(ref_path + "/" + file_text);
			fileRepository.save(getFile);

		} catch (IOException i) {

		}
	}

//      CREATE SCRIPT FILE

//		if (file_type.equalsIgnoreCase("script")) {
	public void script(String str, String prj, String portNumber,Long profile_id) {
		Date d = new Date();
		String addString = "_" + d.getTime();
		String file_text2 = "script_test" + addString + ".sh";
		StringBuilder scriptdata = new StringBuilder();

		List<String> keys = new ArrayList<>();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);
		JsonArray Array = element.getAsJsonArray();

		for (JsonElement ar : Array) {
			JsonObject obj = ar.getAsJsonObject();
			Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
			for (Map.Entry<String, JsonElement> entry : entries) {
				String key = entry.getKey();
				String string = key.toString();
				String value = entry.getValue().getAsString();
				keys.add(value);
//			System.out.println("----------------key---" + key);
//			System.out.println("----------------value--" + value);

				if (key.equals("script")) {
//					scriptdata.append("PRJ_NAME= " + prj + " \nDOCKER_USER=< DOCKER_USER >\r\n"
//							+ "DOCKER_PASS=< DOCKER_PASS >\r\n" + "DOCKER_URL=< DOCKER_URL >\r\n" + "RUN_PORT= "
//							+ portNumber);
					scriptdata.append("\n" + value);
				}

			}
		}
		// TEMPORARY PUT DATA
//		scriptdata.append("PRJ_NAME= " + prj + " \nDOCKER_USER=< DOCKER_USER >\r\n" + "DOCKER_PASS=< DOCKER_PASS >\r\n"
//				+ "DOCKER_URL=< DOCKER_URL >\r\n" + "RUN_PORT= " + portNumber);
		try {

//				Creating Folder
			String ref_path = "/SureOps" + File.separator + "script_files" + addString;
			String scriptDir = projectPath + ref_path;
			File Dir = new File(scriptDir);
			if (!Dir.exists()) {
				Dir.mkdir();
			}

			System.out.println(scriptDir);

//			creating files
//				String dir2 = projectPath + "/script_files"+addString+"/" + file_text2;
			String dir2 = scriptDir + "/" + file_text2;

			File file = new File(dir2);

			if (!file.exists()) {
				file.createNewFile();
			}
			System.out.println("file created successfully");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(scriptdata.toString());
			bw.close();

			// save file name and file path
			GetFile getFile = new GetFile();
			getFile.setFile_name(file_text2);
			getFile.setProfile_id(profile_id);
			getFile.setFile_path(ref_path + "/" + file_text2);
			fileRepository.save(getFile);

		} catch (IOException i) {

		}

	}

//       CREATE DOCKER FILE
	public void docker(String str, String prj, String portNumber,Long profile_id) {
		Date d = new Date();
		String addString = "_" + d.getTime();
		String file_text3 = "docker_test" + addString + ".docker";
		StringBuilder dockerdata = new StringBuilder();
//	   if(file_type.equals("docker")){

		List<String> keys = new ArrayList<>();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);
		JsonArray Array = element.getAsJsonArray();

		for (JsonElement ar : Array) {
			JsonObject obj = ar.getAsJsonObject();
			Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
			for (Map.Entry<String, JsonElement> entry : entries) {
				String key = entry.getKey();
				String string = key.toString();
				String value = entry.getValue().getAsString();
				keys.add(value);
//				System.out.println("----------------key---" + key);
//				System.out.println("----------------value--" + value);

				if (key.equals("script")) {
//					dockerdata.append("PRJ_NAME= " + prj + " \nDOCKER_USER=< DOCKER_USER >\r\n"
//							+ "DOCKER_PASS=< DOCKER_PASS >\r\n" + "DOCKER_URL=< DOCKER_URL >\r\n" + "RUN_PORT= "
//							+ portNumber);
					dockerdata.append("\n" + value);
				}

			}
		}
		// TEMPORARY PUT DATA
//		dockerdata.append("PRJ_NAME= " + prj + " \nDOCKER_USER=< DOCKER_USER >\r\n" + "DOCKER_PASS=< DOCKER_PASS >\r\n"
//				+ "DOCKER_URL=< DOCKER_URL >\r\n" + "RUN_PORT= " + portNumber);

		try {

//				Creating Folder
			String ref_path = "/SureOps" + File.separator + "docker_files" + addString;
			String dockerDir = projectPath + ref_path;
			File Dir = new File(dockerDir);
			if (!Dir.exists()) {
				Dir.mkdir();
			}

			System.out.println(dockerDir);

//			creating files
//				String dir2 = projectPath + "/docker_files" + addString + "/" + file_text3;
			String dir2 = dockerDir + "/" + file_text3;

			File file = new File(dir2);

			if (!file.exists()) {
				file.createNewFile();
			}
			System.out.println("file created successfully");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(dockerdata.toString());
			bw.close();

			// save file name and file path
			GetFile getFile = new GetFile();
			getFile.setFile_name(file_text3);
			getFile.setProfile_id(profile_id);
			getFile.setFile_path(ref_path + "/" + file_text3);
			fileRepository.save(getFile);

		} catch (IOException i) {

		}
	}

//NOT WORKING CURRENTLY
//		if (file_type.equalsIgnoreCase("script")) {
	public ResponseEntity<?> Workflow(String str, String prj, String portNumber) {
		Date d = new Date();
		String addString = "_" + d.getTime();
		String file_text2 = "test" + addString + ".script";
		StringBuilder yamldata = new StringBuilder();

		List<String> keys = new ArrayList<>();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);
		JsonArray Array = element.getAsJsonArray();

		for (JsonElement ar : Array) {
			JsonObject obj = ar.getAsJsonObject();
			Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
			for (Map.Entry<String, JsonElement> entry : entries) {
				String key = entry.getKey();
				String string = key.toString();
				String value = entry.getValue().getAsString();
				keys.add(value);
//			System.out.println("----------------key---" + key);
//			System.out.println("----------------value--" + value);

				if (key.equals("script")) {
					yamldata.append("PRJ_NAME= " + prj + " \nDOCKER_USER=< DOCKER_USER >\r\n"
							+ "DOCKER_PASS=< DOCKER_PASS >\r\n" + "DOCKER_URL=< DOCKER_URL >\r\n" + "RUN_PORT= "
							+ portNumber);
					yamldata.append("\n" + value);
				}

			}
		}
		try {

//				Creating Folder
			String ref_path = "/SureOps" + File.separator + "script_files" + addString;
			String scriptDir = projectPath + ref_path;
			File Dir = new File(scriptDir);
			if (!Dir.exists()) {
				Dir.mkdir();
			}

			System.out.println(scriptDir);

//			creating files
//				String dir2 = projectPath + "/script_files"+addString+"/" + file_text2;
			String dir2 = scriptDir + "/" + file_text2;

			File file = new File(dir2);

			if (!file.exists()) {
				file.createNewFile();
			}
			System.out.println("file created successfully");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(yamldata.toString());
			bw.close();

			// save file name and file path
			GetFile getFile = new GetFile();
			getFile.setFile_name(file_text2);
			getFile.setFile_path(ref_path + "/" + file_text2);
			fileRepository.save(getFile);

		} catch (IOException i) {

			return new ResponseEntity<>("not found script data", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(yamldata, HttpStatus.OK);

	}

}
