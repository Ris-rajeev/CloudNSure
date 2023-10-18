package com.realnet.SuperUser.Controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.*;
import com.realnet.SuperUser.Entity.SuperUser_t;
import com.realnet.SuperUser.Services.SuperUser_Service;
@RequestMapping(value = "/SuperUser")
@RestController
public class SuperUser_Controller {
	
	@Autowired
	private SuperUser_Service Service;

	@PostMapping("/SuperUser")
	  public SuperUser_t Savedata(@RequestBody SuperUser_t data) {
		SuperUser_t save = Service.Savedata(data)	;
		 return save;
	  }
		 	
	@GetMapping("/SuperUser")
	public List<SuperUser_t> getdetails() {
		 List<SuperUser_t> get = Service.getdetails();		
		return get;
}

@GetMapping("/SuperUser/{id}")
	public  SuperUser_t  getdetailsbyId(@PathVariable Long id ) {
		SuperUser_t  get = Service.getdetailsbyId(id);
		return get;

	}
@DeleteMapping("/SuperUser/{id}")
	public  void delete_by_id(@PathVariable Long id ) {
	Service.delete_by_id(id);
		
	}

@PutMapping("/SuperUser/{id}")
	public  SuperUser_t update(@RequestBody SuperUser_t data,@PathVariable Long id ) {
		SuperUser_t update = Service.update(data,id);
		return update;
	}
}