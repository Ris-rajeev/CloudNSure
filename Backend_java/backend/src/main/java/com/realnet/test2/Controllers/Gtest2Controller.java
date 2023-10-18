package com.realnet.test2.Controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.*;
import com.realnet.test2.Entity.Gtest2;
import com.realnet.test2.Services.Gtest2Service ;
@RequestMapping(value = "/Gtest2")
 @CrossOrigin("*") 
@RestController
public class Gtest2Controller {
	@Autowired
	private Gtest2Service Service;

@Value("${projectPath}")
	private String projectPath;

	public final String UPLOAD_DIREC = "/Files"; 
	@PostMapping("/Gtest2")
		  public Gtest2 Savedata(@RequestBody Gtest2 data) {
		Gtest2 save = Service.Savedata(data)	;
			System.out.println(save);
	 return save;
	  }
@PutMapping("/Gtest2/{id}")
	public  Gtest2 update(@RequestBody Gtest2 data,@PathVariable Integer id ) {
		Gtest2 update = Service.update(data,id);
		return update;
	}	 
	
	@GetMapping("/Gtest2")
	public List<Gtest2> getdetails() {
		 List<Gtest2> get = Service.getdetails();		
		return get;
}
@GetMapping("/Gtest2/{id}")
	public  Gtest2  getdetailsbyId(@PathVariable Integer id ) {
		Gtest2  get = Service.getdetailsbyId(id);
		return get;
	}
@DeleteMapping("/Gtest2/{id}")
	public  void delete_by_id(@PathVariable Integer id ) {
	Service.delete_by_id(id);
		
	}

}