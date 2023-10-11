package com.realnet.BackendConfig.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.BackendConfig.Entity.BackendConfig_t;

@Repository
public interface BackendConfig_Repository extends JpaRepository<BackendConfig_t, Long> {

	@Query(value = "select * from backend_config_t where proj_id=?1", nativeQuery = true)
	List<BackendConfig_t> findbyproj_id(Integer proj_id);

}