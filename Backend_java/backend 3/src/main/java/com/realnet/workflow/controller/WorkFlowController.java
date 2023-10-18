package com.realnet.workflow.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.fnd.entity.Rn_Project_Setup;
import com.realnet.fnd.repository.Rn_ProjectSetup_Repository;
import com.realnet.workflow.Entites.ApiConsumesClass;
import com.realnet.workflow.Entites.Connection;
import com.realnet.workflow.Entites.ProjectDao;
import com.realnet.workflow.Entites.Workflow_table;
import com.realnet.workflow.repository.W_repository;

@RestController
@RequestMapping("/workflow/workflow")
public class WorkFlowController {

	@Autowired
	private Rn_ProjectSetup_Repository projectSetupRepository;

	@Autowired
	private W_repository w_repository;

//	@Autowired
//	RestTemplate restTemplate;

	@GetMapping("/getproject")
	public ResponseEntity<?> getproject() {

		ProjectDao dao = new ProjectDao();
		int id = 103;
		Rn_Project_Setup project = projectSetupRepository.findById(id).orElseThrow(null);
		dao.setCopyTo(project.getCopyTo());
		dao.setProjectName(project.getProjectName());
		dao.setTechnologyStack(project.getTechnologyStack());
		return new ResponseEntity<>(dao, HttpStatus.OK);

	}

	@GetMapping("/connector")
	public Object connector(@RequestBody Connection con)
			throws JsonGenerationException, JsonMappingException, IOException {

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Object> entity = restTemplate.getForEntity(con.getConnector_json(), Object.class);

		Object object = entity.getBody();
		Connection conn = new Connection();

		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(object);
//		System.out.println(
//				mapper.writerWithDefaultPrettyPrinter(
//						).writeValueAsString(object));//print

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);
		JsonObject obj = element.getAsJsonObject();

		// get kry from get api
		Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
		for (Map.Entry<String, JsonElement> entry : entries) {

			String key = entry.getKey();
			String string = key.toString();
			String value = entry.getValue().toString();

			// get key from connection table
//			String ConStr = mapper.writeValueAsString(conn);
//			JsonElement element1 = parser.parse(ConStr);
//			JsonObject obj1 = element1.getAsJsonObject();
//			Set<Map.Entry<String, JsonElement>> ents = obj1.entrySet();
//			for (Map.Entry<String, JsonElement> ent : ents) {
//				
//	         String string2 = ent.getKey().toString();

			if (string.equals("id")) {

				conn.setId(value);
			} else if (string.equals("project_name")) {
				conn.setProject_name(value);

			}
		}
//		}

		return conn;
	}

	// get all workflow table that we call
	@GetMapping("/callworkflow")
	public ResponseEntity<?> callworkflow() {

		List<Workflow_table> table = w_repository.callTable();

		return new ResponseEntity<>(table, HttpStatus.OK);

	}

}
