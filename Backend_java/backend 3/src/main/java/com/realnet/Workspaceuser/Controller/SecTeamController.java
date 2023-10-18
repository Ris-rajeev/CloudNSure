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

import com.realnet.Workspaceuser.Entity.Sec_teams;
import com.realnet.Workspaceuser.Service.Sec_teamService;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;

@RequestMapping("/Workspace_team")
@RestController
public class SecTeamController {

	@Autowired
	private Sec_teamService sec_teamService;

	@Autowired
	private AppUserServiceImpl userService;

//	create 
	@PostMapping("/SecTeam")
	public Sec_teams create(@RequestBody Sec_teams rnrule) {
		AppUser loggedInUser = userService.getLoggedInUser();

		long accountId = loggedInUser.getAccount().getAccount_id();
		Long l = accountId;
		if (l != null) {
			rnrule.setAccountId(accountId);

		}

		Sec_teams rn = sec_teamService.create(rnrule);
		return rn;
	}

//	get all

	@GetMapping("/SecTeam")
	public ResponseEntity<?> getall() {
		List<Sec_teams> li = sec_teamService.getall();
		return new ResponseEntity<>(li, HttpStatus.OK);
	}

//	get by id
	@GetMapping("/SecTeam/{id}")
	public ResponseEntity<?> getbyid(@PathVariable int id) {
		Sec_teams rn = sec_teamService.getbyid(id);
		return new ResponseEntity<>(rn, HttpStatus.OK);
	}

//	update by id
	@PutMapping("/SecTeam/{id}")
	public ResponseEntity<?> update(@RequestBody Sec_teams project, @PathVariable int id) {
		Sec_teams rule_t = sec_teamService.updatebyid(project, id);
		return new ResponseEntity<>(rule_t, HttpStatus.OK);

	}

//	Delete by id
	@DeleteMapping("/SecTeam/{id}")
	public void deletebyid(@PathVariable int id) {
		sec_teamService.deletebyid(id);
	}

}
