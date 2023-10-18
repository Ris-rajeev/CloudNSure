package com.realnet.library.Service;

import com.realnet.BackendConfig.Entity.BackendConfig_t;
import com.realnet.fnd.entity.Rn_Module_Setup;
import com.realnet.fnd.entity.Rn_Project_Setup;
import com.realnet.library.module_library.Entity.Backend_Config_Lib;
import com.realnet.library.module_library.Entity.ModuleLibrary;
import com.realnet.library.project_library.Entity.ProjectLibrary;

public interface LibraryServices {

	public ModuleLibrary rn_moduletoModuleLibrary(Rn_Module_Setup rnmodule);
		
	public Backend_Config_Lib backendConfigtoBackendConfigLib(BackendConfig_t backendconfig);
	
	public ProjectLibrary Rn_projectToProjectLibrary(Rn_Project_Setup projectsetup);

	public Rn_Project_Setup projectLibrarytoRn_ProjectSetup(ProjectLibrary existingProject);

	public Rn_Module_Setup ModuleLibraryTorn_module(ModuleLibrary existingModule);

	public BackendConfig_t backendConfigLibtoBackendConfig_t(Backend_Config_Lib b);
}
