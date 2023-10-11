package com.realnet.Working_test.Services;
import com.realnet.Working_test.Repository.Working_test_Repository;
import com.realnet.Working_test.Entity.Working_test_t;import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

@Service
 public class Working_test_Service {
@Autowired
private Working_test_Repository Repository;
public Working_test_t Savedata(Working_test_t data) {
				return Repository.save(data);	
			}

			
public List<Working_test_t> getdetails() {
				return (List<Working_test_t>) Repository.findAll();
			}


public Working_test_t getdetailsbyId(Long id) {
	return Repository.findById(id).get();
			}


	public void delete_by_id(Long id) {
 Repository.deleteById(id);
}


public Working_test_t update(Working_test_t data,Long id) {
	Working_test_t old = Repository.findById(id).get();
old.setWorking_test1(data.getWorking_test1());
final Working_test_t test = Repository.save(old);
		return test;}}