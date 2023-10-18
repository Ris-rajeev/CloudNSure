package com.realnet.Sureops_script_apis_back.Controllers;

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

import com.realnet.Sureops_script_apis_back.Entity.sureops_scriptmaster1;
import com.realnet.Sureops_script_apis_back.Repository.sureops_scriptmaster1Repository;
import com.realnet.Sureops_script_apis_back.Services.sureops_scriptmaster1Service;
import com.realnet.users.service1.AppUserServiceImpl;

@RequestMapping(value = "/Sureops_script_master")
@RestController
public class sureops_scriptmasterController {
	@Autowired
	private sureops_scriptmaster1Service Service;
	
	
	@Autowired sureops_scriptmaster1Repository Sureops_script_apislinesRepository;
	
	private AppUserServiceImpl userService;

	@PostMapping("/sureops_scriptmaster1")
	public sureops_scriptmaster1 Savedata(@RequestBody sureops_scriptmaster1 data) {
		sureops_scriptmaster1 save = Service.Savedata(data);
		return save;
	}

	@GetMapping("/sureops_scriptmaster1")
	public List<sureops_scriptmaster1> getdetails() {
		List<sureops_scriptmaster1> get = Service.getdetails();
		return get;
	}

	@GetMapping("/sureops_scriptmaster1/{id}")
	public sureops_scriptmaster1 getdetailsbyId(@PathVariable Long id) {
		sureops_scriptmaster1 get = Service.getdetailsbyId(id);
		return get;
	}

	@DeleteMapping("/sureops_scriptmaster1/{id}")
	public void delete_by_id(@PathVariable Long id) {
		Service.delete_by_id(id);

	}

	@PutMapping("/sureops_scriptmaster1/{id}")
	public sureops_scriptmaster1 update(@RequestBody sureops_scriptmaster1 data, @PathVariable Long id) {
		sureops_scriptmaster1 update = Service.update(data, id);
		return update;
	}

	

}
