package com.realnet.Globalboard.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.Globalboard.Entity.Rn_board_rule_t;
import com.realnet.Globalboard.Repository.GlobalRepository;

@Service
public class Globalservice {
     
	@Autowired
	private GlobalRepository globalRepository;
	
	public Rn_board_rule_t create(Rn_board_rule_t rnrule) {
		return globalRepository.save(rnrule);
	}

	
	public List<Rn_board_rule_t> getall() {
		return (List<Rn_board_rule_t>) globalRepository.findAll();
	}

	
	public Rn_board_rule_t getbyid(int id) {
		return globalRepository.findById(id);
	}


	public Rn_board_rule_t update(Rn_board_rule_t project) {
		return globalRepository.save(project);
	}
	public Rn_board_rule_t updatebyid(Rn_board_rule_t project, int id) {
		Rn_board_rule_t rule_t = globalRepository.findById(id);
//				.orElseThrow(()-> ResourceNotFoundException("rueboard","id",id));
		rule_t.setAccountId(project.getAccountId());
		rule_t.setAlert_to(project.getAlert_to());
		rule_t.setAssign_to(project.getAssign_to());
		rule_t.setDescription(project.getDescription());
		rule_t.setMessage(project.getMessage());
		rule_t.setOn_event(project.getOn_event());
		rule_t.setRule_name(project.getRule_name());
		rule_t.setSubject(project.getSubject());
		rule_t.setTo_updated_field(project.getTo_updated_field());
		rule_t.setTo_updated_value(project.getUpdated_value());
		rule_t.setUpdated_field(project.getUpdated_field());
		rule_t.setUpdated_value(project.getUpdated_value());
		
		return globalRepository.save(rule_t);
	}

	public void deletebyid(int id) {
		globalRepository.deleteById(id);
	}

}
