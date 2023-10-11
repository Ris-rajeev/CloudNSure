package com.realnet.bi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.bi.entity.Rn_Dashboard_Header;

@Repository
public interface Rn_Dashboard_Header_Repository extends JpaRepository<Rn_Dashboard_Header, Integer>{

	
	@Query(value = "select * from rn_dashboard_header_t where module_id =:id", nativeQuery = true)
	List<Rn_Dashboard_Header> getByModule(@Param("id") int id);

}
