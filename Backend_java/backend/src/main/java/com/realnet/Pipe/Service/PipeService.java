package com.realnet.Pipe.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.Globalboard.Entity.Rn_board_rule_t;
import com.realnet.Pipe.Entity.rn_surepipe_t;
import com.realnet.Pipe.Repository.PipeRepository;

@Service
public class PipeService {
	
	@Autowired
	private PipeRepository pipeRepository;

	public rn_surepipe_t create(rn_surepipe_t rnrule) {
		return pipeRepository.save(rnrule);
	}

	
	public List<rn_surepipe_t> getall() {
		return (List<rn_surepipe_t>) pipeRepository.findAll();
	}

	
	public rn_surepipe_t getbyid(int id) {
		return pipeRepository.findById(id);
	}


	public rn_surepipe_t update(rn_surepipe_t project) {
		return pipeRepository.save(project);
	}


	public void deletebyid(int id) {
		pipeRepository.deleteById(id);
	}
	
}
