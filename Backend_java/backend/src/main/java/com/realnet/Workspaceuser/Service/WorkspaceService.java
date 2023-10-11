package com.realnet.Workspaceuser.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.realnet.Workspaceuser.Entity.Sec_workspace;
import com.realnet.Workspaceuser.Repository.WorkspaceRepository;

@Service
public class WorkspaceService {
	
	@Autowired
	private WorkspaceRepository workspaceRepository;
	
	public Sec_workspace create(Sec_workspace rnrule) {
		return workspaceRepository.save(rnrule);
	}

	
	public List<Sec_workspace> getall() {
		return (List<Sec_workspace>) workspaceRepository.findAll();
	}

	
	public Sec_workspace getbyid(int id) {
		return workspaceRepository.findById(id);
	}


	public Sec_workspace updatebyid(Sec_workspace project, int id) {
		Sec_workspace rule = workspaceRepository.findById(id);

//				.orElseThrow(()-> ResourceNotFoundException("rueboard","id",id));
		rule.setAccountId(project.getAccountId());
//		rule.setDefault_team_id(project.getDefault_team_id());
		rule.setDescription(project.getDescription());
		rule.setIs_active(project.getIs_active());
		rule.setIs_default(project.getIs_default());
		rule.setName(project.getName());
		rule.setOwner_id(project.getOwner_id());
		
		return workspaceRepository.save(rule);
	}


	public void deletebyid(int id) {
		Sec_workspace save = workspaceRepository.findById(id);
		workspaceRepository.delete(save);
	}

}
