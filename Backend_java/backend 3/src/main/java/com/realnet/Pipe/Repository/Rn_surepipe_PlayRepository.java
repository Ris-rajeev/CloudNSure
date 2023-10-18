package com.realnet.Pipe.Repository;

import java.util.List;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.realnet.Pipe.Entity.Rn_surepipe_Workflow;
import com.realnet.Pipe.Entity.Rn_surepipe_play;

@Repository
public interface Rn_surepipe_PlayRepository extends CrudRepository<Rn_surepipe_play, Integer> {
	
//	@Query(
//			value= " select * from dashbord_header where module_id=?1",nativeQuery=true)
//			List<Dashbord_Header> findbydashboardmodule(int module_id);

//	@Query( value="select * from rn_surepipe_play where workflow_instanceid=?1", nativeQuery=true)
//			
//	List<Rn_surepipe_play> findByworkflow_instanceid(int workflow_instanceid);
	
	Rn_surepipe_play findById(int id);


}
