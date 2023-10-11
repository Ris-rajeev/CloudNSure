package com.realnet.codeextractor.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.codeextractor.entity.Build_controller;
import com.realnet.codeextractor.entity.Rn_Bcf_Extractor;
import com.realnet.codeextractor.entity.Rn_Bcf_Extractor_Params;
import com.realnet.codeextractor.repository.BuildControllerrepo;
import com.realnet.codeextractor.service.AngularService;
import com.realnet.codeextractor.service.Rn_Bcf_Extractor_Service;
import com.realnet.flf.entity.Rn_Bcf_Technology_Stack;
import com.realnet.flf.entity.Technology_element;
import com.realnet.flf.repository.Rn_Bcf_TechnologyStack_Repository;
import com.realnet.flf.repository.Techstack_elementRepo;
import com.realnet.fnd.entity.Error;
import com.realnet.fnd.entity.ErrorPojo;
import com.realnet.fnd.entity.Success;
import com.realnet.fnd.entity.SuccessPojo;
import com.realnet.utils.Constant;
import com.realnet.utils.Port_Constant;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Build Master Builder" })
public class MasterCodeBuild_gk {

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

	private static final Logger logger = LoggerFactory.getLogger(MasterCodeBuild_gk.class);

	@GetMapping("/build_master_builder")
	public ResponseEntity<?> masterControllerBuilder(@RequestParam(value = "id") Integer id)
			throws IOException, FileNotFoundException {

//		Path projectPath = FileSystems.getDefault().getPath("").toAbsolutePath();
		System.out.println("current path is : " + projectPath);

		Integer k = 1;
		RestTemplate restTemplate = new RestTemplate();
		String urlString = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.BACKEND_PORT_9191
				+ "/token/codeextractor/dynamic/dynamic_code_extraction?header_id=" + id;
		ResponseEntity<Object> entity = restTemplate.getForEntity(urlString, Object.class);
		System.out.println(entity.getBody());

		StringBuilder variablesDynamicCode = new StringBuilder();
		StringBuilder stringBuilderDynamicCode = new StringBuilder();

		// RN_BCF_CODE_EXTRACTOR_T ID
		// int eid = Integer.parseInt(id);

		Rn_Bcf_Extractor extractor = rn_bcf_extractor_service.getById(id);
		String technology_stack = extractor.getTech_stack();
		String object_type = extractor.getObject_type();
		String sub_object_type = extractor.getSub_object_type();
		String extractor_service = extractor.getService().toLowerCase();
		extractor.getSample_file_name().replace(".zip", "");
		String Service = "";
		String Totalproject_path = "";

		Rn_Bcf_Technology_Stack stack = technologyStack_Repository.findByTech_stack(technology_stack);

		List<Technology_element> el = elementRepo.findtechid(stack.getId());

		if (!el.isEmpty()) {

			for (Technology_element technology_element : el) {

				String ele_model = technology_element.getModel();

				JsonParser parser = new JsonParser();
				System.out.println(ele_model);
				String model = ele_model.toString();

				JsonElement element1 = parser.parse(model);
				JsonArray asJsonArray = element1.getAsJsonArray();
				for (JsonElement jsonElement : asJsonArray) {

					JsonObject object = jsonElement.getAsJsonObject();
					String title = object.get("title").getAsString().toLowerCase();
					if (title.contains(extractor_service)) {
						Service = title;

					}

				}

				String extension = "";
				// RN_BCF_CODE_EXTRACTOR_PARAMS_T VALUES
				List<Rn_Bcf_Extractor_Params> params = extractor.getRn_bcf_extractor_Params();

				int j = 0;
				for (Rn_Bcf_Extractor_Params param : params) {
					boolean is_creation_enabled = param.isIs_creation_enabled();
					param.getName_string().toLowerCase();
					Totalproject_path = param.getTotal_project_path_dynamic_string();

//			                param.isIs_extraction_enabled();
					String path = param.getMoved_address_string();
					File file = new File(path);
					String parentPath = file.getParent();

					String name = file.getName();
					String convertedFileName = "SE_" + name;

					// STATIC CODE DIRECTORY
					String staticFileParentDir = parentPath + File.separator + "static_code";
					File staticFile = new File(staticFileParentDir + File.separator + convertedFileName);
//					String total_address_path = param.getTotal_project_path_dynamic_string();
					String total_address_path = param.getReference_address_string();

					total_address_path = total_address_path.substring(0, total_address_path.lastIndexOf("/") + 1);
					System.out.println("total path : " + total_address_path + "\n");
					String finalDir = "";

					// HERE WE GET FILE INSIDE DATA //READ FILE
					String fileToString = FileUtils.readFileToString(staticFile, StandardCharsets.UTF_8);

					// ex. controller_file (FROM PARAMS TABLE)
					String f_name = param.getFile_name_var();
					int f_index = f_name.indexOf(".");
					String file_name_var = f_name.substring(0, f_index) + j;

					// ex. ui_name + "controller"
					String file_name_dynamic_string = param.getFile_name_dynamic_string().toLowerCase();
					String original_filename = param.getFile_name_dynamic_string();
//					int indexOf = original_filename.indexOf(".");
//					extension = original_filename.substring(indexOf).replaceAll("\"", "");

					String buildfilename = "object_name +" + "\"" + original_filename + "\"";

					// HERE WE CHECK AND APPEND FILE NAME
					if (!file_name_dynamic_string.isEmpty()) {
						variablesDynamicCode.append("String " + file_name_var + " = "
//					+ "object_name + " + "\""
								+ buildfilename + ";\r\n\n"
//								+ "copy_from =\"/data/cns-portal/code-extractor/builders/\"+proj_id+\"/\"+proj_id+\"/\"+repo_name+\""
//								+ total_address_path + "\";\n" + "		Copy_to_path =\n " + project_path + "		\n"
//								+ "		index_Service.build_index(proj_id,prj_name, j, copy_from,Copy_to_path);\n"
//								+ "j++;\n\n\n" + "		"
								+ "");

						System.out.println("file name dynamic str  " + file_name_dynamic_string);
					}

					else {
						System.out.println("not found");
					}

					// ======= MODULE NAME SHOULD COME FROM THE SESSION ========
					String moduleName = "\" + module_name + \"/";

					String modulePath = param.getTotal_project_path_dynamic_string();
					// System.out.println("MODULE PATH = " + modulePath);
					if (!modulePath.isEmpty()) {
						String parent = modulePath.substring(0, modulePath.lastIndexOf("/")); // 1
						String lvl2Parent = parent.substring(0, parent.lastIndexOf("/") + 1); // 2
						String tail0 = modulePath.substring(parent.lastIndexOf("/") + 1); // 3
						tail0 = tail0.substring(0, tail0.lastIndexOf("/") + 1); // remove the .java file
																				// name
						modulePath = lvl2Parent + moduleName + tail0;
					} else {

						System.out.println("modulePath not found");
					}

					// here we set file path
					String dest_path = "projectPath + \"/cns-portal/code-extractor/builders/\" + Path + \""
							+ total_address_path;
					System.out.println("dest path : " + dest_path);
					finalDir = dest_path + "\" + " + file_name_var;
					System.out.println(finalDir);

					finalDir = dest_path + "\" + " + file_name_var;
					System.out.println("NIL FINAL DIR = " + finalDir + "\n");

					if (is_creation_enabled) {
						StringBuilder fileCode = new StringBuilder();

						fileCode.append(" " + file_name_var + "Code.append(" + fileToString + ");\r\n");

						// EMPTY FILE CODE WILL NOT GO IN THIS LOOP

						stringBuilderDynamicCode
								.append(" StringBuilder " + file_name_var + "Code = new StringBuilder();\r\n"
								// + " " + file_name_var + "Code.append(" + fileToString + ");\r\n"
										+ fileCode + "\r\n"

										+ "	File " + file_name_var + "File = new File(" + finalDir + ");\r\n"
										+ "	System.out.println(\"Directory name = \" + " + file_name_var + "File);\r\n"
										+ ""
										// == CREATE PARENT DIR IF NOT EXIST===
										+ "	File " + file_name_var + "FileParentDir = new File(" + file_name_var
										+ "File.getParent());\r\n" + "	if(!" + file_name_var
										+ "FileParentDir.exists()) {\r\n" + "	" + file_name_var
										+ "FileParentDir.mkdirs();\r\n" + "			}\r\n"
										// ==
										+ "	if (!" + file_name_var + "File.exists()) {\r\n" + "				"
										+ file_name_var + "File.createNewFile();\r\n" + "			}\r\n"
										+ "			" + "fw = new FileWriter(" + file_name_var
										+ "File.getAbsoluteFile());\r\n" + "	bw = new BufferedWriter(fw);\r\n"
										+ "		" + "	bw.write(" + file_name_var + "Code.toString());\r\n"
										+ "	bw.close();\r\n	fw.close();" + "\r\n\n\n");

					}
					j++;

				}

				// CHILD MASTER BUILDER NAME DEPENDS ON (TECH_STACK, OBJ_TYPE, SUB_OBJ_TYPE)

				Date d = new Date();
				String time = "_" + d.getTime();

				String childMasterBuilderName = technology_stack + "_" + object_type + "_" + sub_object_type
						+ "_Builder";
				childMasterBuilderName = childMasterBuilderName.replace(" ", "_");
				childMasterBuilderName = childMasterBuilderName.replaceAll("[-]+", "_") + "_" + time + "_" + k;

				StringBuilder childMasterBuilderCode = new StringBuilder();
//		String action_builder_code = fieldTypeService.angular_action_builder_code();

				childMasterBuilderCode.append("package com.realnet.Builders;\n" + "\n"
						+ "import java.io.BufferedWriter;\n" + "import java.io.File;\n" + "import java.io.FileWriter;\n"
						+ "import java.io.IOException;\n" + "import java.util.ArrayList;\n"
						+ "import java.util.HashMap;\n" + "import java.util.Map.Entry;\n" + "import java.util.Set;\n"
						+ "\n" + "import org.springframework.beans.factory.annotation.Autowired;\n"
						+ "import org.springframework.beans.factory.annotation.Value;\n"
						+ "import org.springframework.http.HttpStatus;\n"
						+ "import org.springframework.http.MediaType;\n"
						+ "import org.springframework.http.ResponseEntity;\n"
						+ "import org.springframework.web.bind.annotation.GetMapping;\n"
						+ "import org.springframework.web.bind.annotation.PathVariable;\n"
						+ "import org.springframework.web.bind.annotation.RequestMapping;\n"
						+ "import org.springframework.web.bind.annotation.RequestParam;\n"
						+ "import org.springframework.web.bind.annotation.RestController;\n" + "\n"
						+ "import com.google.gson.JsonArray;\n" + "import com.google.gson.JsonElement;\n"
						+ "import com.google.gson.JsonObject;\n" + "import com.google.gson.JsonParser;\n"
						+ "import com.realnet.entitybuilder.Services.FileUploadService;\n"
						+ "import com.realnet.entitybuilder.entity2.wf_line;\n"
						+ "import com.realnet.entitybuilder.repo.Wf_linerepo;\n"
						+ "import com.realnet.entitybuilder.response.EntityResponse;\n"
						+ "import com.realnet.flf.service.Flf_WireframeService;\n"
						+ "import com.realnet.utils.RealNetUtils;\n" + "import io.swagger.annotations.Api;"

						+ "\r\n" + "@RestController\r\n"
						// CONTROLLER NAME SHOULD CHANGE
						// DEPENDS ON TECH_STACK/OBJECT_tYPE/SUB_OBJECT_TYPE
						+ "@RequestMapping(value = \"/token/api\", produces = MediaType.APPLICATION_JSON_VALUE)\r\n"
						+ "@Api(tags = { \"Master Builder\" })\r\n" + "public class " + childMasterBuilderName
						+ " {\r\n" + "\r\n" + "\r\n"

//								
						+ " @Value(\"${projectPath}\")\n" + "	private String projectPath;\n" + "\n"
						+ "	@Autowired\n" + "	private Flf_WireframeService flf_WireframeService;\n" + "\n"
						+ "	@Autowired\n" + "	private FileUploadService fileUploadService;\n" + "\n"
						+ "	@Autowired\n" + "	private Wf_linerepo wf_linerepo;\n"

						// DEPENDENCIES FOR WIREFRAME

						+ "@GetMapping(value = \"/" + childMasterBuilderName
//								+ "\")\n"
						+ "/{proj_id}/{prj_name}/{repo_name}/{packagename}/{gitname}\")\n"
						+ "	public ResponseEntity<?> createbyjson(@PathVariable Integer proj_id, @PathVariable String prj_name,\n"
						+ "			@PathVariable String repo_name, @PathVariable String packagename, @PathVariable String gitname,\n"
						+ "			@RequestParam Long wfdataid) throws IOException {\n" + ""

						// DON'T DELETE THIS COMMENTED CODE, ITS FOR FUTURE USE

						// SPECIFY PACKAGE NAME
						+ "	String Path = proj_id + \"/index/\" + prj_name + \"/\" + packagename + File.separator + gitname\n"
						+ "				+ \"" + Totalproject_path + "\"+\"/\" + repo_name;\n"

						+ "\n" + "		JsonParser parser = new JsonParser();\n" + "\n"
						+ "		wf_line wfdata = wf_linerepo.findById(wfdataid).get();\n"
						+ "		String model = wfdata.getModel();\n"
						+ "		Set<Entry<String, JsonElement>> rel_entryset = null;\n"
						+ "		String real_model = \"\";\n" + "\n"
						+ "		HashMap<String, String> entityname = new HashMap<>();\n" + "\n"
						+ "		System.out.println(\"model got \" + \"\\n\");\n" + "\n"
						+ "		JsonElement model_element = parser.parse(model);\n"
						+ "		JsonObject jsonObject = model_element.getAsJsonObject();\n" + "\n"
						+ "		String tab_name = jsonObject.get(\"name\").getAsString().replaceAll(\" \", \"_\");\n"
						+ "		String table_name = RealNetUtils.toFirstUpperCase(tab_name);\n" + "\n"
						+ "		JsonElement element2 = jsonObject.get(\"attributes\");\n"
						+ "		System.out.println(element2);\n" + "\n"
						+ "		JsonArray jsonArray = element2.getAsJsonArray();\n"
						+ "		System.out.println(jsonArray);\n" + "\n"
						+ "		for (JsonElement ar : jsonArray) {\n" + "\n"
						+ "			JsonObject obj1 = ar.getAsJsonObject();\n" + "\n"
						+ "			String field_value1 = obj1.get(\"label\").getAsString();\n"
						+ "			String field2 = field_value1.replaceAll(\" \", \"_\").toLowerCase();\n"
						+ "			System.out.println(field_value1);\n" + "\n"
						+ "			String type = obj1.get(\"type\").getAsString();\n"
						+ "			String data_type1 = type.replaceAll(\" \", \"_\");\n" + "\n"
						+ "			if (data_type1.contains(\"button\")) {\n" + "\n"
						+ "			} else if (field_value1.equals(\"OnetoOne\") || field_value1.equals(\"OnetoMany\")\n"
						+ "					|| field_value1.equals(\"ManytoMany\")) {\n"
						+ "				String totable = obj1.get(\"toWireframe\").getAsString();\n"
						+ "				real_model = obj1.get(\"model\").getAsString();\n" + "\n"
						+ "				field2 = totable;\n" + "				data_type1 = field_value1;\n"
						+ "				entityname.put(totable, field_value1);\n" + "\n" + "			}\n" + "\n"
						+ "			else {\n" + "				entityname.put(field2, data_type1);\n" + "\n"
						+ "			}\n" + "		}\n" + "\n" + "		String object_name = table_name;\n" + "\n"
						+ "		String relafield = real_model;\n" + "\n" + "		if (!relafield.isEmpty()) {\n"
						+ "			rel_entryset = parser.parse(relafield).getAsJsonObject().entrySet();\n" + "\n"
						+ "		}\n" + "		JsonElement element = parser.parse(entityname.toString());\n"
						+ "		JsonObject obj = element.getAsJsonObject();\n"
						+ "		Set<Entry<String, JsonElement>> entrySet = obj.entrySet();\n" + "\n"
						+ "		FileWriter fw = null;\n" + "		BufferedWriter bw = null;\n"
						+ variablesDynamicCode);
				childMasterBuilderCode.append(""

						// ACTION BUILDER CODE

						+ // =========== VARIABLE CODE WILL BE APPEND HERE ===============//
						"\n" + stringBuilderDynamicCode.toString() + "\r\n"
						+ "\n	return new ResponseEntity<>(new EntityResponse(\"File created\") , HttpStatus.CREATED);\r\n"
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

							try {
//								 insert in job pro

								insertin_jobpro("94", class_name);

							} catch (Exception e) {
							}

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
					build_controller.setService(Service);
					build_controller.setObject_type(object_type);
					build_controller.setSub_object_type(sub_object_type);
					build_controller.setTech_stack(technology_stack);
					build_controller.setFilepath(projectPath + builderpath);

					buildControllerrepo.save(build_controller);

					k++;

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

		} else {
			return new ResponseEntity<>("service is empty", HttpStatus.CREATED);

		}
	}

	public String UpdateEntity(String path, String classname) throws IOException {

//		String path = "C:\\Users\\Aniket\\Documents\\Entity.txt";
//		String classname = "gk";
		String addition = "for (int i = 0; i < entityname.size(); i++) {\r\n"
				+ "			String string = entityname.get(i);\r\n"
				+ "			String lowerCase = string.replaceAll(\" \", \"_\").toLowerCase();\r\n"
				+ "			String add = \"\\n private \" + \"String\" + \" \" + lowerCase + \";\";\r\n"
				+ "			intialize.append(add);\r\n" + "		}";
		/*
		 * RandomAccessFile writer=new
		 * RandomAccessFile("C:/Users/lenovo/Documents/demo.txt","rw");
		 *
		 * writer.seek(position); writer.writeBytes(addition); writer.close();
		 */
		String line = "";
		StringBuilder intialize = new StringBuilder();
		StringBuilder class_name = new StringBuilder();
		StringBuilder middle = new StringBuilder();
		StringBuilder end = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(path));
		while ((line = br.readLine()) != null) {
			String[] data = line.split(",");
			for (String d : data) {
				if (d.contains("import") || d.contains("@Data") || d.contains("@Entity")) {
					intialize.append("\"");
					intialize.append(d);
					intialize.append("\"+\n");
				} else if (d.contains("public")) {

					class_name.append("\"public class " + classname + " {\"+ \n");
//					intialize.append("\"+\n");
				} else if (d.contains("}")) {
					intialize.append("\"");
					end.append(d);
					intialize.append("\"+\n");
				}

			}
		}
		br.close();

		middle.append(addition);
		StringBuilder finalstring = new StringBuilder();
		finalstring.append("\"StringBuilder intialize = new StringBuilder();\"+\r\n" + "\" intialize.append(\"+");
		finalstring.append(intialize.toString());
		finalstring.append(class_name.toString());
		finalstring.append("\t\"@Id\"+\r\n" + "\"	@GeneratedValue(strategy = GenerationType.IDENTITY)\"+\r\n"
				+ "\"	private int id;\");\n");
		finalstring.append(middle.toString());

		return finalstring.toString();
	}

	public String UpdateRepo(String path, String classname) throws IOException {

		String line = "";
		StringBuilder intialize = new StringBuilder();
		StringBuilder class_name = new StringBuilder();
		new StringBuilder();
		new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(path));
		intialize.append("\"");

		while ((line = br.readLine()) != null) {
			String[] data = line.split(",");
			for (String d : data) {
				if (d.contains("import") || d.contains("@Repository")) {
					intialize.append("\"" + d);
					intialize.append("\"+\n");
				} else if (d.contains("public")) {
					class_name.append("\"" + "public class " + classname + " extends JpaRepository<" + classname
							+ ", Long> " + "{" + "\"+");
					class_name.append("\n");
				}

			}
		}
		br.close();

		StringBuilder finalstring = new StringBuilder();
		finalstring.append("	\"" + "	StringBuilder repo = new StringBuilder();" + "\"+\n" + "	\"" + "" + " "
				+ "repo.append(");
		finalstring.append(intialize.toString());
		finalstring.append(class_name.toString());
		finalstring.append("\"+}\"");
		finalstring.append("\");" + "\"");

		return finalstring.toString();
	}

