package com.realnet.listbuilder.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonParser;
import com.realnet.formdrag.repository.Rn_wf_lines_3Repository;
import com.realnet.listbuilder.Entity.Lb_Header;
import com.realnet.listbuilder.Service.Lb_HeaderService;
import com.realnet.wfb.repository.Rn_Fb_Header_Repository;

@RestController
@RequestMapping("/listbuilder/lb_line")
public class Lb_LineController {

	@Autowired
	private Lb_HeaderService headerService;

	@Autowired
	private Rn_Fb_Header_Repository fb_header_Repository;

	@Autowired
	private Rn_wf_lines_3Repository wfline_repo;

	// GET ALL TABLE NAME IN WIREFRAME VIA LB HEADER ID
	@GetMapping(value = "/getallwireframe_table/{lbHeaderId}")
	public ResponseEntity<?> BuildByProject(@PathVariable Integer lbHeaderId) throws IOException {
		Lb_Header data = headerService.getdetailsbyId(lbHeaderId);

		System.out.println(" header data get....");

		Long backend_id = data.getBackend_id();

		List<String> tablename = new ArrayList<>();
		JsonParser parser = new JsonParser();

		System.out.println("now all table data getting.... ");
		fb_header_Repository.findbybackendid(backend_id)
				.forEach(header -> wfline_repo.findheader(header.getId())
						.ifPresent(wf -> tablename.add(parser.parse(wf.getModel()).getAsJsonObject().get("name")
								.toString().replaceAll("\"", "").replaceAll(" ", "_"))));

		return new ResponseEntity<>(tablename, HttpStatus.OK);

	}

}
