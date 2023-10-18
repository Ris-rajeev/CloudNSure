package com.realnet.actionbuilder.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.actionbuilder.entity.Rn_cff_ActionBuilder_Header;
import com.realnet.wfb.entity.Rn_Fb_Header;

@Repository
public interface Rn_CFF_ActionBuilder_Header_Repository extends JpaRepository<Rn_cff_ActionBuilder_Header, Integer> {
	// for pagination
	Page<Rn_cff_ActionBuilder_Header> findAll(Pageable p);
	
	@Query(value = "SELECT count(id) FROM realnet_CNSBE.rn_cff_action_builder_header_t where rn_fb_header_id=?1", nativeQuery = true)
	String count_action(Integer action_id);
}
