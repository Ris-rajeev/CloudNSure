package com.realnet.Workspaceuser.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.Workspaceuser.Entity.Sec_team_members;
import com.realnet.Workspaceuser.Entity.Sec_teams;
import com.realnet.Workspaceuser.Repository.Sec_team_MemberRepository;

@Service
public class SecTeam_MemberService {
	
	@Autowired
	private Sec_team_MemberRepository sec_team_MemberRepository;

	public Sec_team_members create(Sec_team_members rnrule) {
		return sec_team_MemberRepository.save(rnrule);
	}

	
	public List<Sec_team_members> getall() {
		return (List<Sec_team_members>) sec_team_MemberRepository.findAll();
	}

	
	public Sec_team_members getbyid(int id) {
		return sec_team_MemberRepository.findById(id);
	}


	public Sec_team_members updatebyid(Sec_team_members project, int id) {
		Sec_team_members rule = sec_team_MemberRepository.findById(id);

//				.orElseThrow(()-> ResourceNotFoundException("rueboard","id",id));
		rule.setAccess_days(project.getAccess_days());
		rule.setAccess_start_date(project.getAccess_start_date());
		rule.setMember_id(project.getMember_id());
		rule.setTeam_id(project.getTeam_id());
		
		return sec_team_MemberRepository.save(rule);
	}


	public void deletebyid(int id) {
		sec_team_MemberRepository.deleteById(id);
	}

}
