package com.realnet.Check.Services;
import com.realnet.Check.Repository.Check_Repository;
import com.realnet.Check.Entity.Check_t;import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

@Service
 public class Check_Service {
@Autowired
private Check_Repository Repository;
public Check_t Savedata(Check_t data) {
				return Repository.save(data);	
			}

			
public List<Check_t> getdetails() {
				return (List<Check_t>) Repository.findAll();
			}


public Check_t getdetailsbyId(Long id) {
	return Repository.findById(id).get();
			}


	public void delete_by_id(Long id) {
 Repository.deleteById(id);
}


public Check_t update(Check_t data,Long id) {
	Check_t old = Repository.findById(id).get();
old.setCheck_1(data.getCheck_1());
final Check_t test = Repository.save(old);
		return test;}}