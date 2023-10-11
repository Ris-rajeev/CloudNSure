package com.realnet.Dbconfig.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.realnet.BackendConfig.Entity.BackendConfig_t;
import com.realnet.Dbconfig.Entity.Dbconfig_t;
import com.realnet.Dbconfig.Repository.Dbconfig_Repository;
import com.realnet.Dbconfig.Services.Dbconfig_Service;
import com.realnet.fnd.entity.Rn_Module_Setup;
import com.realnet.fnd.repository.Rn_ModuleSetup_Repository;

@RequestMapping(value = "/Dbconfig")
@RestController
public class Dbconfig_Controller {

	@Autowired
	private Dbconfig_Service Service;

	@Autowired
	private Dbconfig_Repository dbconfig_Repository;

	@Autowired
	private Rn_ModuleSetup_Repository moduleSetup_Repository;

	@PostMapping("/Dbconfig")
	public Dbconfig_t Savedata(@RequestBody Dbconfig_t data) {
		Dbconfig_t save = Service.Savedata(data);
		return save;
	}

	@GetMapping("/Dbconfig")
	public List<Dbconfig_t> getdetails() {
		List<Dbconfig_t> get = Service.getdetails();
		return get;
	}

	@GetMapping("/Dbconfig/{id}")
	public Dbconfig_t getdetailsbyId(@PathVariable Long id) {
		Dbconfig_t get = Service.getdetailsbyId(id);
		return get;

	}

	@DeleteMapping("/Dbconfig/{id}")
	public void delete_by_id(@PathVariable Long id) {
		Service.delete_by_id(id);

	}

	@PutMapping("/Dbconfig/{id}")
	public Dbconfig_t update(@RequestBody Dbconfig_t data, @PathVariable Long id) {
		Dbconfig_t update = Service.update(data, id);
		return update;
	}

//	GET ALL DATABASES BY MODULE ID
	@GetMapping("/bymoduleid/{moduleId}")
	public ResponseEntity<List<Dbconfig_t>> getDbConfigurationsByModuleId(@PathVariable Integer moduleId) {

		ArrayList<Dbconfig_t> dbconfig = new ArrayList<>();
		Optional<Rn_Module_Setup> module = moduleSetup_Repository.findById(moduleId);
		if (module.isPresent()) {
			Set<BackendConfig_t> backendConfigs = module.get().getBackendConfig_ts();

			backendConfigs.forEach(i -> dbconfig.add(dbconfig_Repository.findById(i.getDb_id()).get()));

			return ResponseEntity.ok(dbconfig);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

//	GET ALL DATABASES BY PROJ ID
	@GetMapping("/by_proj_id/{proj_id}")
	public List<Dbconfig_t> getallb_proj_id(@PathVariable Integer proj_id) {
		List<Dbconfig_t> get = Service.getallby_projid(proj_id);
		return get;
	}
}