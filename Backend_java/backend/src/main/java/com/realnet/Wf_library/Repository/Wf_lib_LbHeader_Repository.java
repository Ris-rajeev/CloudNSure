package com.realnet.Wf_library.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.Wf_library.Entity.WfLib_Lb_Header;

@Repository
public interface Wf_lib_LbHeader_Repository extends JpaRepository<WfLib_Lb_Header, Long> {

//	@Query(value = "SELECT * FROM wf_library_t where ischild=false", nativeQuery = true)
//	List<Wf_library_t> getall();
//
	@Query(value = "SELECT * FROM wflib_lb_header where library_id=?1", nativeQuery = true)
	List<WfLib_Lb_Header> getallby_libid(Long libraryId);
}