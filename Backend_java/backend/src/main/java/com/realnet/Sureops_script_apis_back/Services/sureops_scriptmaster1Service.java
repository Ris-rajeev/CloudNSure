package com.realnet.Sureops_script_apis_back.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.Sureops_script_apis_back.Entity.sureops_scriptmaster1;
import com.realnet.Sureops_script_apis_back.Repository.sureops_scriptmaster1Repository;

@Service
public class sureops_scriptmaster1Service {
	@Autowired
	private sureops_scriptmaster1Repository Repository;
	
	

	public sureops_scriptmaster1 Savedata(sureops_scriptmaster1 data) {
		return Repository.save(data);
	}

	public List<sureops_scriptmaster1> getdetails() {
		return (List<sureops_scriptmaster1>) Repository.findAll();
	}

	public sureops_scriptmaster1 getdetailsbyId(Long id) {
		return Repository.findById(id).get();
	}

	public void delete_by_id(Long id) {
		Repository.deleteById(id);
	}

	public sureops_scriptmaster1 update(sureops_scriptmaster1 data, Long id) {
		sureops_scriptmaster1 old = Repository.findById(id).get();
		old.setScript_name(data.getScript_name());
		old.setDescription(data.getDescription());
		old.setTechstack(data.getTechstack());
		old.setVersion(data.getVersion());
		old.setScript(data.getScript());
		final sureops_scriptmaster1 test = Repository.save(old);
		return test;
	}
	
	
}
