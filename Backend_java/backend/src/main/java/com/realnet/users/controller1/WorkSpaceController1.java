package com.realnet.users.controller1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.realnet.users.entity1.AppUser;
import com.realnet.users.repository1.AppUserRepository;
import com.realnet.users.response.MessageResponse;
import com.realnet.users.service1.AppUserServiceImpl;

@RestController
@RequestMapping("/User_workSpace")
public class WorkSpaceController1 {
	@Autowired
	private AppUserServiceImpl userService;
	
	@Autowired
	private AppUserRepository appUserRepository;
//	@Autowired
//	private Sec_teams_Repository sec_teams_Repository;
//	
//	@Autowired
//	private Sec_team_MemberRepository memberRepository;
//	
//	@Autowired
//	private SecWorkspaceUserRepo secWorkspaceUserRepo;

	//GET ALL USER attach from login id 
	@GetMapping("/GetAll")
	public ResponseEntity<?> getall(){
		AppUser loggedInUser = userService.getLoggedInUser();
		Long account_id = loggedInUser.getAccount().getAccount_id();
		
		List<AppUser> li = appUserRepository.getall(account_id);
		return new ResponseEntity<>(li,HttpStatus.OK);
	}
	
	
	
	//GET ALL USER ADD BY ADMIN
	@GetMapping("/GetAllUser")
	public ResponseEntity<?> GetUser(){
		AppUser loggedInUser = userService.getLoggedInUser();
		Long account_id = loggedInUser.getAccount().getAccount_id();
		
		List<AppUser> li = appUserRepository.getalluser(account_id);
		return new ResponseEntity<>(li,HttpStatus.OK);
	}

	//GET ALL GUEST ADD BY ADMIN
	@GetMapping("/GetAllGuest")
	public ResponseEntity<?> Getguest(){
		AppUser loggedInUser = userService.getLoggedInUser();
		Long account_id = loggedInUser.getAccount().getAccount_id();
		
		List<AppUser> li = appUserRepository.getallguest(account_id);
		return new ResponseEntity<>(li,HttpStatus.OK);
	}



}
