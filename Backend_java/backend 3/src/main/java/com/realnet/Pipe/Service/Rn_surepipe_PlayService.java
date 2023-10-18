package com.realnet.Pipe.Service;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.Pipe.Entity.Rn_surepipe_Workflow;
import com.realnet.Pipe.Entity.Rn_surepipe_play;
import com.realnet.Pipe.Repository.Rn_surepipe_PlayRepository;
import com.realnet.Pipe.Repository.Rn_surepipe_WorkflowRepository;

@Service
public class Rn_surepipe_PlayService {
	
	@Autowired
	private Rn_surepipe_PlayRepository rn_surepipe_PlayRepository;
	
	@Autowired
	private Rn_surepipe_WorkflowRepository rn_surepipe_WorkflowRepository;

	public Rn_surepipe_Workflow create(Rn_surepipe_Workflow wf) {
		
		Rn_surepipe_Workflow saRn_surepipe_Workflow = rn_surepipe_WorkflowRepository.save(wf);
		return saRn_surepipe_Workflow;
	}

	public Rn_surepipe_play create1(@Valid Rn_surepipe_play studentEntity) {
		// TODO Auto-generated method stub
		 Rn_surepipe_play  rn = rn_surepipe_PlayRepository.save(studentEntity) ;
		 return rn;
	}

	public List<Rn_surepipe_play> getallsure() {
	return	(List<Rn_surepipe_play>)  rn_surepipe_PlayRepository.findAll();
		
		
	}

	public Rn_surepipe_play get_by_wfinstanceid(int id) {
		
			Rn_surepipe_play rn =rn_surepipe_PlayRepository.findById(id);
			return rn;
	}
	

	public Rn_surepipe_play updateby_id(int id, Rn_surepipe_play rn_surepipe_play) {
		
		Rn_surepipe_play rn =rn_surepipe_PlayRepository.findById(id);
		
		rn.setAccountId(rn_surepipe_play.getAccountId());
		rn.setProjectid(rn_surepipe_play.getProjectid());
		rn.setRepoid(rn_surepipe_play.getRepoid());
//		rn.setRn_surepipe_Workflow(rn_surepipe_play.getRn_surepipe_Workflow());
		rn.setTemplateid(rn_surepipe_play.getTemplateid());
		rn.setWorkflow_instanceid(rn_surepipe_play.getWorkflow_instanceid());
		
		final Rn_surepipe_play savePlay = rn_surepipe_PlayRepository.save(rn);
		
		
		return savePlay;
	}

	

}
