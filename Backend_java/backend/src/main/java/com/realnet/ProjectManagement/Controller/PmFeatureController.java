package com.realnet.ProjectManagement.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.realnet.ProjectManagement.Entity.Pm_Feature;
import com.realnet.ProjectManagement.Entity.Projectmix;
import com.realnet.ProjectManagement.Repository.FeatureRepo;
import com.realnet.ProjectManagement.Service.FeatureService;

@RestController
public class PmFeatureController {
	
	@Autowired
	private FeatureService featureService;
	
	@Autowired
	private FeatureRepo featureRepo;
	
	@PostMapping("/Feature")
	public ResponseEntity<?> add(@RequestBody Pm_Feature pm_Feature){
			Pm_Feature order= featureService.create(pm_Feature);
			
		return new ResponseEntity<>(order, HttpStatus.OK);
		
	}
	
//	update data with file
	@PutMapping("/Feature/{id}")
	public ResponseEntity<?> update(@RequestBody Pm_Feature pm_Feature, Integer id){
			
		Pm_Feature order = featureService.update(pm_Feature, id);

	
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	

	
	
//	Get all
	@GetMapping("/Feature")
	public ResponseEntity<?> getall(){
		List<Pm_Feature> pm = featureService.getall();
	return 	new ResponseEntity<>(pm, HttpStatus.OK);
	}
	

//	get by id
	@GetMapping("/Feature/{id}")
	public ResponseEntity<?> getbyid(@PathVariable int id){
		Pm_Feature pm = featureService.getbyid(id);

		return 	new ResponseEntity<>(pm, HttpStatus.OK);
	}
	
	
	
//	delete by id
	@DeleteMapping("/Feature/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable int id){

		featureService.deletebyid(id);		
		return new ResponseEntity<>(HttpStatus.OK);
		}

	//GET ID AND NAME
		 @GetMapping("/featurename")
		    public ResponseEntity<?> getfeature() {
		    	
		    	List<Projectmix> s = featureRepo.getfeature();
		    	return new ResponseEntity<>(s,HttpStatus.OK);
		    }

}
