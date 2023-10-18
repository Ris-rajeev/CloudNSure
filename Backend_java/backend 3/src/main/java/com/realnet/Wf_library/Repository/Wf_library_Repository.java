package com.realnet.Wf_library.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.Wf_library.Entity.Wf_library_t;

@Repository
public interface Wf_library_Repository extends JpaRepository<Wf_library_t, Long> {

	@Query(value = "SELECT * FROM wf_library_t where ischild=false", nativeQuery = true)
	List<Wf_library_t> getall();

	@Query(value = "SELECT * FROM wf_library_t where library_id=?1 && ischild=true", nativeQuery = true)
	List<Wf_library_t> getallby_libid(Long libraryId);
}