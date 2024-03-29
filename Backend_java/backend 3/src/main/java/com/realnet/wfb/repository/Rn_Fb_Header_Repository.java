package com.realnet.wfb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.wfb.entity.Rn_Fb_Header;

@Repository
public interface Rn_Fb_Header_Repository extends JpaRepository<Rn_Fb_Header, Integer> {
	// for pagination
	Page<Rn_Fb_Header> findAll(Pageable p);

//	@Query(value = "SELECT ID, UI_NAME FROM RN_FB_HEADER", nativeQuery = true)
//	List<Rn_Fb_Header> findFbHeadersForDropDown();

//	// LIST OF MODULES IN A PROJECT
//	@Query(value = "SELECT ID, MODULE_NAME FROM RN_FB_HEADER WHERE MODULE_ID=:moduleId", nativeQuery = true)
//	List<Rn_Fb_Header> findModuleWireframesForDropDown(@Param("moduleId") Integer moduleId);

	// LIST OF MODULES IN A PROJECT
	@Query(value = "SELECT * FROM rn_fb_header WHERE MODULE_ID=:moduleId", nativeQuery = true)
	List<Rn_Fb_Header> findModuleWireframesForDropDown(@Param("moduleId") Integer moduleId);

	@Query(value = "SELECT count(id) FROM rn_fb_header where module_id=?1", nativeQuery = true)
	Object count_wireframe(Integer moduleId);

	@Query(value = "SELECT * FROM rn_fb_header where backend_id=?1", nativeQuery = true)
	List<Rn_Fb_Header> findbybackendid(Long id);

	@Query(value = "SELECT * FROM rn_fb_header where module_id=?1 && backend_id=?2", nativeQuery = true)
	List<Rn_Fb_Header> getallwf(Integer moduleId, Long id);
	
	@Query(value = "SELECT * FROM rn_fb_header where module_id=?1 && backend_id=?2 && child_form=true", nativeQuery = true)
	List<Rn_Fb_Header> getallwfforchild(Integer moduleId, Long id);

	@Query(value = "SELECT * FROM rn_fb_header WHERE header_name LIKE %:keyword%", nativeQuery = true)
	List<Rn_Fb_Header> findWireframesByHeaderNameContainingKeyword(@Param("keyword") String keyword);
}
