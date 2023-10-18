package com.realnet.Dbconfig.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.Dbconfig.Entity.Dbconfig_t;

@Repository
public interface Dbconfig_Repository extends JpaRepository<Dbconfig_t, Long> {

	@Query(value = "select * from dbconfig_t where proj_id=?1", nativeQuery = true)
	List<Dbconfig_t> findby_projid(Integer proj_id);
}