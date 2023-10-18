package com.realnet.Dbconfig.Services;

import com.realnet.Dbconfig.Repository.Dbconfig_Repository;
import com.realnet.Dbconfig.Entity.Dbconfig_t;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Dbconfig_Service {
	@Autowired
	private Dbconfig_Repository Repository;

	public Dbconfig_t Savedata(Dbconfig_t data) {
		return Repository.save(data);
	}

	public List<Dbconfig_t> getdetails() {
		return (List<Dbconfig_t>) Repository.findAll();
	}

	public Dbconfig_t getdetailsbyId(Long id) {
		return Repository.findById(id).get();
	}

	public void delete_by_id(Long id) {
		Repository.deleteById(id);
	}

	public Dbconfig_t update(Dbconfig_t data, Long id) {
		Dbconfig_t old = Repository.findById(id).get();
		old.setDb_name(data.getDb_name());
		old.setDb_type(data.getDb_type());
		old.setDb_username(data.getDb_username());
		old.setDb_password(data.getDb_password());
		old.setPort_no(data.getPort_no());
		old.setExisting_db(data.isExisting_db());
		old.setHost_name(data.getHost_name());
		final Dbconfig_t test = Repository.save(old);
		return test;
	}

	public List<Dbconfig_t> getallby_projid(Integer proj_id) {

		List<Dbconfig_t> list = Repository.findby_projid(proj_id);

		return list;
	}
}