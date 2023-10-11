package com.realnet.Globalboard.Controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.realnet.Globalboard.Entity.Rn_board_rule_t;
import com.realnet.Globalboard.Service.Globalservice;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;


@RestController
public class GlobalController {
	
	@Autowired
	private Globalservice globalservice;
	@Autowired
	private AppUserServiceImpl userService;
	
//	create ruleboard
	@PostMapping("/ruleboard")
	
	public Rn_board_rule_t create(@RequestBody Rn_board_rule_t rnrule){
		AppUser loggedInUser = userService.getLoggedInUser();
		
		long accountId = loggedInUser.getAccount().getAccount_id();
		Long l = accountId;
		if(l!=null) {
			rnrule.setAccountId(accountId);
			
		}
		Rn_board_rule_t rn = globalservice.create(rnrule);
		return rn;
	}
	
//	getallglobal
	@GetMapping("/ruleboard")
	public ResponseEntity<?> getall(){
		List<Rn_board_rule_t> li = globalservice.getall();
		return new ResponseEntity<>(li,HttpStatus.OK);
	}
	
//	getbyid
	@GetMapping("/ruleboard/{id}")
	public ResponseEntity<?> getbyid(@PathVariable int id){
		Rn_board_rule_t rn= globalservice.getbyid(id);
		return new ResponseEntity<>(rn,HttpStatus.OK);
	}
	
//	update
	@PutMapping("/ruleboard")
	public ResponseEntity<?> update(@RequestBody Rn_board_rule_t project){
		Rn_board_rule_t rule_t= globalservice.update(project);
		return new ResponseEntity<>(rule_t,HttpStatus.OK);

	}
	//	update by id
	@PutMapping("/ruleboard/{id}")
	public ResponseEntity<?> update(@RequestBody Rn_board_rule_t project, @PathVariable int id){
		Rn_board_rule_t rule_t= globalservice.updatebyid(project,id);
		return new ResponseEntity<>(rule_t,HttpStatus.OK);
	}
//	Deletebyid
	@DeleteMapping("/ruleboard/{id}")
	public void deletebyid(@PathVariable int id){
		globalservice.deletebyid(id);
		
		
	}

}
