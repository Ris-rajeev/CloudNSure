package com.realnet.test2.Services;
import com.realnet.test2.Repository.Gtest2Repository;
import com.realnet.test2.Entity.Gtest2;import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

@Service
 public class Gtest2Service {
@Autowired
private Gtest2Repository Repository;
public Gtest2 Savedata(Gtest2 data) {
				return Repository.save(data);	
			}

			
public List<Gtest2> getdetails() {
				return (List<Gtest2>) Repository.findAll();
			}


public Gtest2 getdetailsbyId(Integer id) {
	return Repository.findById(id).get();
			}


	public void delete_by_id(Integer id) {
 Repository.deleteById(id);
}


public Gtest2 update(Gtest2 data,Integer id) {
	Gtest2 old = Repository.findById(id).get();
old.setCountry(data.getCountry());
old.setName(data.getName());
final Gtest2 test = Repository.save(old);
  return test;}}
