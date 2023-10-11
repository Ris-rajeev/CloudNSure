package com.realnet.listbuilder.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.Dashboard1.Entity.Dashbord1_Line;
import com.realnet.listbuilder.Entity.Lb_Line;

@Repository
public interface Lb_lineRepository extends CrudRepository<Lb_Line, Integer> {

	Lb_Line findById(int id);
	
	@Query(value = "SELECT * FROM realnet_CNSBE.lb_line where dashbord_header_id =:id", nativeQuery = true)
	List<Lb_Line> getByheader(@Param("id") int id);

}
