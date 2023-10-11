package com.realnet.codeextractor.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.realnet.fnd.service.Rn_ProjectSetup_Service;
import com.realnet.formdrag.entity.Rn_wf_lines_3;
import com.realnet.formdrag.repository.Rn_wf_lines_3Repository;
import com.realnet.wfb.entity.Rn_Fb_Header;

@RestController
@RequestMapping("/codeextractor/listbuilder")
public class ListBuildercontroller {

	@Autowired
	private Rn_ProjectSetup_Service projectSetupService;

	@Autowired
	private Rn_wf_lines_3Repository wfline_repo;

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

//						Optional<Rn_wf_lines_3> wireframe = repo.findheader(header.getId());
//									i++;
							JsonParser parser = new JsonParser();

							JsonElement model_element = parser.parse(model);
							JsonObject jsonObject = model_element.getAsJsonObject();

							String tab_name = jsonObject.get("name").toString().replaceAll("\"", "").toLowerCase();

							if (tab_name.contains(tablename)) {
								JsonElement element2 = jsonObject.get("attributes");
								System.out.println(element2);

								JsonArray jsonArray = element2.getAsJsonArray();
								System.out.println(jsonArray);

								for (JsonElement ar : jsonArray) {

									JsonObject obj1 = ar.getAsJsonObject();
//										
									String field_value1 = obj1.get("label").getAsString();
									String field2 = field_value1.replaceAll(" ", "_");
									columnlist.add(field2);
									System.out.println(field_value1);

								}
							}
						}
					}

				}
			}
		}
		return new ResponseEntity<>(columnlist, HttpStatus.OK);
	}
}
