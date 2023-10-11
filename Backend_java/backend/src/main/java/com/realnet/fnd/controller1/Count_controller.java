package com.realnet.fnd.controller1;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.actionbuilder.repository.Rn_CFF_ActionBuilder_Header_Repository;
import com.realnet.fnd.entity.Rn_Module_Setup;
import com.realnet.fnd.repository.Rn_ModuleSetup_Repository;
import com.realnet.rb.repository.Rn_report_builder_repository;
import com.realnet.wfb.entity.Rn_Fb_Header;
import com.realnet.wfb.repository.Rn_Fb_Header_Repository;

@RestController
@RequestMapping("/fnd1/count")
public class Count_controller {

	@Autowired
	private Rn_Fb_Header_Repository header_Repository;

	@Autowired
	private Rn_report_builder_repository report_builder_repository;

	@Autowired
	private Rn_CFF_ActionBuilder_Header_Repository actionBuilder_Header_Repository;

	@Autowired
	private Rn_ModuleSetup_Repository moduleSetup_Repository;

//COUNT OF WIREFRAME
	@GetMapping("/get_wireframe/{module_id}")
	public ResponseEntity<?> getdetails(@PathVariable Integer module_id) {
		Object count_wireframe = header_Repository.count_wireframe(module_id);
		return new ResponseEntity<>(count_wireframe, HttpStatus.OK);

	}

	// COUNT OF REPORT BUILDER
	@GetMapping("/get_REPORT/{module_id}")
	public ResponseEntity<?> getREPORT(@PathVariable Integer module_id) {
		String count_wireframe = report_builder_repository.count_report(module_id);

		if (count_wireframe.isEmpty()) {
			return new ResponseEntity<>(0, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(count_wireframe, HttpStatus.OK);

		}
	}

	// COUNT OF ACTION BUILDER
	@GetMapping("/get_action/{module_id}")
	public ResponseEntity<?> getaction(@PathVariable Integer module_id) {

		String count_wireframe = "0";

		Rn_Module_Setup module_Setup = moduleSetup_Repository.findById(module_id).orElseThrow(null);
		List<Rn_Fb_Header> rn_fb_headers = module_Setup.getRn_fb_headers();
		for (Rn_Fb_Header rn_Fb_Header : rn_fb_headers) {

			count_wireframe = actionBuilder_Header_Repository.count_action(rn_Fb_Header.getId());

		}
		return new ResponseEntity<>(count_wireframe, HttpStatus.OK);

	}
}
