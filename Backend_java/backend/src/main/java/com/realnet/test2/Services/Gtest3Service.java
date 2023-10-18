package com.realnet.test2.Services;
import com.realnet.test2.Repository.Gtest3Repository;
import com.realnet.test2.Entity.Gtest3;import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

@Service
 public class Gtest3Service {
@Autowired
private Gtest3Repository Repository;
public Gtest3 Savedata(Gtest3 data) {
				return Repository.save(data);	
			}

			
public List<Gtest3> getdetails() {
				return (List<Gtest3>) Repository.findAll();
			}


public Gtest3 getdetailsbyId(Integer id) {
	return Repository.findById(id).get();
			}


	public void delete_by_id(Integer id) {
 Repository.deleteById(id);
}


public Gtest3 update(Gtest3 data,Integer id) {
	Gtest3 old = Repository.findById(id).get();
old.setSurvey_form(data.getSurvey_form());
old.setName(data.getName());
final Gtest3 test = Repository.save(old);
  return test;}}
