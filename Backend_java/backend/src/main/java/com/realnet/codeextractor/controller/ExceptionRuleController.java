package com.realnet.codeextractor.controller;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.realnet.codeextractor.entity.Rn_Bcf_Exception_Rules;
import com.realnet.codeextractor.service.ExceptionRuleService;
import com.realnet.codeextractor.service.Rn_Bcf_Exception_Rule_Library_Service;

@RequestMapping(value = "/codeextractor/exception")
@RestController
public class ExceptionRuleController {

	@Autowired
	private Rn_Bcf_Exception_Rule_Library_Service Service;

	@Autowired
	private ExceptionRuleService ruleService;

	// add data
	@PostMapping("/exception")
	public Rn_Bcf_Exception_Rules Savedata(@RequestBody Rn_Bcf_Exception_Rules data) throws JsonProcessingException {
		Rn_Bcf_Exception_Rules save = Service.save(data);

		return save;
	}

	// get all
	@GetMapping("/exception")
	public List<Rn_Bcf_Exception_Rules> getdetails() {
		List<Rn_Bcf_Exception_Rules> get = Service.getAll();
		return get;
	}

	// getby id
	@GetMapping("/exception/{id}")
	public Rn_Bcf_Exception_Rules getdetailsbyId(@PathVariable int id) {
		Rn_Bcf_Exception_Rules get = Service.getById(id);
		return get;
	}

	// update by id
	@PutMapping("/exception/{id}")
	public Rn_Bcf_Exception_Rules update(@RequestBody Rn_Bcf_Exception_Rules data, @PathVariable int id) {
		Rn_Bcf_Exception_Rules update = Service.updateById(id, data);
		return update;
	}

	// delete by id
	@DeleteMapping("/exception/{id}")
	public ResponseEntity<?> delete_by_id(@PathVariable Integer id) {
		Service.deleteById(id);
		return new ResponseEntity<>("deleted", HttpStatus.OK);

	}

//	get by type
	@GetMapping("/exception/{tech_stack}/{object_type}/{sub_object_type}")
	public List<Rn_Bcf_Exception_Rules> getbytype(@PathVariable String tech_stack, @PathVariable String object_type,
			@PathVariable String sub_object_type) {
		List<Rn_Bcf_Exception_Rules> get = Service.getByType(tech_stack, object_type, sub_object_type);
		return get;
	}

//	APPLY EXCEPTION RULE
	@GetMapping("/exceptionrule")
	public ResponseEntity<?> applyrule(@RequestParam String type, @RequestParam String path,
			@RequestParam String file_name, @RequestParam String startstring, @RequestParam String endstring,
			@RequestParam String replaceWith, @RequestParam String linestring, @RequestParam String keyword)
			throws IOException, ParseException {

		String replacerule = "";
		if (type.contains("replacebyfirstandlast")) {
			replacerule = ruleService.replacewithstartandend(path, file_name, startstring, endstring, replaceWith);

		}
		if (type.contains("replacement")) {
			replacerule = ruleService.replacesting(path, file_name, keyword, replaceWith);

		}
		if (type.contains("appending")) {
			replacerule = ruleService.appendline(linestring, replaceWith, path, file_name);
		}
		if (type.contains("linereplacement")) {
			replacerule = ruleService.linereplacement(path, file_name, keyword, replaceWith);
		}

		return new ResponseEntity<>(replacerule, HttpStatus.CREATED);
	}

}