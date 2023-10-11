package com.realnet.library.module_library.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.BackendConfig.Entity.BackendConfig_t;
import com.realnet.library.module_library.Entity.Backend_Config_Lib;

@Repository
public interface Backend_Config_Lib_Repository extends JpaRepository<Backend_Config_Lib, Integer>{

	@Query(value = "select * from backend_config_lib where module_id=?1", nativeQuery = true)
	List<Backend_Config_Lib> findbymoduleid(Integer module_id);

}
