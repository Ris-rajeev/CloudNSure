package com.realnet.DataTypesWireframe.Controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.*;
import com.realnet.DataTypesWireframe.Entity.DataTypesWireframe_t;
import com.realnet.DataTypesWireframe.Services.DataTypesWireframe_Service;
@RequestMapping(value = "/DataTypesWireframe")
@RestController
public class DataTypesWireframe_Controller {
	
	@Autowired
	private DataTypesWireframe_Service Service;

	@PostMapping("/DataTypesWireframe")
	  public DataTypesWireframe_t Savedata(@RequestBody DataTypesWireframe_t data) {
		DataTypesWireframe_t save = Service.Savedata(data)	;
		 return save;
	  }
		 	
	@GetMapping("/DataTypesWireframe")
	public List<DataTypesWireframe_t> getdetails() {
		 List<DataTypesWireframe_t> get = Service.getdetails();		
		return get;
}

@GetMapping("/DataTypesWireframe/{id}")
	public  DataTypesWireframe_t  getdetailsbyId(@PathVariable Long id ) {
		DataTypesWireframe_t  get = Service.getdetailsbyId(id);
		return get;

	}
@DeleteMapping("/DataTypesWireframe/{id}")
	public  void delete_by_id(@PathVariable Long id ) {
	Service.delete_by_id(id);
		
	}

@PutMapping("/DataTypesWireframe/{id}")
	public  DataTypesWireframe_t update(@RequestBody DataTypesWireframe_t data,@PathVariable Long id ) {
		DataTypesWireframe_t update = Service.update(data,id);
		return update;
	}
}