package com.realnet.library.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.BackendConfig.Entity.BackendConfig_t;
import com.realnet.BackendConfig.Repository.BackendConfig_Repository;
import com.realnet.Dbconfig.Entity.Dbconfig_t;
import com.realnet.Dbconfig.Repository.Dbconfig_Repository;
import com.realnet.fnd.entity.Rn_Module_Setup;
import com.realnet.fnd.entity.Rn_Project_Setup;
import com.realnet.library.module_library.Entity.Backend_Config_Lib;
import com.realnet.library.module_library.Entity.Dbconfig_Lib;
import com.realnet.library.module_library.Entity.ModuleLibrary;
import com.realnet.library.module_library.Repository.Backend_Config_Lib_Repository;
import com.realnet.library.module_library.Repository.ModuleLibraryRepository;
import com.realnet.library.project_library.Entity.ProjectLibrary;
@Service
public class LibraryServicesImpl implements LibraryServices{
	
	@Autowired
	ModuleLibraryRepository modulelibraryrepo;
	
	@Autowired
	BackendConfig_Repository backendcofigrepo;
	
	@Autowired
	Backend_Config_Lib_Repository backendconfigLibrepo;
	
	@Autowired
	Dbconfig_Repository dbconfigrepo;

	
	//RN_MODULESETUP TO MODULE_LIBRARY
	@Override
	public ModuleLibrary rn_moduletoModuleLibrary(Rn_Module_Setup existingModule) {
		ModuleLibrary newModuleLibrary = new ModuleLibrary();

		newModuleLibrary.setCopy_baseproj(existingModule.isCopy_baseproj());
		newModuleLibrary.setCopyTo(existingModule.getCopyTo());
		newModuleLibrary.setDbName(existingModule.getDbName());
		newModuleLibrary.setDbPassword(existingModule.getDbPassword());
		newModuleLibrary.setDbUserName(existingModule.getDbUserName());
		newModuleLibrary.setDescription(existingModule.getDescription());
		newModuleLibrary.setLoginservice(existingModule.isLoginservice());
		newModuleLibrary.setMicroservice(existingModule.isMicroservice());
		newModuleLibrary.setModuleName(existingModule.getModuleName());
		newModuleLibrary.setModulePrefix(existingModule.getModulePrefix());
		newModuleLibrary.setParentrepo(existingModule.getParentrepo());
		newModuleLibrary.setPortaldeployment(existingModule.isPortaldeployment());
		newModuleLibrary.setPortNumber(existingModule.getPortNumber());
		newModuleLibrary.setProjectId(existingModule.getProjectId());
		newModuleLibrary.setProjectName(existingModule.getProjectName());
		newModuleLibrary.setReadme(existingModule.isReadme());
		newModuleLibrary.setTags(existingModule.getTags());
		newModuleLibrary.setTechnologyStack(existingModule.getTechnologyStack());
		newModuleLibrary.setTesting(existingModule.isTesting());
	
		
		return newModuleLibrary;
	}




//BACKEND TO BACKEND_CONFIG_LIB
	@Override
	public Backend_Config_Lib backendConfigtoBackendConfigLib(BackendConfig_t b) {
		Backend_Config_Lib backendconnew = new Backend_Config_Lib();
		backendconnew.setBackend_service_name(b.getBackend_service_name());
		backendconnew.setDescription(b.getDescription());
		backendconnew.setProj_id(b.getProj_id());
		backendconnew.setTechstack(b.getTechstack());
		backendconnew.setIsprimary(b.isIsprimary());
		
		return backendconnew;
	}
//	
//	
//	//BACKEND_CONFIG_LIB TO BACKEND
	@Override
	public BackendConfig_t backendConfigLibtoBackendConfig_t(Backend_Config_Lib b) {
		BackendConfig_t backendconnew = new BackendConfig_t();
		backendconnew.setBackend_service_name(b.getBackend_service_name());
		backendconnew.setDescription(b.getDescription());        
		backendconnew.setProj_id(b.getProj_id());
		backendconnew.setTechstack(b.getTechstack());
		backendconnew.setIsprimary(b.isIsprimary());
		
		return backendconnew;
	}

	
//	//RN_PROJECT TO PROJECT LIBRARY
	@Override
	public ProjectLibrary Rn_projectToProjectLibrary(Rn_Project_Setup existingProject) {
		ProjectLibrary newProject = new ProjectLibrary();
		
		newProject.setOwner(existingProject.getOwner());
		newProject.setOwned_by(existingProject.getOwned_by());
		newProject.setProjectName(existingProject.getProjectName());
		newProject.setDescription(existingProject.getDescription());
		newProject.setCopyTo(existingProject.getCopyTo());
		newProject.setTechnologyStack(existingProject.getTechnologyStack());
		newProject.setProjectPrefix(existingProject.getProjectPrefix());
		newProject.setDbName(existingProject.getDbName());
		newProject.setDbUserName(existingProject.getDbUserName());
		newProject.setDbPassword(existingProject.getDbPassword());
		newProject.setPortNumber(existingProject.getPortNumber());
		newProject.setNamespace(existingProject.getNamespace());
		newProject.setTags(existingProject.getTags());
		newProject.setCategory(existingProject.getCategory());
		newProject.setAccessibility(existingProject.getAccessibility());
		newProject.setIs_archived(existingProject.getIs_archived());
		newProject.setIs_aged(existingProject.getIs_aged());
		newProject.setIs_fav(existingProject.getIs_fav());
		newProject.setFavCnt(existingProject.getFavCnt());
		newProject.setIs_stared(existingProject.getIs_stared());
		newProject.setStaredCnt(existingProject.getStaredCnt());
		newProject.setIs_watchlisted(existingProject.getIs_watchlisted());
		newProject.setWatchlistedCnt(existingProject.getWatchlistedCnt());
		newProject.setIs_futuristic(existingProject.getIs_futuristic());
		newProject.setFuturisticCnt(existingProject.getFuturisticCnt());
		newProject.setIs_pinned(existingProject.getIs_pinned());
		newProject.setPinnedCnt(existingProject.getPinnedCnt());

		newProject.setWorkflow_id(existingProject.getWorkflow_id());
		newProject.setGitea_url(existingProject.getGitea_url());
		
		return newProject;
	}
	
//	//PROJECT LIBRARY TO RN_PROJECT_SETUP
	@Override
	public Rn_Project_Setup projectLibrarytoRn_ProjectSetup(ProjectLibrary existingProject) {
		Rn_Project_Setup newProject = new Rn_Project_Setup();
		
		newProject.setOwner(existingProject.getOwner());
		newProject.setOwned_by(existingProject.getOwned_by());
		newProject.setProjectName(existingProject.getProjectName());
		newProject.setDescription(existingProject.getDescription());
		newProject.setCopyTo(existingProject.getCopyTo());
		newProject.setTechnologyStack(existingProject.getTechnologyStack());
		newProject.setProjectPrefix(existingProject.getProjectPrefix());
		newProject.setDbName(existingProject.getDbName());
		newProject.setDbUserName(existingProject.getDbUserName());
		newProject.setDbPassword(existingProject.getDbPassword());
		newProject.setPortNumber(existingProject.getPortNumber());
		newProject.setNamespace(existingProject.getNamespace());
		newProject.setTags(existingProject.getTags());
		newProject.setCategory(existingProject.getCategory());
		newProject.setAccessibility(existingProject.getAccessibility());
		newProject.setIs_archived(existingProject.getIs_archived());
		newProject.setIs_aged(existingProject.getIs_aged());
		newProject.setIs_fav(existingProject.getIs_fav());
		newProject.setFavCnt(existingProject.getFavCnt());
		newProject.setIs_stared(existingProject.getIs_stared());
		newProject.setStaredCnt(existingProject.getStaredCnt());
		newProject.setIs_watchlisted(existingProject.getIs_watchlisted());
		newProject.setWatchlistedCnt(existingProject.getWatchlistedCnt());
		newProject.setIs_futuristic(existingProject.getIs_futuristic());
		newProject.setFuturisticCnt(existingProject.getFuturisticCnt());
		newProject.setIs_pinned(existingProject.getIs_pinned());
		newProject.setPinnedCnt(existingProject.getPinnedCnt());

		newProject.setWorkflow_id(existingProject.getWorkflow_id());
		newProject.setGitea_url(existingProject.getGitea_url());
		
		return newProject;
	}

//	//MODULE_LIBRARY TO RN_MODUEL SETUP
	@Override
	public Rn_Module_Setup ModuleLibraryTorn_module(ModuleLibrary existingModule) {
		Rn_Module_Setup newModuleSetup = new Rn_Module_Setup();

	    newModuleSetup.setModuleName(existingModule.getModuleName());
	    newModuleSetup.setDescription(existingModule.getDescription());
	    newModuleSetup.setModulePrefix(existingModule.getModulePrefix());
	    newModuleSetup.setCopyTo(existingModule.getCopyTo());
	    newModuleSetup.setTechnologyStack(existingModule.getTechnologyStack());
	    newModuleSetup.setTags(existingModule.getTags());
	    newModuleSetup.setReadme(existingModule.isReadme());
	    newModuleSetup.setDbName(existingModule.getDbName());
	    newModuleSetup.setDbUserName(existingModule.getDbUserName());
	    newModuleSetup.setDbPassword(existingModule.getDbPassword());
	    newModuleSetup.setPortNumber(existingModule.getPortNumber());
	    newModuleSetup.setMicroservice(existingModule.isMicroservice());
	    newModuleSetup.setPortaldeployment(existingModule.isPortaldeployment());
	    newModuleSetup.setParentrepo(existingModule.getParentrepo());
	    newModuleSetup.setCopy_baseproj(existingModule.isCopy_baseproj());
	    newModuleSetup.setLoginservice(existingModule.isLoginservice());
	    newModuleSetup.setTesting(existingModule.isTesting());

	    return newModuleSetup;
		
	}

	
	
	

}
