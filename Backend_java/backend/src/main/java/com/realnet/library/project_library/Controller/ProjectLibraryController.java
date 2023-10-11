package com.realnet.library.project_library.Controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.realnet.library.project_library.Entity.ProjectLibrary;
import com.realnet.library.project_library.Repository.ProjectLibraryRepository;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserService;

@RestController
@RequestMapping("/projectlibrary")
public class ProjectLibraryController {

	@Autowired
	Rn_ModuleSetup_Repository rn_ModuleSetup_Repository;

	@Autowired
	Rn_ProjectSetup_Repository rn_ProjectSetup_Repository;

	@Autowired
	BackendConfig_Repository rn_backendConfig_Repository;

	@Autowired
	Backend_Config_Lib_Repository bakBackend_Config_Lib_Repository;

	@Autowired
	ModuleLibraryRepository moduleLibraryRepository;

	@Autowired
	ProjectLibraryRepository projectLibraryRepository;

	@Autowired
	LibraryServices libraryServices;
	
	@Autowired
	private AppUserService userService;
	
	//GET ALL PROJECTLIBRARY
		@GetMapping("/getall_projectlibrary")
		public ResponseEntity<?> getAllprojectLibrary(){
			List<ProjectLibrary> projectlibrarylist = projectLibraryRepository.findAll();
			return new ResponseEntity(projectlibrarylist,HttpStatus.OK);
		}

	// COPY RN_PROJECT_SETUP TO PROJECT LIBRARY
	@GetMapping("/copyfromrn_project/{id}")
	public ResponseEntity<?> copyFromRn_ModuletoModule_Library(@PathVariable Integer id) {
		// Assuming you have an existing ProjectLibrary object called "existingProject"
		AppUser loggedInUser = userService.getLoggedInUser();
		
		Optional<Rn_Project_Setup> existingProjectOpt = rn_ProjectSetup_Repository.findById(id);
		if (!existingProjectOpt.isPresent()) {
			return new ResponseEntity<>("Invalid Project id : " + id, HttpStatus.BAD_REQUEST);
		}

		Rn_Project_Setup existingProject = existingProjectOpt.get();

		ProjectLibrary newProject = libraryServices.Rn_projectToProjectLibrary(existingProject);
		newProject.setCreatedBy(loggedInUser.getUserId());
		newProject.setCreatedAt(Date.valueOf(LocalDate.now()));
		ProjectLibrary savedProject = projectLibraryRepository.save(newProject);

		// Copy the list of modules
		List<Rn_Module_Setup> existingModules = existingProject.getModules();
		for (Rn_Module_Setup existingModule : existingModules) {
//save Module
			ModuleLibrary returnedModule = libraryServices.rn_moduletoModuleLibrary(existingModule);
			returnedModule.setCreatedBy(loggedInUser.getUserId());
			returnedModule.setProjectId(savedProject.getId());
			returnedModule.setProjectName(savedProject.getProjectName());
			returnedModule.setProject(savedProject);
			if(savedProject.getModules()!=null) {
				savedProject.getModules().add(returnedModule);
			}else {
				savedProject.setModules(new ArrayList<>());
				savedProject.getModules().add(returnedModule);
			}
			
			ModuleLibrary savedModule = moduleLibraryRepository.save(returnedModule);
//save backend
			 Set<BackendConfig_t> backendExistingList = existingModule.getBackendConfig_ts();
			for (BackendConfig_t b : backendExistingList) {
				Backend_Config_Lib returnedBackend = libraryServices.backendConfigtoBackendConfigLib(b);
				
				returnedBackend.setProj_id(savedProject.getId());
				returnedBackend.getModulelibraries().add(savedModule);
				savedModule.getBackends().add(returnedBackend);
				bakBackend_Config_Lib_Repository.save(returnedBackend);
			}

		}

		return new ResponseEntity<>(savedProject, HttpStatus.OK);
	}

	// COPY FROP PROJECT LIBRARY TO RN_PROJECT_SETUP
	@GetMapping("/copyfromprojectlibrary/{id}")
	public ResponseEntity<?> copyFromModule_LibraryToRn_Moduleto(@PathVariable Integer id) {
		Optional<ProjectLibrary> existingProjectOpt = projectLibraryRepository.findById(id);
		if (!existingProjectOpt.isPresent()) {
			return new ResponseEntity<>("Invalid Project id : " + id, HttpStatus.BAD_REQUEST);
		}
		ProjectLibrary existingProject = existingProjectOpt.get();
		Rn_Project_Setup newProject = libraryServices.projectLibrarytoRn_ProjectSetup(existingProject);
//save new project
		
		AppUser loggedInUser = userService.getLoggedInUser();
		
		newProject.setCreatedBy(loggedInUser.getUserId());
		newProject.setOwned_by(loggedInUser.getUserId());
		newProject.setCreatedAt(Date.valueOf(LocalDate.now()));
		
		Rn_Project_Setup savedProject = rn_ProjectSetup_Repository.save(newProject);

		// Copy the list of modules
		List<ModuleLibrary> existingModules = existingProject.getModules();

		for (ModuleLibrary existingModule : existingModules) {
//save new module	
			Rn_Module_Setup returnedModule = libraryServices.ModuleLibraryTorn_module(existingModule);
			returnedModule.setCreatedBy(loggedInUser.getUserId());
			returnedModule.setCreatedAt(Date.valueOf(LocalDate.now()));
			returnedModule.setProjectId(savedProject.getId());
			returnedModule.setProjectName(savedProject.getProjectName());
			if (savedProject.getModules() != null) {
				savedProject.getModules().add(returnedModule);
			} else {
				savedProject.setModules(new ArrayList<>());
				savedProject.getModules().add(returnedModule);
			}

			Rn_Module_Setup savedModule = rn_ModuleSetup_Repository.save(returnedModule);
//save backend
			List<Backend_Config_Lib> backendExistingList = existingModule.getBackends();
			for (Backend_Config_Lib b : backendExistingList) {
				BackendConfig_t returnedBackend = libraryServices.backendConfigLibtoBackendConfig_t(b);
				returnedBackend.setProj_id(savedProject.getId());
				returnedBackend.getModule_Setups().add(savedModule);
				savedModule.getBackendConfig_ts().add(returnedBackend);
				rn_backendConfig_Repository.save(returnedBackend);
			}
		}
		return new ResponseEntity<>(savedProject, HttpStatus.OK);
	}

}
