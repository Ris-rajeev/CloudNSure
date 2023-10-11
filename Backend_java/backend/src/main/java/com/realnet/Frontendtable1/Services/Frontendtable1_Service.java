package com.realnet.Frontendtable1.Services;
import com.realnet.Frontendtable1.Repository.Frontendtable1_Repository;
import com.realnet.Frontendtable1.Entity.Frontendtable1_t;import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

@Service
 public class Frontendtable1_Service {
@Autowired
private Frontendtable1_Repository Repository;
public Frontendtable1_t Savedata(Frontendtable1_t data) {
				return Repository.save(data);	
			}

			
public List<Frontendtable1_t> getdetails() {
				return (List<Frontendtable1_t>) Repository.findAll();
			}


public Frontendtable1_t getdetailsbyId(Long id) {
	return Repository.findById(id).get();
			}


	public void delete_by_id(Long id) {
 Repository.deleteById(id);
}


public Frontendtable1_t update(Frontendtable1_t data,Long id) {
	Frontendtable1_t old = Repository.findById(id).get();
old.setName(data.getName());
final Frontendtable1_t test = Repository.save(old);
		return test;}}