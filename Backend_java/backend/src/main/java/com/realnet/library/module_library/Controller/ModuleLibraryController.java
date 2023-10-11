package com.realnet.library.module_library.Controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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

import com.realnet.BackendConfig.Entity.BackendConfig_t;
import com.realnet.BackendConfig.Repository.BackendConfig_Repository;
import com.realnet.fnd.entity.Rn_Module_Setup;
import com.realnet.fnd.entity.Rn_Project_Setup;
import com.realnet.fnd.repository.Rn_ModuleSetup_Repository;
import com.realnet.fnd.repository.Rn_ProjectSetup_Repository;
import com.realnet.library.Service.LibraryServices;
import com.realnet.library.module_library.Entity.Backend_Config_Lib;
import com.realnet.library.module_library.Entity.ModuleLibrary;
import com.realnet.library.module_library.Repository.Backend_Config_Lib_Repository;
import com.realnet.library.module_library.Repository.ModuleLibraryRepository;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/library/modulelibrary")
public class ModuleLibraryController {

	@Autowired
	private ModuleLibraryRepository modulelibraryrepo;

	@Autowired
	private Backend_Config_Lib_Repository backendconfigLibrepo;

	@Autowired
	private Rn_ModuleSetup_Repository rn_module_setup_repo;

	@Autowired
	private BackendConfig_Repository backendcofigrepo;

	@Autowired
	private LibraryServices libraryServices;

	@Autowired
	private Rn_ProjectSetup_Repository projectSetup_Repository;
	
	@Autowired
	private AppUserService userService;
	
	//GET ALL MODULES LIST
	@GetMapping("/getall_module_lib")
	public ResponseEntity<?> getallmodulelib(){
		List<ModuleLibrary> modulesList = modulelibraryrepo.findAll();
		return new ResponseEntity(modulesList,HttpStatus.OK);
	}

	// COPY RN_MODULE_SETUP TO MODULE LIBRARY
	@GetMapping("/copyfromrn_module/{id}")
	public ResponseEntity<?> copyFromRn_ModuletoModule_Library(@PathVariable Integer id) {
		Optional<Rn_Module_Setup> existingModuleOpt = rn_module_setup_repo.findById(id);
		if (!existingModuleOpt.isPresent()) {
			return new ResponseEntity<>("Invalid Module id : " + id, HttpStatus.BAD_REQUEST);
		}
		AppUser loggedInUser = userService.getLoggedInUser();
		Rn_Module_Setup existingModule = existingModuleOpt.get();
		ModuleLibrary newModuleLibrary = libraryServices.rn_moduletoModuleLibrary(existingModule);
		newModuleLibrary.setCreatedBy(loggedInUser.getUserId());
		newModuleLibrary.setCreatedAt(Date.valueOf(LocalDate.now()));
		 Set<BackendConfig_t> backendExistingList = existingModule.getBackendConfig_ts();
		System.out.println(backendExistingList.size());
		for (BackendConfig_t b : backendExistingList) {
			Backend_Config_Lib backendconnew = libraryServices.backendConfigtoBackendConfigLib(b);
			 backendconnew.getModulelibraries().add(newModuleLibrary); 
			newModuleLibrary.getBackends().add(backendconnew);
			
		}
		ModuleLibrary savedModule = modulelibraryrepo.save(newModuleLibrary);
		return new ResponseEntity<>(savedModule, HttpStatus.OK);
	}

	// COPY MODULE LIBRARY TO MODULE
	@GetMapping("/copyfrommodulelibrarytomodule/{library_id}/{proj_id}")
	public ResponseEntity<?> copyFromModuleLibrary(@PathVariable Integer library_id, @PathVariable Integer proj_id) {
		Optional<ModuleLibrary> existingModuleOpt = modulelibraryrepo.findById(library_id);
		if (!existingModuleOpt.isPresent()) {
			return new ResponseEntity<>("Invalid Module Library id : " + library_id, HttpStatus.BAD_REQUEST);
		}

		ModuleLibrary existingModule = existingModuleOpt.get();
		
		Rn_Module_Setup newModuleSetup = libraryServices.ModuleLibraryTorn_module(existingModule);
		AppUser loggedInUser = userService.getLoggedInUser();
		newModuleSetup.setCreatedBy(loggedInUser.getUserId());
		newModuleSetup.setCreatedAt(Date.valueOf(LocalDate.now()));
		Optional<Rn_Project_Setup> projectexistopt = projectSetup_Repository.findById(proj_id);
		if(!projectexistopt.isPresent()) {
			return new ResponseEntity<>("Invalid Project id : " + library_id, HttpStatus.BAD_REQUEST);
		}
		Rn_Project_Setup projectexist = projectexistopt.get();
		newModuleSetup.setProject(projectexist);
		newModuleSetup.setProjectId(projectexist.getId());
		projectexist.getModules().add(newModuleSetup);

		// ------------------Backend Lib to rn------------------------------------
		Set<BackendConfig_t> newBackedset = new HashSet<>();
		List<Backend_Config_Lib> backendExistingList = existingModule.getBackends();
		for (Backend_Config_Lib b : backendExistingList) {

			BackendConfig_t backendconnew = libraryServices.backendConfigLibtoBackendConfig_t(b);
			backendconnew.setProj_id(projectexist.getId());
			newBackedset.add(backendconnew);
		}
		
		for(BackendConfig_t back:newBackedset) {
			back.getModule_Setups().add(newModuleSetup);
			newModuleSetup.getBackendConfig_ts().add(back);
		}

		Rn_Project_Setup updatedProject = projectSetup_Repository.save(projectexist);
		return new ResponseEntity<>(updatedProject, HttpStatus.OK);
	}

}
