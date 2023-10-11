package com.realnet.Workspaceuser.Controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.realnet.Workspaceuser.Entity.Sec_workspace;
import com.realnet.Workspaceuser.Repository.WorkspaceRepository;
import com.realnet.Workspaceuser.Service.WorkspaceService;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;

@RequestMapping("/Workspace_workspace")
@RestController
public class WorkspaceController {

	@Autowired
	private AppUserServiceImpl userService;

	@Autowired
	private WorkspaceService workspaceService;
	@Autowired
	private WorkspaceRepository workspaceRepository;

//	create 
	@PostMapping("/workspace")
	public Sec_workspace create(@RequestBody Sec_workspace rnrule) {

		AppUser loggedInUser = userService.getLoggedInUser();
		rnrule.setOwner_id(loggedInUser.getUsername());

		long accountId = loggedInUser.getAccount().getAccount_id();
		Long l = accountId;
		if (l != null) {
			rnrule.setAccountId(accountId);

		}
		Sec_workspace rn = workspaceService.create(rnrule);
		return rn;
	}

//	get all

	@GetMapping("/workspace")
	public ResponseEntity<?> getall() {

		List<Sec_workspace> li = workspaceService.getall();

		return new ResponseEntity<>(li, HttpStatus.OK);
	}

//	get all BY ACCOUNT ID
	@GetMapping("/FindByaccount")
	public ResponseEntity<?> getallbyaccount() {
		AppUser loggedInUser = userService.getLoggedInUser();
		long accountId = loggedInUser.getAccount().getAccount_id();
		List<Sec_workspace> li = workspaceRepository.findByAccountId(accountId);
		return new ResponseEntity<>(li, HttpStatus.OK);
	}

//	get by id
	@GetMapping("/workspace/{id}")
	public ResponseEntity<?> getbyid(@PathVariable int id) {
		Sec_workspace rn = workspaceService.getbyid(id);
		return new ResponseEntity<>(rn, HttpStatus.OK);
	}

//	update by id
	@PutMapping("/workspace/{id}")
	public ResponseEntity<?> update(@RequestBody Sec_workspace project, @PathVariable int id) {
		Sec_workspace rule_t = workspaceService.updatebyid(project, id);
		return new ResponseEntity<>(rule_t, HttpStatus.OK);

	}

//	Delete by id
	@DeleteMapping("/workspace/{id}")
	public void deletebyid(@PathVariable int id) {
		workspaceService.deletebyid(id);
	}

}
