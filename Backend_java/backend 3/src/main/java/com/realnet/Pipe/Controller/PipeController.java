package com.realnet.Pipe.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.realnet.Pipe.Entity.Rn_surepipe_play;
import com.realnet.Pipe.Entity.rn_surepipe_t;
import com.realnet.Pipe.Service.PipeService;
import com.realnet.Pipe.Service.Rn_surepipe_PlayService;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;


@RestController
public class PipeController {

	@Autowired
	private PipeService pipeService;
	
	@Autowired
	private Rn_surepipe_PlayService rn_surepipe_PlayService;
	
	@Autowired
	private AppUserServiceImpl userService;

//	create
	@PreAuthorize("hasAnyRole('SYSADMIN','ProjectManager','DEVOPS')")
	@PostMapping("/pipe")
	public rn_surepipe_t create(@RequestBody rn_surepipe_t rnrule){
		rn_surepipe_t rn = pipeService.create(rnrule);
		return rn;
	}
	
//	getall 
	@PreAuthorize("hasAnyRole('SYSADMIN','ProjectManager','DEVOPS')")
	@GetMapping("/pipe")
	public ResponseEntity<?> getall(){
		List<rn_surepipe_t> li = pipeService.getall();
		return new ResponseEntity<>(li,HttpStatus.OK);
	}
	
//	getbyid
	@GetMapping("/pipe/{id}")
	public ResponseEntity<?> getbyid(@PathVariable int id){
		rn_surepipe_t rn= pipeService.getbyid(id);
		return new ResponseEntity<>(rn,HttpStatus.OK);
	}
	
//	update
	@PutMapping("/pipe")
	public ResponseEntity<?> update(@RequestBody rn_surepipe_t project){
		rn_surepipe_t rule_t= pipeService.update(project);
		return new ResponseEntity<>(rule_t,HttpStatus.OK);

	}
	
//	Deletebyid
	@DeleteMapping("/pipe/{id}")
	public void deletebyid(@PathVariable int id){
		pipeService.deletebyid(id);
		
		
	}
	
	
	// add data
		@PostMapping(path = "/surepipe")
		public ResponseEntity<?> savedata(
				@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken, @Valid
				@RequestBody Rn_surepipe_play sure) {
			
			AppUser loggedInUser = userService.getLoggedInUser();
		
			long accountId = loggedInUser.getAccount().getAccount_id();
			Long l = accountId;
			if(l!=null) {
				sure.setAccountId(accountId);
				
			}	
		
//			Rn_surepipe_Workflow wf= new Rn_surepipe_Workflow();
//		
//			
//			wf.setCurrent_json("{\"step0\":{\"state\":\"current\",\"open\":true},\"step1\":{\"state\":\"not-started\",\"open\":false},\"step2\":{\"state\":\"not-started\",\"open\":false,\"failed\":false},\"step3\":{\"state\":\"not-started\",\"open\":false,\"failed\":false},\"step4\":{\"state\":\"not-started\",\"open\":false,\"failed\":false}}");
//			wf.setStatus("not_started");
//			wf.setWf_id(1);
//			
//			Rn_surepipe_Workflow save = rn_surepipe_PlayService.create(wf);
//			
//			System.out.println("workflow data  "+save);
//			
//			Rn_surepipe_play data=null;
//			if(wf.getId()!=0)
//			{
//				sure.setWorkflow_instanceid(wf.getId());
//				data = rn_surepipe_PlayService.create1(sure);
//			}		
//			System.out.println("student data "+data);	
//		
//						if (data == null) {
//				throw new ResourceNotFoundException("data not saved");
//			}
			Rn_surepipe_play data = rn_surepipe_PlayService.create1(sure);

			return ResponseEntity.status(HttpStatus.CREATED).body(data);
		}
//	get all surepipe play
	@GetMapping("/surepipe")
	public ResponseEntity<?> getallsure(){
		
		List<Rn_surepipe_play> li = rn_surepipe_PlayService.getallsure();
		return new ResponseEntity<>(li,HttpStatus.OK);
	}
	
//	get by wf_instance id
	
	@GetMapping("/get_by_wfinstanceid/{id}")
	public ResponseEntity<?> get_by_wfinstanceid(@PathVariable int id){
		
		Rn_surepipe_play li = rn_surepipe_PlayService.get_by_wfinstanceid(id);
		return new ResponseEntity<>(li,HttpStatus.OK);
	}
	
//	update by id
	@PutMapping("/updateby_id/{id}")
	public ResponseEntity<?> updateby_id(@PathVariable int id, 
			                               @RequestBody Rn_surepipe_play rn_surepipe_play ){
		Rn_surepipe_play rn = rn_surepipe_PlayService.updateby_id(id, rn_surepipe_play);
		return new ResponseEntity<>(rn, HttpStatus.ACCEPTED);
	}
	
	
}





