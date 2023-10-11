package com.realnet.Type.Controller;

import java.util.ArrayList;
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

import com.realnet.Type.Repository.TypeRepo;
import com.realnet.Type.entity.Dd_type;
import com.realnet.Type.entity.Sec_type;
import com.realnet.exceptions.ResourceNotFoundException;

@RestController
public class TypeController {
	
	@Autowired
	private TypeRepo typeRepo;
	
	// CREATE DATA
		@PostMapping("/Sec_type")
		public ResponseEntity<?> add(@RequestBody List<Sec_type> sec_type){
			 List<Sec_type>  order= typeRepo.saveAll(sec_type);
				
			return new ResponseEntity<>(order, HttpStatus.OK);
			
		}
		
//		update data with file
		@PutMapping("/Sec_type/{id}")
		public ResponseEntity<?> update(@RequestBody Sec_type sec_type, @PathVariable int id){
			Sec_type type = typeRepo.findById(id)
					.orElseThrow(()-> new ResourceNotFoundException("type not found !!"+ id));
				
			type.setName(sec_type.getName());
			type.setDescription(sec_type.getDescription());
			
			Sec_type save = typeRepo.save(type);

		
			return new ResponseEntity<>(save, HttpStatus.OK);
		}
		

		
		
//		Get all
		@GetMapping("/Sec_type")
		public ResponseEntity<?> getall(){
			List<Sec_type> pm = typeRepo.findAll();
		return 	new ResponseEntity<>(pm, HttpStatus.OK);
		}
		

//		get by id
		@GetMapping("/Sec_type/{id}")
		public ResponseEntity<?> getbyid(@PathVariable int id){
			Sec_type type = typeRepo.findById(id)
					.orElseThrow(()-> new ResourceNotFoundException("type not found !!"+ id));

			return 	new ResponseEntity<>(type, HttpStatus.OK);
		}
		
		
		
//		delete by id
		@DeleteMapping("/Sec_type/{id}")
		public ResponseEntity<?> deleteOne(@PathVariable int id){
			Sec_type type = typeRepo.findById(id)
					.orElseThrow(()-> new ResourceNotFoundException("type not found !!"+ id));

			typeRepo.delete(type);	
			return new ResponseEntity<>(HttpStatus.OK);
			}
		
//		DROPDOWN
		@GetMapping("/DD_type")
		public ResponseEntity<?> getDD(){
			List<Sec_type> sc = typeRepo.findAll();
			ArrayList<Dd_type> dd = new ArrayList<>();
			for(Sec_type s :sc) {
				Dd_type d = new Dd_type();
				d.setId(s.getId());
				d.setName(s.getName());
				dd.add(d);
			}
			
		return 	new ResponseEntity<>(dd, HttpStatus.OK);
		}

}
