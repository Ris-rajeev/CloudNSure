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
import com.realnet.Workspaceuser.Service.SecTeam_MemberService;
import com.realnet.Workspaceuser.Entity.Sec_team_members;
@RequestMapping("/Workspace_team_member")
@RestController
public class Sec_team_members_Controller {
	
	@Autowired
 private SecTeam_MemberService secTeam_MemberService;
	
//	create 
	@PostMapping("/Teammember")
	public Sec_team_members create(@RequestBody Sec_team_members rnrule){
		Sec_team_members rn = secTeam_MemberService.create(rnrule);
		return rn;
	}
	
//	get all
	
	@GetMapping("/Teammember")
	public ResponseEntity<?> getall(){
		List<Sec_team_members> li = secTeam_MemberService.getall();
		return new ResponseEntity<>(li,HttpStatus.OK);
	}
	
//	get by id
	@GetMapping("/Teammember/{id}")
	public ResponseEntity<?> getbyid(@PathVariable int id){
		Sec_team_members rn= secTeam_MemberService.getbyid(id);
		return new ResponseEntity<>(rn,HttpStatus.OK);
	}
	
//	update by id
	@PutMapping("/Teammember/{id}")
	public ResponseEntity<?> update(@RequestBody Sec_team_members project, @PathVariable int id){
		Sec_team_members rule_t= secTeam_MemberService.updatebyid(project,id);
		return new ResponseEntity<>(rule_t,HttpStatus.OK);

	}
	
//	Delete by id
	@DeleteMapping("/Teammember/{id}")
	public void deletebyid(@PathVariable int id){
		secTeam_MemberService.deletebyid(id);		
	} 
}
