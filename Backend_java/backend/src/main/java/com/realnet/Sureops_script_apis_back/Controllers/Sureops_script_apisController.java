package com.realnet.Sureops_script_apis_back.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.realnet.Sureops_script_apis_back.Entity.Script_Lines;
import com.realnet.Sureops_script_apis_back.Entity.Sureops_script_apis;
import com.realnet.Sureops_script_apis_back.Repository.Sureops_script_apisRepository;
import com.realnet.Sureops_script_apis_back.Services.Sureops_script_apisService;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;
import com.realnet.workflow.Entites.Workflow_Line;


@RequestMapping(value = "/Sureops_script_api")
@RestController
public class Sureops_script_apisController {

	@Autowired
	private Sureops_script_apisService Service;
	
	
	@Autowired Sureops_script_apisRepository Sureops_script_apislinesRepository;
	
	private AppUserServiceImpl userService;

	@PostMapping("/Sureops_script_apis")
	public Sureops_script_apis Savedata(@RequestBody Sureops_script_apis data) {
		Sureops_script_apis save = Service.Savedata(data);
		return save;
	}

	@GetMapping("/Sureops_script_apis")
	public List<Sureops_script_apis> getdetails() {
		List<Sureops_script_apis> get = Service.getdetails();
		return get;
	}

	@GetMapping("/Sureops_script_apis/{id}")
	public Sureops_script_apis getdetailsbyId(@PathVariable Long id) {
		Sureops_script_apis get = Service.getdetailsbyId(id);
		return get;
	}

	@DeleteMapping("/Sureops_script_apis/{id}")
	public void delete_by_id(@PathVariable Long id) {
		Service.delete_by_id(id);

	}

	@PutMapping("/Sureops_script_apis/{id}")
	public Sureops_script_apis update(@RequestBody Sureops_script_apis data, @PathVariable Long id) {
		Sureops_script_apis update = Service.update(data, id);
		return update;
	}

	//update script line by id
	@PutMapping("/Sureops_script_line/{id}")
	public Script_Lines updatescript_line(@RequestBody Script_Lines data, @PathVariable Long id) {
		Script_Lines update = Service.updatescript_line(data, id);
		return update;
	}


}