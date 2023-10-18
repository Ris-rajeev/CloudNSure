package com.realnet.report_builder.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.realnet.Dashboard1.Entity.Dashbord_Header;
import com.realnet.report_builder.Entity.Rp_builder;


@Repository
public interface ReportRepository extends CrudRepository<Rp_builder,Integer>{

	Rp_builder	findById(int id);


	
	@Query(
			value= " select * from rp_builder where module_id=?1",nativeQuery=true)
			List<Rp_builder> findbydashboardmodule(int module_id);
	

			
			@Query(value="select count(id) from rp_builder",nativeQuery=true)
			public List<Object> findCount();

}