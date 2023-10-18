package com.realnet.webhook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.webhook.entity.Outgoing_lines;
import com.realnet.webhook.repository.OutgoingLineRepository;

@RestController
@RequestMapping("/api/outgoing_lines")
public class OutgoingLineController {

	@Autowired
	private OutgoingLineRepository line_repo;

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody Outgoing_lines outgoing_data) {

		Outgoing_lines outgoing = line_repo.findById(id).get();
		outgoing.setHeader_id(outgoing_data.getHeader_id());
		outgoing.setModel(outgoing_data.getModel());
		Outgoing_lines save = line_repo.save(outgoing);

		return new ResponseEntity<>(save, HttpStatus.ACCEPTED);

	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {

		return new ResponseEntity<>(line_repo.findById(id).get(), HttpStatus.ACCEPTED);

	}
	@GetMapping("/getall")
	public ResponseEntity<?> getall() {

		return new ResponseEntity<>(line_repo.findAll(), HttpStatus.ACCEPTED);

	}
	

}
