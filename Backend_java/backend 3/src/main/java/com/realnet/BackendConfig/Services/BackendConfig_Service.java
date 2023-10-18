package com.realnet.BackendConfig.Services;

import com.realnet.BackendConfig.Repository.BackendConfig_Repository;
import com.realnet.Dbconfig.Entity.Dbconfig_t;
import com.realnet.BackendConfig.Entity.BackendConfig_t;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackendConfig_Service {
	@Autowired
	private BackendConfig_Repository Repository;

	public BackendConfig_t Savedata(BackendConfig_t data) {
		return Repository.save(data);
	}

	public List<BackendConfig_t> getdetails() {
		return (List<BackendConfig_t>) Repository.findAll();
	}

	public BackendConfig_t getdetailsbyId(Long id) {
		return Repository.findById(id).get();
	}

	public void delete_by_id(Long id) {
		Repository.deleteById(id);
	}

	public BackendConfig_t update(BackendConfig_t data, Long id) {
		BackendConfig_t old = Repository.findById(id).get();
		old.setBackend_service_name(data.getBackend_service_name());
		old.setTechstack(data.getTechstack());
		old.setDescription(data.getDescription());
		old.setProj_id(data.getProj_id());
		old.setIsprimary(data.isIsprimary());
		old.setDb_id(data.getDb_id());
		old.setModule_Setups(data.getModule_Setups());

		final BackendConfig_t test = Repository.save(old);
		return test;
	}

//	get all via module id
	public List<BackendConfig_t> getallvia_projid(Integer proj_id) {
		List<BackendConfig_t> list = Repository.findbyproj_id(proj_id);

		return list;
	}
}