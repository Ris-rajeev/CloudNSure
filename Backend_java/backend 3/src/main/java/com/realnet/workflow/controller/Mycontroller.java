package com.realnet.workflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.Dashboard1.Entity.Dashbord1_Line;
import com.realnet.Dashboard1.Entity.Dashbord_Header;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;
import com.realnet.workflow.Entites.Workflow_Line;
import com.realnet.workflow.Entites.Workflow_table;
import com.realnet.workflow.repository.W_repository;
import com.realnet.workflow.repository.Workflow_lineRepository;

@RestController
public class Mycontroller {

	@Autowired
	private W_repository w_repository;

	@Autowired
	private Workflow_lineRepository workflow_lineRepository;

	@Autowired
	private AppUserServiceImpl userService;

//	 save data
	@PostMapping("/work")
	public Workflow_table Savedata(@RequestBody Workflow_table workflow) {
		AppUser logged = userService.getLoggedInUser();
		workflow.setAccountId(logged.getAccount().getAccount_id());
		Workflow_Line wf = new Workflow_Line();
		wf.setAccountId(logged.getAccount().getAccount_id());

		Workflow_table dash = w_repository.save(workflow);
		return dash;
	}

// get all	
	@GetMapping("/work")
	public List<Workflow_table> getdetails() {
		List<Workflow_table> dash = w_repository.findAll();
		return dash;
	}

//	get by id
	@GetMapping("/work/{id}")
	public Workflow_table getdetailsbyId(@PathVariable int id) {
		Workflow_table dash = w_repository.findById(id);
		return dash;
	}

//	update  by id

	@PutMapping("/work/{id}")
	public Workflow_table update_Dashbord1_Lineby_id(@PathVariable int id, @RequestBody Workflow_table workflow) {
		Workflow_table work = w_repository.findById(id);
		work.setApp_category(workflow.getApp_category());
		work.setDescription(workflow.getDescription());
		work.setSelect_technology(workflow.getSelect_technology());
		work.setTags(workflow.getTags());
		work.setWorkflow_name(workflow.getWorkflow_name());
		work.setCallable(workflow.getCallable());

		return this.w_repository.save(work);
	}

//	delete by id
	@DeleteMapping("/work/{id}")
	public ResponseEntity<?> delete_by_id(@PathVariable int id) {
		w_repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);

	}

//	update workflowLine by id;

	@PutMapping("/updateByid/{id}")
	public ResponseEntity<?> updatebyheaderid(@RequestBody Workflow_Line workflow_Line, @PathVariable int id) {
		Workflow_Line work = workflow_lineRepository.findById(id);
		AppUser logged = userService.getLoggedInUser();
		workflow_Line.setAccountId(logged.getAccount().getAccount_id());

		work.setAccountId(workflow_Line.getAccountId());
		work.setHeader_id(workflow_Line.getHeader_id());
		work.setModel(workflow_Line.getModel());

		Workflow_Line line = workflow_lineRepository.save(work);
		return new ResponseEntity<>(line, HttpStatus.OK);
	}

}
