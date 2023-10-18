package com.realnet.Working_test.Controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.*;
import com.realnet.Working_test.Entity.Working_test_t;
import com.realnet.Working_test.Services.Working_test_Service;
@RequestMapping(value = "/Working_test")
@RestController
public class Working_test_Controller {
	
	@Autowired
	private Working_test_Service Service;

	@PostMapping("/Working_test")
	  public Working_test_t Savedata(@RequestBody Working_test_t data) {
		Working_test_t save = Service.Savedata(data)	;
		 return save;
	  }
		 	
	@GetMapping("/Working_test")
	public List<Working_test_t> getdetails() {
		 List<Working_test_t> get = Service.getdetails();		
		return get;
}

@GetMapping("/Working_test/{id}")
	public  Working_test_t  getdetailsbyId(@PathVariable Long id ) {
		Working_test_t  get = Service.getdetailsbyId(id);
		return get;

	}
@DeleteMapping("/Working_test/{id}")
	public  void delete_by_id(@PathVariable Long id ) {
	Service.delete_by_id(id);
		
	}

@PutMapping("/Working_test/{id}")
	public  Working_test_t update(@RequestBody Working_test_t data,@PathVariable Long id ) {
		Working_test_t update = Service.update(data,id);
		return update;
	}
}