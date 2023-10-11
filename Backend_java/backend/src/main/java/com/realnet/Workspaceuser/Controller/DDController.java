package com.realnet.Workspaceuser.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.Workspaceuser.Entity.DDTable;
import com.realnet.Workspaceuser.Entity.SecUsedDd;
import com.realnet.Workspaceuser.Entity.Sec_teams;
import com.realnet.Workspaceuser.Repository.Sec_teams_Repository;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.repository1.AppUserRepository;
//@RequestMapping("/Workspace_Dd")
@RestController
public class DDController {
	
	@Autowired
	private Sec_teams_Repository sec_teams_Repository;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
//	SEC TEAM DD
	
	@GetMapping("/Sec_team")
	public ResponseEntity<?> getteam(){
		List<Sec_teams> sec = (List<Sec_teams>) sec_teams_Repository.findAll();
		ArrayList<DDTable> dd = new ArrayList<DDTable>();
		for(Sec_teams s:sec) {
			DDTable d = new DDTable();
			d.setId(s.getId());
			d.setName(s.getName());
			dd.add(d);
		}
		return new ResponseEntity<>(dd, HttpStatus.OK);
			
	}
	
//	Report To
	@GetMapping("/Report_to")
	public ResponseEntity<?> reportto(){
		List<AppUser> sec = (List<AppUser>) appUserRepository.findAll();
		ArrayList<SecUsedDd> dd = new ArrayList<SecUsedDd>();
		for(AppUser s:sec) {
			SecUsedDd d = new SecUsedDd();
			d.setUserId(s.getUserId());
			d.setFullName(s.getFullName());
			
			dd.add(d);
		}
		return new ResponseEntity<>(dd, HttpStatus.OK);
			
	}
//	assign To
	@GetMapping("/Assign")
	public ResponseEntity<?> Assign(){
		List<AppUser> sec = (List<AppUser>) appUserRepository.findAll();
		ArrayList<SecUsedDd> dd = new ArrayList<SecUsedDd>();
		for(AppUser s:sec) {
			SecUsedDd d = new SecUsedDd();
			d.setUserId(s.getUserId());
			d.setFullName(s.getFullName());
			
			dd.add(d);
		}
		return new ResponseEntity<>(dd, HttpStatus.OK);
			
	}
//	Requestor To
	@GetMapping("/Requestor")
	public ResponseEntity<?> Requestor(){
		List<AppUser> sec = (List<AppUser>) appUserRepository.findAll();
		ArrayList<SecUsedDd> dd = new ArrayList<SecUsedDd>();
		for(AppUser s:sec) {
			SecUsedDd d = new SecUsedDd();
			d.setUserId(s.getUserId());
			d.setFullName(s.getFullName());
			
			dd.add(d);
		}
		return new ResponseEntity<>(dd, HttpStatus.OK);
			
	}
	
//	owner To
	@GetMapping("/Owner")
	public ResponseEntity<?> Owner(){
		List<AppUser> sec = (List<AppUser>) appUserRepository.findAll();
		ArrayList<SecUsedDd> dd = new ArrayList<SecUsedDd>();
		for(AppUser s:sec) {
			SecUsedDd d = new SecUsedDd();
			d.setUserId(s.getUserId());
			d.setFullName(s.getFullName());
			
			dd.add(d);
		}
		return new ResponseEntity<>(dd, HttpStatus.OK);
			
	}

}
