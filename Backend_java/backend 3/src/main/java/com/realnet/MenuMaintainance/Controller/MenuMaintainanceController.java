package com.realnet.MenuMaintainance.Controller;

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

import com.realnet.MenuMaintainance.Entity.Rn_Menu_maintainance;
import com.realnet.MenuMaintainance.Repository.MenuRepository;
import com.realnet.SureConnect.Entities.Sure_Connect;
import com.realnet.SureConnect.Service.SureService;
import com.realnet.exceptions.ResourceNotFoundException;

@RestController
public class MenuMaintainanceController {
	
	@Autowired
	private MenuRepository menuRepository;
	
// CREATE DATA
	@PostMapping("/Menu_maintain")
	public ResponseEntity<?> add(@RequestBody Rn_Menu_maintainance rn_Menu_maintainance){
		Rn_Menu_maintainance menu= menuRepository.save(rn_Menu_maintainance);
			
		return new ResponseEntity<>(menu, HttpStatus.OK);
		
	}
	
//	update data with file
	@PutMapping("/Menu_maintain/{id}")
	public ResponseEntity<?> update(@RequestBody Rn_Menu_maintainance rn,
			                         @PathVariable Integer id){
			Rn_Menu_maintainance menu = menuRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("menu not found"));

			menu.setMenu_item_name(rn.getMenu_item_name());
			menu.setModule_name(rn.getModule_name());
			menu.setOriginal_menu(rn.getOriginal_menu());
			menu.setSequence(rn.getSequence());
			menu.setStatus(rn.getStatus());
			Rn_Menu_maintainance save = menuRepository.save(menu);
	
		return new ResponseEntity<>(save, HttpStatus.OK);
	}
	

	
	
//	Get all
	@GetMapping("/Menu_maintain")
	public ResponseEntity<?> getall(){
		List<Rn_Menu_maintainance> menu = menuRepository.findAll();
	return 	new ResponseEntity<>(menu, HttpStatus.OK);
	}
	

//	get by id
	@GetMapping("/Menu_maintain/{id}")
	public ResponseEntity<?> getbyid(@PathVariable int id){
		Rn_Menu_maintainance menu = menuRepository.findById(id);

		return 	new ResponseEntity<>(menu, HttpStatus.OK);
	}
	
	
	
//	delete by id
	@DeleteMapping("/Menu_maintain/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable int id){

		menuRepository.deleteById(id);	
		return new ResponseEntity<>(HttpStatus.OK);
		}



}
