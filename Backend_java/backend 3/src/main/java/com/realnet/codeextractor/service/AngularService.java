package com.realnet.codeextractor.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.realnet.codeextractor.entity.Build_controller;
import com.realnet.codeextractor.entity.Rn_Bcf_Extractor;
import com.realnet.codeextractor.entity.Rn_Bcf_Extractor_Params;
import com.realnet.codeextractor.repository.BuildControllerrepo;
import com.realnet.flf.repository.Rn_Bcf_TechnologyStack_Repository;
import com.realnet.flf.repository.Techstack_elementRepo;
import com.realnet.fnd.entity.Error;
import com.realnet.fnd.entity.ErrorPojo;
import com.realnet.fnd.entity.Success;
import com.realnet.fnd.entity.SuccessPojo;
import com.realnet.utils.Constant;
import com.realnet.utils.Port_Constant;

@Service
public class AngularService {

	@Value("${projectPath}")
	private String projectPath;

	@Autowired
	private Rn_Bcf_Extractor_Service rn_bcf_extractor_service;

	@Autowired
	private Rn_Bcf_TechnologyStack_Repository technologyStack_Repository;

	@Autowired
	private Techstack_elementRepo elementRepo;

	@Autowired
	private BuildControllerrepo buildControllerrepo;

	public ResponseEntity<?> buildangularcontroller(Rn_Bcf_Extractor extractor, Integer k)
			throws IOException, FileNotFoundException {

		StringBuilder variablesDynamicCode = new StringBuilder();
		StringBuilder stringBuilderDynamicCode = new StringBuilder();

		String technology_stack = extractor.getTech_stack();
		String object_type = extractor.getObject_type();
		String sub_object_type = extractor.getSub_object_type();
		String package_name = extractor.getSample_file_name().replace(".zip", "");
		List<Rn_Bcf_Extractor_Params> params = extractor.getRn_bcf_extractor_Params();

		int j = 0;
		String file_name = "";
		for (Rn_Bcf_Extractor_Params param : params) {
			boolean is_creation_enabled = param.isIs_creation_enabled();
			file_name = param.getName_string().toLowerCase();
			if (file_name.contains(".html") || file_name.contains(".ts") || file_name.contains("scss")) {

//			param.isIs_extraction_enabled();
				String path = param.getMoved_address_string();
				File file = new File(path);
				String parentPath = file.getParent();

				String name = file.getName();
				String convertedFileName = "SE_" + name;

				// STATIC CODE DIRECTORY
				String staticFileParentDir = parentPath + File.separator + "static_code";
				File staticFile = new File(staticFileParentDir + File.separator + convertedFileName);

				// HERE WE GET FILE INSIDE DATA //READ FILE
				String fileToString = FileUtils.readFileToString(staticFile, StandardCharsets.UTF_8);

				// ex. controller_file (FROM PARAMS TABLE)
				String file_name_var = param.getFile_name_var().replace(".component.", "_").replaceAll(".service.", "_")
						+ j;

				String file_name_dynamic_string = param.getFile_name_dynamic_string().toLowerCase();
				String original_filename = param.getFile_name_dynamic_string().toLowerCase();

				// HERE WE CHECK AND APPEND FILE NAME
				if (file_name_dynamic_string.contains(".ts")) {
					variablesDynamicCode.append("String " + file_name_var + " = " + "angular_class + " + "\""
							+ original_filename + "\"" + ";\r\n");

					System.out.println("file name dynamic str  " + file_name_dynamic_string);
				} else if (file_name_dynamic_string.contains(".html")) {
					variablesDynamicCode.append("String " + file_name_var + " = " + "angular_class + " + "\""
							+ original_filename + "\"" + ";\r\n");

					System.out.println("file name dynamic str  " + file_name_dynamic_string);

				} else if (file_name_dynamic_string.contains(".scss")) {
					variablesDynamicCode.append("String " + file_name_var + " = " + "angular_class + " + "\""
							+ original_filename + "\"" + ";\r\n");

					System.out.println("file name dynamic str  " + file_name_dynamic_string);
				}

				else {
					System.out.println("not found");
				}

				// ======= MODULE NAME SHOULD COME FROM THE SESSION ========
				String moduleName = "\" + module_name + \"/";

				String modulePath = param.getTotal_project_path_dynamic_string();
				System.out.println("MODULE PATH = " + modulePath);
				if (modulePath.endsWith(".java")) {
					String parent = modulePath.substring(0, modulePath.lastIndexOf("/")); // 1
					String lvl2Parent = parent.substring(0, parent.lastIndexOf("/") + 1); // 2
					String tail0 = modulePath.substring(parent.lastIndexOf("/") + 1); // 3
					tail0 = tail0.substring(0, tail0.lastIndexOf("/") + 1); // remove the .java file name
					modulePath = lvl2Parent + moduleName + tail0;
				} else {
					// add module name in spring project
					modulePath = modulePath.substring(0, modulePath.lastIndexOf("/") + 1);
					String data0 = modulePath.substring(0, modulePath.lastIndexOf("/") + 1);
					String tail0 = modulePath.substring(modulePath.lastIndexOf("/") + 1);
					data0 += moduleName + tail0;
					modulePath = data0;
				}
//			System.out.println("MANUPULATED module PATH = " + modulePath);

				String total_address_path = param.getTotal_project_path_dynamic_string();
				total_address_path = total_address_path.substring(0, total_address_path.lastIndexOf("/") + 1);
				System.out.println("total path : " + total_address_path + "\n");
				String finalDir = "";

//	
				// here we set file path
				String dest_path = "projectPath + \""
						+ "/cns-portal/code-extractor/builders/\"+ proj_id + \"/\"+proj_id+ \"/\" + project_name +\"/\"+ angular_class+ \"_ui\" +\""
						+ total_address_path;
				System.out.println("dest path : " + dest_path);
				// String finalDir = dirString + "/" + "\" + " + file_name_var;
				finalDir = dest_path + "\" + " + file_name_var;
				System.out.println(finalDir);

				finalDir = dest_path + "\" + " + file_name_var;
				System.out.println("NIL FINAL DIR = " + finalDir + "\n");

				if (is_creation_enabled) {
					StringBuilder fileCode = new StringBuilder();

					fileCode.append(" " + file_name_var + "Code.append(\"\\" + fileToString + ");\r\n");

					// EMPTY FILE CODE WILL NOT GO IN THIS LOOP

					stringBuilderDynamicCode.append(
							" StringBuilder " + file_name_var + "Code = new StringBuilder();\r\n" + fileCode + "\r\n"

									+ "	File " + file_name_var + "File = new File(" + finalDir + ");\r\n"
									+ "	System.out.println(\"Directory name = \" + " + file_name_var + "File);\r\n" + ""
									// == CREATE PARENT DIR IF NOT EXIST===
									+ "	File " + file_name_var + "FileParentDir = new File(" + file_name_var
									+ "File.getParent());\r\n" + "	if(!" + file_name_var
									+ "FileParentDir.exists()) {\r\n" + "	" + file_name_var
									+ "FileParentDir.mkdirs();\r\n" + "			}\r\n"
									// ==
									+ "	if (!" + file_name_var + "File.exists()) {\r\n" + "				"
									+ file_name_var + "File.createNewFile();\r\n" + "			}\r\n" + "			"
									+ "fw = new FileWriter(" + file_name_var + "File.getAbsoluteFile());\r\n"
									+ "	bw = new BufferedWriter(fw);\r\n" + "		" + "	bw.write(" + file_name_var
									+ "Code.toString());\r\n" + "	bw.close();\r\n	fw.close();" + "\r\n");

				}
				j++;
			}
		}

		// CHILD MASTER BUILDER NAME DEPENDS ON (TECH_STACK, OBJ_TYPE, SUB_OBJ_TYPE)

		if (file_name.contains(".html") || file_name.contains(".ts") || file_name.contains("scss")) {
			Date d = new Date();
			String time = "_" + d.getTime();

			String childMasterBuilderName = technology_stack + "_" + object_type + "_" + sub_object_type + "_Builder";
			childMasterBuilderName = childMasterBuilderName.replace(" ", "_");
			childMasterBuilderName = childMasterBuilderName.replaceAll("[-]+", "_") + "_" + time + "_" + k;

			StringBuilder childMasterBuilderCode = new StringBuilder();
//		String action_builder_code = fieldTypeService.angular_action_builder_code();

			childMasterBuilderCode.append("package com.realnet.Builders;\r\n" + "\r\n"
					+ "import java.io.BufferedWriter;\r\n" + "import java.io.File;\r\n"
					+ "import java.io.FileWriter;\r\n" + "import java.io.IOException;\r\n"
					+ "import java.util.ArrayList;\r\n" + "import java.util.Date;\r\n" + "import java.util.List;\r\n"
					+ "import java.util.Optional;\r\n" + "\r\n" + "import org.modelmapper.ModelMapper;\r\n"
					+ "import java.nio.file.FileSystems;\n" + "import java.nio.file.Path;\n"
					+ "import java.util.Map.Entry;\n" + "import java.util.Set;\n" + "import java.util.HashMap;\n"
					+ "import org.springframework.beans.factory.annotation.Autowired;\r\n"
					+ "import org.springframework.beans.factory.annotation.Value;\r\n"
					+ "import org.springframework.http.HttpStatus;\r\n"
					+ "import org.springframework.http.MediaType;\r\n"
					+ "import org.springframework.http.ResponseEntity;\r\n"
					+ "import org.springframework.web.bind.annotation.GetMapping;\r\n"
					+ "import org.springframework.web.bind.annotation.PathVariable;\r\n"
					+ "import org.springframework.web.bind.annotation.RequestMapping;\r\n"
					+ "import org.springframework.web.bind.annotation.RestController;\r\n"
					+ "import org.springframework.web.bind.annotation.RequestParam;\n" + "" + "\r\n"
					+ "import com.google.gson.JsonElement;\r\n" + "import com.google.gson.JsonObject;\r\n"
					+ "import com.google.gson.JsonParser;\r\n" + "import com.google.gson.JsonArray;\r\n"
					+ "import com.fasterxml.jackson.databind.ObjectMapper;\n" + ""
//							
					+ "import com.realnet.entitybuilder.response.EntityResponse;\n" + ""
					+ "import io.swagger.annotations.Api;"

					+ "\r\n" + "@RestController\r\n"
					// CONTROLLER NAME SHOULD CHANGE
					// DEPENDS ON TECH_STACK/OBJECT_tYPE/SUB_OBJECT_TYPE
					+ "@RequestMapping(value = \"/token/api\", produces = MediaType.APPLICATION_JSON_VALUE)\r\n"
					+ "@Api(tags = { \"Master Builder\" })\r\n" + "public class " + childMasterBuilderName + " {\r\n"
					+ "\r\n" + "\r\n"

					+ " @Value(\"${projectPath}\")\r\n" + "	private String projectPath;\n"

					+ "@GetMapping(value = \"/" + childMasterBuilderName
//								+ "\")\n"
					+ "/{proj_id}/{repo_name}/{table_name}\")\r\n" + "	public ResponseEntity<?> createbyjson("
					+ "@PathVariable Integer proj_id,"
					+ "@PathVariable String repo_name,@PathVariable String table_name,"
					+ "@RequestParam String entityname) throws IOException {\r\n"

					+ "		// CALL BACKEND\r\n"

					// SPECIFY PACKAGE NAME
					+ "		String project_name = repo_name;\n" + "		String angular_class =table_name;\n"
					+ "	entityname = entityname.toString().replace(\"@\", \"{\").replace(\"$\", \"}\").replaceAll(\"\\\"\", \"\");\n"
					+ "\n" + "		JsonParser parser = new JsonParser();\n"
					+ "		JsonElement element = parser.parse(entityname);\n"
					+ "		JsonObject obj = element.getAsJsonObject();\n"
					+ "		Set<Entry<String, JsonElement>> entrySet = obj.entrySet();\n"

					+ "\r\r\rFileWriter fw = null;\r\n" + "BufferedWriter bw = null;\r\n" + variablesDynamicCode);
			childMasterBuilderCode.append(""

					// ACTION BUILDER CODE

					+ // =========== VARIABLE CODE WILL BE APPEND HERE ===============//
					"\n" + stringBuilderDynamicCode.toString() + "\r\n"
					+ "\n	return new ResponseEntity<>(new EntityResponse(\"Angular file created\"), HttpStatus.CREATED);\r\n"
					+ "}\r\n }");

//		
			FileWriter fw = null;
			BufferedWriter bw = null;
			try {
				// FILE NAME SHOULD CHANGE DEPENDS ON TECH_STACK/OBJECT_tYPE/SUB_OBJECT_TYPE
				String builderpath = "/cns-portal/code-extractor/builders/";
				String class_name = childMasterBuilderName + ".java";

				String CreateFile_path = projectPath + builderpath + class_name;

				System.out.println("create file path is : " + CreateFile_path);

				File masterBuilderFile = new File(CreateFile_path);
				if (!masterBuilderFile.exists()) {
					boolean createNewFile = masterBuilderFile.createNewFile();
					if (createNewFile) {

						// INSERT IN JOB PRO
						insertin_jobpro("94", class_name);
						System.out.println(" builder file created : " + createNewFile);
					} else {
						System.out.println("builder file not created  path is : " + builderpath);
					}
				}
				fw = new FileWriter(masterBuilderFile.getAbsoluteFile());
				bw = new BufferedWriter(fw);
				bw.write(childMasterBuilderCode.toString());
				bw.close();

				// SAVE END POINT OF CONTROLLER CLASS
				Build_controller build_controller = new Build_controller();
				build_controller.setApi_endpoint(childMasterBuilderName);
				build_controller.setRn_bcf_extractor(extractor);

				buildControllerrepo.save(build_controller);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				ErrorPojo errorPojo = new ErrorPojo();
				Error error = new Error();
				error.setTitle(Constant.MASTER_BUILDER_API_TITLE);
				error.setMessage(Constant.MASTER_BUILDER_FAILURE);
				errorPojo.setError(error);
				return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
			}
		}

		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.MASTER_BUILDER_API_TITLE);
		success.setMessage(Constant.MASTER_BUILDER_SUCCESS);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.CREATED);

	}

	// INSERT IN JOB PRO TO insert in app builder
	public void insertin_jobpro(String prj_id, String java_class) throws JsonProcessingException {

		Map<String, String> jobdata = new HashMap<String, String>();

		String job_url = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.APP_BUILD_19001
				+ "/entityBuilder/builder/" + prj_id + "/" + java_class;

		jobdata.put("url", job_url);
		jobdata.put("method", "GET");
		jobdata.put("connection_name", "jobprtal");
		jobdata.put("createdBy", "2022");
		jobdata.put("updatedBy", "2022");
		jobdata.put("job_type", "app builder");
		jobdata.put("ref", prj_id.toString());
		System.out.println(jobdata);

		RestTemplate restTemplate = new RestTemplate();
		String jobprourl = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.JOBPRO_PORT_8087
				+ "/jobpro/pipiline";
		HttpHeaders headers = getHeaders();
		HttpEntity<Object> request = new HttpEntity<Object>(jobdata, headers);
		ResponseEntity<Object> res1 = restTemplate.postForEntity(jobprourl, request, Object.class);
		if (res1.getStatusCodeValue() == 200) {
			System.out.println("script runner data inserted in job pro");

		}
		System.out.println(res1.getBody());
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}

}