	public String UpdateService(String path, String classname) throws IOException {

		String apiName = "Api";

		StringBuilder intialize = new StringBuilder();
		StringBuilder class_name = new StringBuilder();
		StringBuilder middle = new StringBuilder();
		new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(path));

		intialize.append("\"import java.util.List;\"+\r\n" + "\r\n"
				+ "\"import org.springframework.beans.factory.annotation.Autowired;\"+\r\n"
				+ "\"	import org.springframework.stereotype.Service;\"+\n");

		intialize.append("\n\"@Service\"+\n");

		intialize.append("\"@RequestMapping(value = " + "\"/" + apiName + "\")\"+\n" + "\"@RestController\"+\r\n");

		class_name.append("\"public class " + classname + "Service {\"+\r\n"

				+ "\"	@Autowired\"+\r\n" + "\"	private " + classname + "Service Service;\"+\n");

		middle.append("\" @Autowired\r\n" + "private " + classname + "Repository " + "Repository;\"+\n");

		middle.append("\"public " + classname + " Savedata(" + classname + " data) {\"+\r\n"
				+ "	\"			return Repository.save(data);\"+	\r\n" + "	\"		}\"+\r\n" + "\r\n"
				+ "	\"		\r\n" + "public List<" + classname + "> getdetails() {\"+\r\n"
				+ "	\"			return (List<" + classname + ">) Repository.findAll();\"+\r\n" + "			}\r\n"
				+ "\r\n" + "\r\n" + "\"public " + classname + " getdetailsbyId(Long id) {\"+\r\n"
				+ "\"	return Repository.findById(id).get();\"+\r\n" + "\"			}\"+\r\n" + "\r\n" + "\r\n"
				+ "\"	public void delete_by_id(Long id) {\"+\r\n" + "\" Repository.deleteById(id);\"+\r\n"
				+ "}\"+\r\n" + "\r\n" + "\r\n");

