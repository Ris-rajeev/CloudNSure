package com.realnet.Pipe.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.realnet.Pipe.Entity.rn_surepipe_t;

@Repository
public interface PipeRepository extends CrudRepository<rn_surepipe_t, Integer> {
	
	rn_surepipe_t findById(int id);


}
