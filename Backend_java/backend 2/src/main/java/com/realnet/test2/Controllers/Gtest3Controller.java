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
import com.realnet.test2.Entity.Gtest3;
import com.realnet.test2.Services.Gtest3Service ;
@RequestMapping(value = "/Gtest3")
 @CrossOrigin("*") 
@RestController
public class Gtest3Controller {
	@Autowired
	private Gtest3Service Service;

@Value("${projectPath}")
	private String projectPath;

	public final String UPLOAD_DIREC = "/Files"; 
	@PostMapping("/Gtest3")
		  public Gtest3 Savedata(@RequestBody Gtest3 data) {
		Gtest3 save = Service.Savedata(data)	;
			System.out.println(save);
	 return save;
	  }
@PutMapping("/Gtest3/{id}")
	public  Gtest3 update(@RequestBody Gtest3 data,@PathVariable Integer id ) {
		Gtest3 update = Service.update(data,id);
		return update;
	}	 
	
	@GetMapping("/Gtest3")
	public List<Gtest3> getdetails() {
		 List<Gtest3> get = Service.getdetails();		
		return get;
}
@GetMapping("/Gtest3/{id}")
	public  Gtest3  getdetailsbyId(@PathVariable Integer id ) {
		Gtest3  get = Service.getdetailsbyId(id);
		return get;
	}
@DeleteMapping("/Gtest3/{id}")
	public  void delete_by_id(@PathVariable Integer id ) {
	Service.delete_by_id(id);
		
	}

}