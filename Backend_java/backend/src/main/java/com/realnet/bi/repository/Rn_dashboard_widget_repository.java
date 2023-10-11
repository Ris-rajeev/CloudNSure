package com.realnet.bi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.bi.entity.Rn_Dashboard_Widgets;
import com.realnet.comm.entity.Order;
import com.realnet.fnd.entity.Rn_Module_Setup;

@Repository
public interface Rn_dashboard_widget_repository extends JpaRepository<Rn_Dashboard_Widgets, Integer>{

	@Query(value = "select * from rn_dashboard_widgets_t where module_id =:id", nativeQuery = true)
	List<Rn_Dashboard_Widgets> getByModule(@Param("id") int id);
	
	@Query(value="select * from rn_dashboard_widgets_t where widget_name =:name", nativeQuery = true)
	Rn_Dashboard_Widgets getWidgetsByName(@Param("name") String name);
	
	@Query(value = "SELECT * FROM rn_dashboard_widgets_t WHERE MODULE_ID=:moduleId", nativeQuery = true)
	List<Rn_Dashboard_Widgets> findWidgetsForDropDown(@Param("moduleId") Integer moduleId);
	
}
