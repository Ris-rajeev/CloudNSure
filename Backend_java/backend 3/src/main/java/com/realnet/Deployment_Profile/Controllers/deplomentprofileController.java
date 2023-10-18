package com.realnet.Deployment_Profile.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.Deployment_Profile.Entity.Deplomentprofile;
import com.realnet.Deployment_Profile.Entity.Deplomentprofilelines;
import com.realnet.Deployment_Profile.Repository.Deploymentprofile_line_repo;
import com.realnet.Deployment_Profile.Repository.deplomentprofileRepository;
import com.realnet.Deployment_Profile.Services.deplomentprofileService;

@RequestMapping(value = "/deployment")
@RestController
public class deplomentprofileController {

	@Autowired
	private deplomentprofileService Service;
	
	@Autowired
	private Deploymentprofile_line_repo linerepo;

	@PostMapping("/deplomentprofile")

	public Deplomentprofile Savedata(@RequestBody Deplomentprofile data) {
		Deplomentprofile save = Service.Savedata(data);
		return save;
	}

	@GetMapping("/deplomentprofile")
	public List<Deplomentprofile> getdetails() {
		List<Deplomentprofile> get = Service.getdetails();
		return get;
	}

	@GetMapping("/deplomentprofile/{id}")
	public Deplomentprofile getdetailsbyId(@PathVariable Long id) {
		Deplomentprofile get = Service.getdetailsbyId(id);
		return get;
	}

	@DeleteMapping("/deplomentprofile/{id}")
	public void delete_by_id(@PathVariable Long id) {
		Service.delete_by_id(id);

	}

	@PutMapping("/deplomentprofile/{id}")
	public Deplomentprofile update(@RequestBody Deplomentprofile data, @PathVariable Long id) {
		Deplomentprofile update = Service.update(data, id);
		return update;
	}
	
	@GetMapping("/deplomentprofile_line")
	public List<Deplomentprofilelines> getallline() {
		List<Deplomentprofilelines> get = linerepo.findAll();
		return get;
	}
}