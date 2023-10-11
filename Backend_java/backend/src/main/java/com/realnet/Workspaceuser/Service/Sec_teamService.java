package com.realnet.Workspaceuser.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.Workspaceuser.Entity.Sec_teams;
import com.realnet.Workspaceuser.Repository.Sec_teams_Repository;

@Service
public class Sec_teamService {
	
	@Autowired
	private Sec_teams_Repository sec_teams_Repository;

	
	
	
	public Sec_teams create(Sec_teams rnrule) {
		return sec_teams_Repository.save(rnrule);
	}

	
	public List<Sec_teams> getall() {
		return (List<Sec_teams>) sec_teams_Repository.findAll();
	}

	
	public Sec_teams getbyid(int id) {
		return sec_teams_Repository.findById(id);
	}


	public Sec_teams updatebyid(Sec_teams project, int id) {
		Sec_teams rule = sec_teams_Repository.findById(id);

//				.orElseThrow(()-> ResourceNotFoundException("rueboard","id",id));
		rule.setAccountId(project.getAccountId());
		rule.setDescription(project.getDescription());
		rule.setIs_active(project.getIs_active());
		rule.setName(project.getName());
		
		return sec_teams_Repository.save(rule);
	}


	public void deletebyid(int id) {
		sec_teams_Repository.deleteById(id);
	}

}
