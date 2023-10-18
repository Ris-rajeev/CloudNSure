package com.realnet.Sureops_script_apis_back.Services;

import com.realnet.Sureops_script_apis_back.Repository.Sureops_script_apisRepository;
import com.realnet.Sureops_script_apis_back.Repository.Sureops_script_apislinesRepository;
import com.realnet.Sureops_script_apis_back.Entity.Script_Lines;
import com.realnet.Sureops_script_apis_back.Entity.Sureops_script_apis;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Sureops_script_apisService {
	@Autowired
	private Sureops_script_apisRepository Repository;
	
	@Autowired
	private Sureops_script_apislinesRepository line_Repository;
	

	public Sureops_script_apis Savedata(Sureops_script_apis data) {
		return Repository.save(data);
	}

	public List<Sureops_script_apis> getdetails() {
		return (List<Sureops_script_apis>) Repository.findAll();
	}

	public Sureops_script_apis getdetailsbyId(Long id) {
		return Repository.findById(id).get();
	}

	public void delete_by_id(Long id) {
		Repository.deleteById(id);
	}

	public Sureops_script_apis update(Sureops_script_apis data, Long id) {
		Sureops_script_apis old = Repository.findById(id).get();
		old.setScript_name(data.getScript_name());
		old.setDescription(data.getDescription());
		old.setTechstack(data.getTechstack());
		old.setVersion(data.getVersion());
		//old.setScript(data.getScript());
		final Sureops_script_apis test = Repository.save(old);
		return test;
	}
	
	public Script_Lines updatescript_line(Script_Lines data, Long id) {
		Script_Lines old = line_Repository.findById(id).get();
		old.setModel(data.getModel());
		final Script_Lines test = line_Repository.save(old);
		return test;
	}
}