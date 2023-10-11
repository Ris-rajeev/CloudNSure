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

import com.realnet.codeextractor.entity.Rule_library_keyword;
import com.realnet.codeextractor.repository.Rule_library_keywordRepository;
import com.realnet.codeextractor.service.Rule_library_keywordService;
import com.realnet.entitybuilder.response.EntityResponse;

@RequestMapping(value = "/code_extractor/rule_keyword")
@RestController
public class Rule_library_keywordController {

	@Autowired
	private Rule_library_keywordService Service;

	@Autowired
	private Rule_library_keywordRepository keywordRepository;

	@PostMapping("/Rule_library_keyword")
	public Rule_library_keyword Savekeyword(@RequestBody Rule_library_keyword keyword) {
		Rule_library_keyword save = Service.Savedata(keyword);
		return save;
	}

	@GetMapping("/Rule_library_keyword")
	public List<Rule_library_keyword> getdetails() {
		List<Rule_library_keyword> get = Service.getdetails();
		return get;
	}

	@GetMapping("/Rule_library_keyword/{id}")
	public Rule_library_keyword getdetailsbyId(@PathVariable Integer id) {
		Rule_library_keyword get = Service.getdetailsbyId(id);
		return get;
	}

	@DeleteMapping("/Rule_library_keyword/{id}")
	public void delete_by_id(@PathVariable Integer id) {
		Service.delete_by_id(id);

	}

	@PutMapping("/Rule_library_keyword/{id}")
	public Rule_library_keyword update(@RequestBody Rule_library_keyword keyword, @PathVariable Integer id) {
		Rule_library_keyword update = Service.update(keyword, id);
		return update;
	}

//	copy rule 
	@PostMapping("/copyrule")
	public ResponseEntity<?> copyrule(@RequestParam String technology_stack, @RequestParam String object_type,
			@RequestParam String sub_object_type, @RequestParam String service,
			@RequestParam String new_tech_stack_name, @RequestBody Rule_library_keyword rule)

			throws IOException, ParseException {

		List<Rule_library_keyword> rules = keywordRepository.findByTech_stack(technology_stack, service, object_type,
				sub_object_type);

		for (Rule_library_keyword keyword : rules) {

			Rule_library_keyword rule1 = new Rule_library_keyword();
			rule1.setTech_stack(new_tech_stack_name);
			rule1.setObject_type(keyword.getObject_type());
			rule1.setSub_object_type(keyword.getSub_object_type());
			rule1.setVersion(keyword.getVersion());
			rule1.setReplacement_string(keyword.getReplacement_string());
			rule1.setKeyword(keyword.getKeyword());
			rule1.setPriority(keyword.getPriority());
			rule1.setService(keyword.getService());
			rule1.setIsactive(keyword.isIsactive());
			keywordRepository.save(rule1);

		}
		if (rules.isEmpty()) {
			return new ResponseEntity<>(new EntityResponse("no rule found"), HttpStatus.NOT_FOUND);

		} else {
			return new ResponseEntity<>(new EntityResponse("rule created"), HttpStatus.CREATED);

		}
	}

}