		middle.append("\"public " + classname + " update(" + classname + " data,Long id) {\"+\n" + "	\"" + classname
				+ " old = Repository.findById(id).get();\"+\n");
//			middle.append("	for (EntityBuild en : ent) {
//				String name = en.getName();
//				
//				String string = name.substring(0,1).toUpperCase()+name.substring(1);
//				middle.append("old.set"+string+ "(data.get"+string+"());\r\n");
//			}
		middle.append("\"final " + classname + " test = Repository.save(old);\"+\r\n" + "	\"	return test;\"+"
				+ "\"}\"+" + "\"}\"+");

		br.close();

		StringBuilder finalstring = new StringBuilder();
		finalstring.append("\"		StringBuilder service = new StringBuilder();\"+\r\n" + "\" service.append(\"+");
		finalstring.append(intialize.toString());
		finalstring.append(class_name.toString());
		finalstring.append(middle.toString());
		finalstring.append("\");");

		//
//		FileWriter fw = null;
//		BufferedWriter bw = null;
//		File masterBuilderFile = new File(newpath + "test" + ".java");
//		if (!masterBuilderFile.exists()) {
//			masterBuilderFile.createNewFile();
//		}
//		fw = new FileWriter(masterBuilderFile.getAbsoluteFile());
//		bw = new BufferedWriter(fw);
//		bw.write(finalstring.toString());
//		bw.close();

