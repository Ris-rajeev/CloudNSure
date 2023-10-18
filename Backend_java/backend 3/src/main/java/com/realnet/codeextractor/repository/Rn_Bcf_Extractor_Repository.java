package com.realnet.codeextractor.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.codeextractor.entity.Rn_Bcf_Extractor;
import com.realnet.flf.entity.Rn_Bcf_Technology_Stack;

@Repository
public interface Rn_Bcf_Extractor_Repository extends JpaRepository<Rn_Bcf_Extractor, Integer> {
	// for pagination
	Page<Rn_Bcf_Extractor> findAll(Pageable p);

	
	@Query(value = "SELECT * FROM realnet_CNSBE.rn_bcf_extractor_t where tech_stack =?1", nativeQuery=true)
	List<Rn_Bcf_Extractor> findByTech_stack_name(String tech_stack);
}
