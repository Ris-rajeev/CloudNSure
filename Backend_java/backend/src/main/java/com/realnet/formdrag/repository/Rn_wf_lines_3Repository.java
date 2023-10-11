package com.realnet.formdrag.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.realnet.formdrag.entity.Rn_wf_lines_3;

public interface Rn_wf_lines_3Repository extends JpaRepository<Rn_wf_lines_3, Long> {
	
	@Query(value = "SELECT * FROM realnet_CNSBE.rn_wf_lines_3 where header_id =:header_id", nativeQuery = true)
	Optional<Rn_wf_lines_3>  findheader(@Param("header_id") Integer header_id);

}
