package com.realnet.SuReops.Controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.SuReops.Services.SureOpsService;
import com.realnet.entitybuilder.response.EntityResponse;
import com.realnet.fnd.entity.Rn_Project_Setup;
import com.realnet.fnd.repository.Rn_ProjectSetup_Repository;
import com.realnet.workflow.Entites.Workflow_Line;
import com.realnet.workflow.repository.W_repository;
import com.realnet.workflow.repository.Workflow_lineRepository;

@RestController
@RequestMapping(value = "/sureops", produces = MediaType.APPLICATION_JSON_VALUE)
public class SureopsController {

	@Autowired
	private Rn_ProjectSetup_Repository projectSetupRepository;

	@Autowired
	private Workflow_lineRepository workflow_lineRepository;

	@Autowired
	private W_repository tableRepository;

	@Autowired
	private SureOpsService sureservice;

	@Value("${projectPath}")
	private String projectPath;

//	Creating files like yaml, shell SCript ,docker File

	@GetMapping("/createfile/{id}/{profile_id}")
	public ResponseEntity<?> CreateFiles(@PathVariable Integer id,@PathVariable Long profile_id) throws UnsupportedEncodingException {

		// get workflow
		Rn_Project_Setup prj = projectSetupRepository.findById(id).orElseThrow(null);
		Workflow_Line workflowline = workflow_lineRepository.findById(prj.getWorkflow_id());

		String PRJ_NAME = prj.getProjectName();
		String portNumber = prj.getPortNumber();

//		Parsing Json data 

		List<String> keys = new ArrayList<>();

		JsonParser parser1 = new JsonParser();
		JsonElement element1 = parser1.parse(workflowline.getModel());
		JsonArray Array1 = element1.getAsJsonArray();

		int i = 1;
		for (JsonElement ar1 : Array1) {
			JsonObject obj1 = ar1.getAsJsonObject();
			System.out.println(obj1);
			Set<Map.Entry<String, JsonElement>> entries = obj1.entrySet();
			for (Map.Entry<String, JsonElement> entry : entries) {
				String key = entry.getKey();
				String string = key.toString();
				String value1 = entry.getValue().getAsString();
				keys.add(value1);

				if (value1.equals("Yamel File")) {
					System.out.println("yaml data");
					sureservice.yml(workflowline.getModel(), PRJ_NAME, portNumber,profile_id);
					i++;
				} else if (value1.equalsIgnoreCase("Shell Script")) {
					System.out.println("script data");
					sureservice.script(workflowline.getModel(), PRJ_NAME, portNumber,profile_id);
					i++;
				} else if (value1.equalsIgnoreCase("Docker File")) {
					System.out.println("docker data");
					sureservice.docker(workflowline.getModel(), PRJ_NAME, portNumber,profile_id);
					i++;
				}

			}
		}
		if (i == 1) {

			sureservice.Createonefile(prj, workflowline,profile_id);
		}

		return new ResponseEntity<>(new EntityResponse("file created"), HttpStatus.CREATED);

	}

}
