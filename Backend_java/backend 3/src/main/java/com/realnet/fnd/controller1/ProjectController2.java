package com.realnet.fnd.controller1;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.Dbconfig.Repository.Dbconfig_Repository;
import com.realnet.fnd.entity.Rn_Module_Setup;
import com.realnet.fnd.entity.Rn_Project_Setup;
import com.realnet.fnd.repository.Rn_ModuleSetup_Repository;
import com.realnet.fnd.repository.Rn_ProjectSetup_Repository;

@RestController
@RequestMapping("/fnd/project2")
public class ProjectController2 {

	@Autowired
	private Rn_ProjectSetup_Repository projectSetupRepository;

	@Autowired
	private Dbconfig_Repository dbconfig_Repository;

	@Autowired
	private Rn_ModuleSetup_Repository moduleSetup_Repository;

	@GetMapping("/techstacks/{proj_id}")
	public ResponseEntity<?> gettechstackfromproj_id(@PathVariable Integer proj_id) {

		Optional<Rn_Project_Setup> projectOpt = projectSetupRepository.findById(proj_id);
		if (!projectOpt.isPresent()) {
			return new ResponseEntity<>("Invalid Project id", HttpStatus.BAD_REQUEST);
		}
		Set<String> result = new HashSet<>();

		Rn_Project_Setup project = projectOpt.get();
		result.add(project.getTechnologyStack());
		project.getModules().forEach(mod -> {
			result.add(mod.getTechnologyStack());
			mod.getBackendConfig_ts().forEach(back -> {
				result.add(back.getTechstack());
			});
		});

		dbconfig_Repository.findby_projid(proj_id).forEach(dbcon -> {
			result.add(dbcon.getTechstack());
		});
		result.removeIf(tech -> tech == null);
		result.removeIf(tech -> tech.isEmpty());

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@GetMapping("/module/flutter/{proj_id}")
	public ResponseEntity<?> getallmodule_flutter(@PathVariable Integer proj_id) {

		List<Rn_Module_Setup> list = moduleSetup_Repository.getallmodule_flutter(proj_id);

		return new ResponseEntity<>(list, HttpStatus.OK);

	}

}
