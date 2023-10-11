package com.realnet.Check.Controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.*;
import com.realnet.Check.Entity.Check_t;
import com.realnet.Check.Services.Check_Service;
@RequestMapping(value = "/Check")
@RestController
public class Check_Controller {
	
	@Autowired
	private Check_Service Service;

	@PostMapping("/Check")
	  public Check_t Savedata(@RequestBody Check_t data) {
		Check_t save = Service.Savedata(data)	;
		 return save;
	  }
		 	
	@GetMapping("/Check")
	public List<Check_t> getdetails() {
		 List<Check_t> get = Service.getdetails();		
		return get;
}

@GetMapping("/Check/{id}")
	public  Check_t  getdetailsbyId(@PathVariable Long id ) {
		Check_t  get = Service.getdetailsbyId(id);
		return get;

	}
@DeleteMapping("/Check/{id}")
	public  void delete_by_id(@PathVariable Long id ) {
	Service.delete_by_id(id);
		
	}

@PutMapping("/Check/{id}")
	public  Check_t update(@RequestBody Check_t data,@PathVariable Long id ) {
		Check_t update = Service.update(data,id);
		return update;
	}
}