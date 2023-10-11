package com.realnet.flf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.realnet.codeextractor.entity.Build_controller;
import com.realnet.codeextractor.entity.Rn_Bcf_Extractor;
import com.realnet.codeextractor.repository.BuildControllerrepo;
import com.realnet.codeextractor.repository.Rn_Bcf_Extractor_Repository;
import com.realnet.flf.entity.Rn_Bcf_Technology_Stack;
import com.realnet.flf.entity.Technology_element;
import com.realnet.flf.repository.Rn_Bcf_TechnologyStack_Repository;
import com.realnet.flf.repository.Techstack_elementRepo;

@RestController
@RequestMapping("/token/flf/tech_stack")
public class Tech_stack_controller {

	@Autowired
	private Rn_Bcf_TechnologyStack_Repository technologyStack_Repository;

	@Autowired
	private Rn_Bcf_Extractor_Repository bcf_Extractor_Repository;

	@Autowired
	private Techstack_elementRepo elementRepo;

	@Autowired
	private BuildControllerrepo buildControllerrepo;

	@GetMapping("/get_techstack/{tech_stack}")
	public ResponseEntity<?> get_byname(@PathVariable String tech_stack) throws JsonProcessingException {

		Rn_Bcf_Technology_Stack stack = technologyStack_Repository.findByTech_stack(tech_stack);

		return new ResponseEntity<>(stack, HttpStatus.OK);
	}

	@GetMapping("/get_element/{technology_stack_id}")
	public ResponseEntity<?> get_element(@PathVariable Integer technology_stack_id) throws JsonProcessingException {

		List<Technology_element> stack = elementRepo.findtechid(technology_stack_id);

		return new ResponseEntity<>(stack, HttpStatus.OK);
	}

	@GetMapping("/get_rnbcf/{tech_stack}")
	public ResponseEntity<?> getbcf_extractor(@PathVariable String tech_stack) throws JsonProcessingException {

		List<Rn_Bcf_Extractor> stack = bcf_Extractor_Repository.findByTech_stack_name(tech_stack);

		return new ResponseEntity<>(stack, HttpStatus.OK);
	}

	@GetMapping("/get_apiendpoint/{tech_stack}")
	public ResponseEntity<?> getapiendpoint(@PathVariable String tech_stack) throws JsonProcessingException {

		List<Build_controller> stack = buildControllerrepo.findbytechsstack(tech_stack);

		return new ResponseEntity<>(stack, HttpStatus.OK);
	}

//	GET TECH STACK BY SERVICE TYPE
	@GetMapping("/get_byservicetype/{servicetype}")
	public ResponseEntity<?> getbyservice(@PathVariable String servicetype) throws JsonProcessingException {

		List<Rn_Bcf_Technology_Stack> stack = technologyStack_Repository.findByservicetype(servicetype);

		return new ResponseEntity<>(stack, HttpStatus.OK);
	}

}
