package com.realnet.Deployment_Profile.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.realnet.AudiTrail.Service.AuditrailService;
import com.realnet.Deployment_Profile.Entity.Deplomentprofile;
import com.realnet.Deployment_Profile.Repository.deplomentprofileRepository;

@Service
public class deplomentprofileService {
	@Autowired
	private deplomentprofileRepository Repository;

	@Autowired
	private AuditrailService auditrailService;
	
	public Deplomentprofile Savedata(Deplomentprofile data) {
		return Repository.save(data);
	}

	public List<Deplomentprofile> getdetails() {
		return (List<Deplomentprofile>) Repository.findAll();
	}

	public Deplomentprofile getdetailsbyId(Long id) {
		return Repository.findById(id).get();
	}

	public void delete_by_id(Long id) {
		Repository.deleteById(id);
	}

	public Deplomentprofile update(Deplomentprofile data, Long id) {
		Deplomentprofile old = Repository.findById(id).get();
		old.setProfile_name(data.getProfile_name());
		old.setEnd_date(data.getEnd_date());
		old.setActive_flag(data.getActive_flag());
		old.setDesc_header_table(data.getDesc_header_table());
		final Deplomentprofile test = Repository.save(old);
		try {
			auditrailService.saveaudiTrail_t(old, test,"Deplomentprofile");
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return test;
	}
}