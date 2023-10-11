package com.realnet.bi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.bi.entity.Rn_Dashboard_Line;

@Repository
public interface Rn_Dashboard_Line_Repository extends JpaRepository<Rn_Dashboard_Line, Integer>{

	@Query(value = "select * from rn_dashboard_line_t where header_id =:id", nativeQuery = true)
	List<Rn_Dashboard_Line> getWidgets(@Param("id") int id);
}