		return finalstring.toString();
	}

	public String UpdateController(String path, String classname) throws IOException {

		String apiName = "Api";

		StringBuilder intialize = new StringBuilder();
		StringBuilder class_name = new StringBuilder();
		StringBuilder middle = new StringBuilder();
		new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(path));

		intialize.append("import org.springframework.beans.factory.annotation.Autowired;\"+\r\n"
				+ "\" import org.springframework.web.bind.annotation.DeleteMapping;\"+\r\n"
				+ "\" import org.springframework.web.bind.annotation.GetMapping;\"+\r\n"
				+ "\" import org.springframework.web.bind.annotation.PathVariable;\"+\r\n"
				+ "\" import org.springframework.web.bind.annotation.PostMapping;\"+\r\n"
				+ "\" import org.springframework.web.bind.annotation.PutMapping;\"+\r\n"
				+ "\" import org.springframework.web.bind.annotation.RequestBody;\"+\r\n"
				+ "\" import org.springframework.web.bind.annotation.RequestParam;\"+\r\n"
				+ "\" import org.springframework.web.bind.annotation.RestController;\"+\r\n"
				+ "\" import org.springframework.web.bind.annotation.*;\"+\r\n");

		intialize.append("\" @RequestMapping(value = " + "\"/" + apiName + "\")\"+\n" + "\" @RestController \"+\r\n ");
		class_name.append("\" public class " + classname + "Controller {\"+\r\n"

				+ "\"	@Autowired \"+ \r\n" + "\"	private " + classname + "Service Service; \"+\n");

		middle.append("\"	@PostMapping(" + "\"/" + classname + ")\"+\r\n" + "	\r\n" + "	\"  public " + classname
				+ " Savedata(@RequestBody " + classname + " data) { \"+ \r\n" + "	\"	" + classname
				+ " save = Service.Savedata(data)	;\"+\r\n" + "	\"	 return save;\"+\r\n" + "	 \"+ }\"+\r\n"
//				+ "	\"	 \r\n \"+"
//				+ "	\" \r\n \"+"
				+ "	\" @GetMapping(\"/" + classname + "\")\"+\r\n" + "	\" public List<" + classname
				+ "> getdetails() { \"+ \r\n" + "	\"	 List<" + classname
				+ "> get = Service.getdetails();	\"+	\r\n" + "	\"	return get;\"+\r\n\" } \"+\n"
				+ "\" @GetMapping(\"/" + classname + "/{id}\")\"+\r\n" + " \"	public  " + classname
				+ "  getdetailsbyId(@PathVariable Long id ) {\"+\r\n" + "	\"	" + classname
				+ "  get = Service.getdetailsbyId(id);\"+\r\n" + "\"		return get;\"+\r\n" + "	\" }\"+\n"
				+ "\" @DeleteMapping(\"/" + classname + "/{id}\")\"+\r\n"
				+ "	\" public  void delete_by_id(@PathVariable Long id ) {\"+\r\n"
				+ "	\" Service.delete_by_id(id);\"+\r\n"
//				+ "	\" \"+	\r\n"
				+ "	\"\"+ }\n" + "\" @PutMapping(\"/" + classname + "/{id}\")\"+\r\n" + "\"	public  " + classname
				+ " update(@RequestBody " + classname + " data,@PathVariable Long id ) {\"+\r\n" + "	\"	"
				+ classname + " update = Service.update(data,id);\"+\r\n" + "\"		return update;\"+\r\n"
				+ "\"	}\"+\n\"}\"+");

		br.close();

		StringBuilder finalstring = new StringBuilder();
		finalstring.append("\"		StringBuilder controller = new StringBuilder();\"+\r\n" + "\"controller.append(");
		finalstring.append(intialize.toString());
		finalstring.append(class_name.toString());
		finalstring.append(middle.toString());
//		finalstring.append("}");
		finalstring.append("\");");

		//
//		FileWriter fw = null;
//		BufferedWriter bw = null;
//		File masterBuilderFile = new File(newpath + "test" + ".java");
//		if (!masterBuilderFile.exists()) {
//			masterBuilderFile.createNewFile();
//		}
//		fw = new FileWriter(masterBuilderFile.getAbsoluteFile());
//		bw = new BufferedWriter(fw);
//		bw.write(finalstring.toString());
//		bw.close();

		return finalstring.toString();

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
