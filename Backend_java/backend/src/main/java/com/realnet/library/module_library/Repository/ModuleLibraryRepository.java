package com.realnet.library.module_library.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.BackendConfig.Entity.BackendConfig_t;
import com.realnet.library.module_library.Entity.Backend_Config_Lib;
import com.realnet.library.module_library.Entity.ModuleLibrary;

@Repository
public interface ModuleLibraryRepository extends JpaRepository<ModuleLibrary, Integer>{
	
}
