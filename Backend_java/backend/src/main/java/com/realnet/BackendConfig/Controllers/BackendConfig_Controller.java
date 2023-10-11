package com.realnet.BackendConfig.Controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.BackendConfig.Entity.BackendConfig_t;
import com.realnet.BackendConfig.Services.BackendConfig_Service;
import com.realnet.entitybuilder.response.EntityResponse;
import com.realnet.fnd.entity.Rn_Module_Setup;
import com.realnet.fnd.repository.Rn_ModuleSetup_Repository;

@RequestMapping(value = "/BackendConfig")
@RestController
public class BackendConfig_Controller {

	@Autowired
	private BackendConfig_Service Service;

	@Autowired
	private Rn_ModuleSetup_Repository moduleSetup_Repository;

	@PostMapping("/BackendConfig")
	public BackendConfig_t Savedata(@RequestBody BackendConfig_t data) {
		BackendConfig_t save = Service.Savedata(data);
		return save;
	}

	@GetMapping("/BackendConfig")
	public List<BackendConfig_t> getdetails() {
		List<BackendConfig_t> get = Service.getdetails();
		return get;
	}

	@GetMapping("/BackendConfig/{id}")
	public BackendConfig_t getdetailsbyId(@PathVariable Long id) {
		BackendConfig_t get = Service.getdetailsbyId(id);
		return get;

	}

	@DeleteMapping("/BackendConfig/{id}")
	public void delete_by_id(@PathVariable Long id) {
		Service.delete_by_id(id);

	}

	@PutMapping("/BackendConfig/{id}")
	public BackendConfig_t update(@RequestBody BackendConfig_t data, @PathVariable Long id) {
		BackendConfig_t update = Service.update(data, id);
		return update;
	}

// GET ALL BACKEND BY MODULE ID
	@GetMapping("/moduleid/{moduleId}")
	public ResponseEntity<Set<BackendConfig_t>> getBackendConfigurationsByModuleId(@PathVariable Integer moduleId) {
		Optional<Rn_Module_Setup> module = moduleSetup_Repository.findById(moduleId);
		if (module.isPresent()) {
			Set<BackendConfig_t> backendConfigs = module.get().getBackendConfig_ts();
			return ResponseEntity.ok(backendConfigs);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

//	GET ALL BACKEND BY PROJECT ID
	@GetMapping("/by_project/{proj_id}")
	public ResponseEntity<?> getviamodule(@PathVariable Integer proj_id) {
		List<BackendConfig_t> get = Service.getallvia_projid(proj_id);
		
			return new ResponseEntity<>(get, HttpStatus.OK);

		

	}
}