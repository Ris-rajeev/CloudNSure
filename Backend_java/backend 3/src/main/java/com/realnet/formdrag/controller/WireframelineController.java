package com.realnet.formdrag.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.fnd.entity.Rn_Module_Setup;
import com.realnet.fnd.entity.Rn_Project_Setup;
import com.realnet.fnd.repository.Rn_ProjectSetup_Repository;
import com.realnet.fnd.service.Rn_ProjectSetup_Service;
import com.realnet.formdrag.entity.Rn_wf_lines_3;
import com.realnet.formdrag.repository.Rn_wf_lines_3Repository;
import com.realnet.wfb.entity.Rn_Fb_Header;
import com.realnet.wfb.repository.Rn_Fb_Header_Repository;

@RestController
@RequestMapping("/formdrag/wireframe")
public class WireframelineController {

	@Autowired
	private Rn_ProjectSetup_Repository projectSetup_Repository;

	@Autowired
	private Rn_ProjectSetup_Service projectSetupService;

	@Autowired
	private Rn_wf_lines_3Repository wfline_repo;

	@Autowired
	private Rn_Fb_Header_Repository header_Repository;

	// GET ALL TABLE NAME IN WIREFRAME VIA PROJ ID

	@GetMapping(value = "/getallwireframe_table/{proj_id}")
	public ResponseEntity<?> BuildByProject(@PathVariable Integer proj_id) throws IOException {

		List<String> tablename = new ArrayList<>();
		JsonParser parser = new JsonParser();

		Optional<Rn_Project_Setup> project = projectSetup_Repository.findById(proj_id);
		List<Rn_Module_Setup> modules = project.get().getModules();

		modules.forEach(mod -> mod.getRn_fb_headers().forEach(header -> wfline_repo.findheader(header.getId())
				.ifPresent(wf -> tablename.add(parser.parse(wf.getModel()).getAsJsonObject().get("name").toString()))));

		return new ResponseEntity<>(tablename, HttpStatus.OK);

	}

	// GET ALL ONLY CHILD TABLE NAME IN WIREFRAME VIA PROJ ID AND WF ID
	@GetMapping(value = "/getall_table/{proj_id}/{wfid}")
	public ResponseEntity<?> getalltable(@PathVariable Integer proj_id, @PathVariable Integer wfid) throws IOException {

		List<String> tablename = new ArrayList<>();
		JsonParser parser = new JsonParser();

		Optional<Rn_Project_Setup> project = projectSetup_Repository.findById(proj_id);
		List<Rn_Module_Setup> modules = project.get().getModules();

		modules.forEach(
				mod -> header_Repository.getallwfforchild(mod.getId(), header_Repository.findById(wfid).get().getBackend_id())
						.forEach(header -> wfline_repo.findheader(header.getId())
								.ifPresent(wf -> tablename.add(parser.parse(wf.getModel()).getAsJsonObject().get("name")
										.toString().replaceAll("\"", "").replaceAll(" ", "_")))));

		return new ResponseEntity<>(tablename, HttpStatus.OK);

	}

//	GET MODEL OF WIREFRAME 
	@GetMapping(value = "/getmodel/{proj_id}/{wfid}/{wfname}")
	public ResponseEntity<?> getmodelviatablename(@PathVariable Integer proj_id, @PathVariable Integer wfid,
			@PathVariable String wfname) throws IOException {

		HashMap<String, String> entityname = new HashMap<>();
		JsonParser parser = new JsonParser();

		Optional<Rn_Project_Setup> project = projectSetup_Repository.findById(proj_id);
		List<Rn_Module_Setup> modules = project.get().getModules();

		for (Rn_Module_Setup mod : modules) {

			List<Rn_Fb_Header> allwf = header_Repository.getallwf(mod.getId(),
					header_Repository.findById(wfid).get().getBackend_id());

			for (Rn_Fb_Header wf : allwf) {
				JsonObject obj = parser.parse(wfline_repo.findheader(wf.getId()).get().getModel()).getAsJsonObject();

				String name = obj.get("name").toString().replaceAll("\"", "").replaceAll(" ", "_");

				if (wfname.equalsIgnoreCase(name)) {
					JsonElement element2 = obj.get("attributes");
					System.out.println(element2);

					JsonArray jsonArray = element2.getAsJsonArray();
					System.out.println(jsonArray);

					for (JsonElement ar : jsonArray) {

						JsonObject obj1 = ar.getAsJsonObject();

						String field_value1 = obj1.get("label").getAsString();
						String field2 = field_value1.replaceAll(" ", "_");
						System.out.println(field_value1);

						String type = obj1.get("type").getAsString();
						String data_type1 = type.replaceAll(" ", "_");

//						For one to many code

						if (data_type1.contains("button")) {

						} else if (field_value1.equals("OnetoOne") || field_value1.equals("OnetoMany")
								|| field_value1.equals("ManytoMany")) {

						}

						else {
							entityname.put(field2, data_type1);

						}

					}
				}
			}
		}

		return new ResponseEntity<>(entityname, HttpStatus.OK);

	}

	// COLUMN LIST OF WIREFRAME
	@GetMapping(value = "/columnlistofwireframe/{proj_id}/{tablename}")
	public ResponseEntity<?> buildfile_byTechstack(@PathVariable Integer proj_id, @PathVariable String tablename)
			throws IOException {

		List<String> columnlist = new ArrayList<>();

		Rn_Project_Setup prj = projectSetupService.getById(proj_id);

		List<Rn_Module_Setup> modules = prj.getModules();

		if (!modules.isEmpty()) {

			for (Rn_Module_Setup module : modules) {

				List<Rn_Fb_Header> rn_fb_headers = module.getRn_fb_headers();

				if (!rn_fb_headers.isEmpty()) {

					for (Rn_Fb_Header header : rn_fb_headers) {

						Integer header_id = header.getId();

						Optional<Rn_wf_lines_3> wf_get = wfline_repo.findheader(header_id);

						if (wf_get.isPresent()) {

							String model = wf_get.get().getModel();

//							Optional<Rn_wf_lines_3> wireframe = repo.findheader(header.getId());
//										i++;
							JsonParser parser = new JsonParser();

							JsonElement model_element = parser.parse(model);
							JsonObject jsonObject = model_element.getAsJsonObject();

							String tab_name = jsonObject.get("name").toString().replaceAll("\"", "").toLowerCase();

							if (tab_name.contains(tablename.toLowerCase())) {
								JsonElement element2 = jsonObject.get("dashboard");
								System.out.println(element2);

								JsonArray jsonArray = element2.getAsJsonArray();
								System.out.println(jsonArray);

								for (JsonElement ar : jsonArray) {

									JsonObject obj1 = ar.getAsJsonObject();
//											
									String field_value1 = obj1.get("charttitle").getAsString();
									String field2 = field_value1.replaceAll(" ", "_");
									columnlist.add(field2);
									System.out.println(field_value1);

								}
								break;
							}
						}
					}

				}
			}
		}
		return new ResponseEntity<>(columnlist, HttpStatus.OK);
	}
}