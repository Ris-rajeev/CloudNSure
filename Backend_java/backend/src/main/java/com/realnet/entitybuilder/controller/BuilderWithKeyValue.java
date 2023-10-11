//package com.realnet.entitybuilder.controller;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.realnet.entitybuilder.Servie.BackendBuilderService;
//import com.realnet.entitybuilder.Servie.FrontEndBuilderService;
//import com.realnet.entitybuilder.Servie.Script_Making;
//import com.realnet.entitybuilder.response.EntityResponse;
//import com.realnet.utils.Constant;
//import com.realnet.utils.Port_Constant;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RestController
//@RequestMapping(value = "/entityBuilder", produces = MediaType.APPLICATION_JSON_VALUE)
//public class BuilderWithKeyValue {
//	
//	@Value("${projectPath}")
//	private String projectPath;
//
//	@Autowired
//	private FrontEndBuilderService frontendservice;
//
//	@Autowired
//	private BackendBuilderService backendservice;
//	
//	@Autowired
//	private Script_Making script_serviceMaking;
//	
//	
//	
//	@GetMapping(value = "/BuildByProject/{id}")
//	public ResponseEntity<?> ADDINJOBPRO(@PathVariable Integer id)
//			throws UnsupportedEncodingException, JsonProcessingException {
//
//		// ADD DATA IN JOB PRO
//		Map<String, String> jobprodata = new HashMap<String, String>();
//
//		jobprodata.put("url", "http://"+Port_Constant.LOCAL_HOST+":"+Port_Constant.APP_BUILD_19001+"/entityBuilder/BuildByProject1/"+id);
//		jobprodata.put("method", "GET");
//		jobprodata.put("connection_name", "jobprtal");
//		jobprodata.put("createdBy", "2022");
//		jobprodata.put("updatedBy", "2022");
//		jobprodata.put("job_type", "build_app");
//		jobprodata.put("ref", id.toString());
//		System.out.println(jobprodata);
//
//		RestTemplate restTemplate = new RestTemplate();
//		String jobprourl2 = "http://"+Port_Constant.LOCAL_HOST+":"+Port_Constant.JOBPRO_PORT_8087+"/api/pipiline";
//		HttpHeaders headers2 = getHeaders();
//		HttpEntity<Object> request2 = new HttpEntity<Object>(jobprodata, headers2);
//
//		ResponseEntity<Object> res2 = restTemplate.postForEntity(jobprourl2, request2, Object.class);
//		System.out.println(res2.getStatusCodeValue());
//
//		if (res2.getStatusCodeValue() == 200) {
//			System.out.println("data inserted in job pro");
//			System.out.println(res2.getBody());
//
//		}
//		return new ResponseEntity<>("data inserted in job pro", HttpStatus.OK);
//	}
//
//
//	// BUILD ALL BY PROJECT ID
//	@GetMapping(value = "/BuildByProject1/{proj_id}")
//	public ResponseEntity<?> BuildByProject(@PathVariable Integer proj_id) throws IOException {
//
//		int i = 0;
//		int j = 1;
//
//		String prj_url = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.BACKEND_PORT_9191
//				+ "/token/fnd1/callingsureops/getproject/" + proj_id;
//
//		System.out.println(prj_url+"\n");
//		// get project
//		ResponseEntity<Object> prj = GET(prj_url);
//		Object prj_body = prj.getBody();
//
//		List<String> list = new ArrayList<>();
//		ObjectMapper mapper = new ObjectMapper();
//		String str = mapper.writeValueAsString(prj_body);
////		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(prj_body));// print
//		JsonParser parser = new JsonParser();
//		JsonElement element = parser.parse(str);
//
//		JsonObject obj = element.getAsJsonObject();
//		String prj_prefix = obj.get("projectPrefix").getAsString();
//		
//		JsonElement modules = obj.get("modules");
////		System.out.println("module " +modules+"\n");
//
//		JsonArray module_list = modules.getAsJsonArray();
//
////		Optional<Rn_Project_Setup> project = projectSetup_Repository.findById(id);
////		List<Rn_Module_Setup> modules = project.get().getModules();
//
//		if (!modules.isJsonNull()) {
//
//			for (JsonElement modue : module_list) {
//				JsonObject module = modue.getAsJsonObject();
//				JsonElement fb_header = module.get("rn_fb_headers");
//				JsonArray rn_fb_headers = fb_header.getAsJsonArray();
////			List<Rn_Fb_Header> rn_fb_headers = module.getRn_fb_headers();
//				if (!rn_fb_headers.isJsonNull()) {
//
//					for (JsonElement header : rn_fb_headers) {
////			for(Rn_Fb_Header header: rn_fb_headers) {
//
//						
//						JsonObject header_obj = header.getAsJsonObject();
//						boolean build = header_obj.get("build").getAsBoolean();
//						String header_id = header_obj.get("id").toString();
////				if (!header.isBuild()) {
////					if (!build) {
//
//						String wf_url = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.BACKEND_PORT_9191
//								+ "/token/fnd1/callingsureops/wfline/" + header_id;
//						System.out.println("wf url "+wf_url +"\n");
//						try {
//							
//						
//						ResponseEntity<Object> wf_get = GET(wf_url);
//						
////					if (wireframe.isPresent()) {
//						if (wf_get.hasBody()) {
//
//							String Wf_str = mapper.writeValueAsString(wf_get.getBody());
//							JsonElement wf_element = parser.parse(Wf_str);
//
//							JsonObject wf_obj = wf_element.getAsJsonObject();
//							String model = wf_obj.get("model").getAsString();
//
////					Optional<Rn_wf_lines_3> wireframe = repo.findheader(header.getId());
//							i++;
//							List<String> tablename = new ArrayList<>();
//							List<String> entityname = new ArrayList<>();
//
//							JsonElement model_element = parser.parse(model);
//							JsonObject jsonObject = model_element.getAsJsonObject();
//
//							JsonElement name = jsonObject.get("name");
////							System.out.println(name);
//							tablename.add(name.getAsString());
//
//							JsonElement desc = jsonObject.get("description");
////							System.out.println(desc);
////					keys.add("desc :"+desc.getAsString());
//
//							JsonElement element2 = jsonObject.get("attributes");
////							System.out.println(element2);
//
//							JsonArray jsonArray = element2.getAsJsonArray();
//							System.out.println(jsonArray);
//
//							for (JsonElement ar : jsonArray) {
//
//								JsonObject obj1 = ar.getAsJsonObject();
//
//								JsonElement type = obj1.get("type");
////								System.out.println(type);
//
//								JsonElement description = obj1.get("description");
////								System.out.println(description);
//
//								JsonElement placeholder = obj1.get("placeholder");
//
//								JsonElement label = obj1.get("label");
//								System.out.println(label);
//								entityname.add(label.getAsString());
//
//							}
//
//							Date d = new Date();
//							String addString = "_t";
////							String addString = "_"+d.getTime();
//
//							if (j==1) {
//								script_serviceMaking.CreateFiles(proj_id, tablename.get(0),addString,j);
//
//							}
//							// CALL BACKEND
//							System.out.println("call backend "+"\n");
//							boolean buildbackend = backendservice.buildbackend(proj_id,tablename, entityname, addString,j,prj_prefix);
////							if (buildbackend) {
////								header.setBuild(true);
////								header_Repository.save(header);
////							}
//							j++;
////							// CALL FRONTEND
//							System.out.println("call frontend"+"\n");
//							frontendservice.buildFrontend(proj_id,tablename, entityname, addString,j);
//							
//							
//							j++;
//							
//							
//							
//
//						}
//						} catch (Exception e) {
//							// TODO: handle exception
//						}
////				}
//
//					}
//				} else {
//					return new ResponseEntity<>(new EntityResponse("header is empty"), HttpStatus.BAD_REQUEST);
//
//				}
//
//			}
//		} else {
//			return new ResponseEntity<>(new EntityResponse("modules is empty"), HttpStatus.BAD_REQUEST);
//
//		}
//		
//		String filepath = projectPath + File.separator + "Builder" + File.separator + proj_id;
//		
//		insertin_jobpro_delete(proj_id,  filepath);
//
//		return new ResponseEntity<>(new EntityResponse(i + " wireframe build"), HttpStatus.CREATED);
//	}
//
//	// WIREFRAME BUILD BY HEADER ID
//	@GetMapping(value = "/json/{header_id}")
//	public ResponseEntity<?> createbyjson(@PathVariable Integer header_id) throws IOException {
//
//		int j =1;
//		String wf_url = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.BACKEND_PORT_9191
//				+ "/token/fnd1/callingsureops/wfline/" + header_id;
//		System.out.println("wf url "+wf_url +"\n");
//
//		ResponseEntity<Object> wf_get = GET(wf_url);
//		ObjectMapper mapper = new ObjectMapper();
//		JsonParser parser = new JsonParser();
//
//		String Wf_str = mapper.writeValueAsString(wf_get.getBody());
//		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(wf_get.getBody()));// print
//		JsonElement wf_element = parser.parse(Wf_str);
//
//		JsonObject wf_obj = wf_element.getAsJsonObject();
//		String model = wf_obj.get("model").getAsString();
//
////		Optional<Rn_wf_lines_3> wireframe = repo.findheader(header_id);
//
//		List<String> tablename = new ArrayList<>();
//		List<String> entityname = new ArrayList<>();
//
//		JsonElement element = parser.parse(model);
//		JsonObject jsonObject = element.getAsJsonObject();
//
//		JsonElement name = jsonObject.get("name");
//		System.out.println(name);
//		tablename.add(name.getAsString());
//
//		JsonElement desc = jsonObject.get("description");
//		System.out.println(desc);
////		keys.add("desc :"+desc.getAsString());
//
//		JsonElement element2 = jsonObject.get("attributes");
//		System.out.println(element2);
//
//		JsonArray jsonArray = element2.getAsJsonArray();
//		System.out.println(jsonArray);
//
//		for (JsonElement ar : jsonArray) {
//
//			JsonObject obj = ar.getAsJsonObject();
//
//			JsonElement type = obj.get("type");
//			System.out.println(type);
////			keys.add("type :"+type.getAsString());
//
//			JsonElement description = obj.get("description");
//			System.out.println(description);
////			keys.add("description :"+description.getAsString());
//
//			JsonElement placeholder = obj.get("placeholder");
////			System.out.println(placeholder);
////			keys.add("placeholder :"+placeholder.getAsString());
//
//			JsonElement label = obj.get("label");
//			System.out.println(label);
//			entityname.add(label.getAsString());
//
//		}
//
//		Date d = new Date();
////		String addString = "_"+d.getTime();
//		String addString = "_t";
//
//
//		// CALL BACKEND
//		System.out.println("call backend "+"\n");
//		backendservice.buildbackend(94,tablename, entityname, addString,j,"aa");
//
////		// CALL FRONTEND
//		System.out.println("call frontendservice "+"\n");
//		frontendservice.buildFrontend(94,tablename, entityname, addString,j);
//		
//		//call deploy
//		script_serviceMaking.CreateFiles(94, tablename.get(0),addString,j);
//
//
//		return new ResponseEntity<>(new EntityResponse("entity created"), HttpStatus.CREATED);
//
//	}
//	
//	// INSERT IN JOB PRO TO DELETE FOLDER
//		public void insertin_jobpro_delete(Integer prj_id, String filepath) throws JsonProcessingException {
//
//			Map<String, String> jobdata = new HashMap<String, String>();
////				jobdata.put("parameters", builder.toString());
//			jobdata.put("url", "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.SUREOPS_PORT_9090
//					+ "/sureops/file_delete/delete_folder?filepath=" + filepath);
//			jobdata.put("method", "GET");
//			jobdata.put("connection_name", "jobprtal");
//			jobdata.put("createdBy", "2022");
//			jobdata.put("updatedBy", "2022");
//			jobdata.put("job_type", "deletefolder");
//			jobdata.put("ref", prj_id.toString());
//			System.out.println(jobdata);
//
//			RestTemplate restTemplate = new RestTemplate();
//			String jobprourl = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.JOBPRO_PORT_8087 + "/api/pipiline";
//			HttpHeaders headers = getHeaders();
//			HttpEntity<Object> request = new HttpEntity<Object>(jobdata, headers);
//			ResponseEntity<Object> res1 = restTemplate.postForEntity(jobprourl, request, Object.class);
//			if (res1.getStatusCodeValue() == 200) {
//				System.out.println(" for delete folder data inserted in job pro");
//			}
////			System.out.println(res1.getBody());
//		}
//
//	public ResponseEntity<Object> GET(String get) {
//		RestTemplate restTemplate = new RestTemplate();
//
//		ResponseEntity<Object> u = restTemplate.getForEntity(get, Object.class);
//
//		return u;
//
//	}
//
//	private HttpHeaders getHeaders() {
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
//		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
//		return headers;
//	}
//
//}
