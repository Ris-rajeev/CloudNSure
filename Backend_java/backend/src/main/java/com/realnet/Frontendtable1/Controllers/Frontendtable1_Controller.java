package com.realnet.Frontendtable1.Controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.*;
import com.realnet.Frontendtable1.Entity.Frontendtable1_t;
import com.realnet.Frontendtable1.Services.Frontendtable1_Service;
@RequestMapping(value = "/Frontendtable1")
@RestController
public class Frontendtable1_Controller {
	
	@Autowired
	private Frontendtable1_Service Service;

	@PostMapping("/Frontendtable1")
	  public Frontendtable1_t Savedata(@RequestBody Frontendtable1_t data) {
		Frontendtable1_t save = Service.Savedata(data)	;
		 return save;
	  }
		 	
	@GetMapping("/Frontendtable1")
	public List<Frontendtable1_t> getdetails() {
		 List<Frontendtable1_t> get = Service.getdetails();		
		return get;
}

@GetMapping("/Frontendtable1/{id}")
	public  Frontendtable1_t  getdetailsbyId(@PathVariable Long id ) {
		Frontendtable1_t  get = Service.getdetailsbyId(id);
		return get;

	}
@DeleteMapping("/Frontendtable1/{id}")
	public  void delete_by_id(@PathVariable Long id ) {
	Service.delete_by_id(id);
		
	}

@PutMapping("/Frontendtable1/{id}")
	public  Frontendtable1_t update(@RequestBody Frontendtable1_t data,@PathVariable Long id ) {
		Frontendtable1_t update = Service.update(data,id);
		return update;
	}
}