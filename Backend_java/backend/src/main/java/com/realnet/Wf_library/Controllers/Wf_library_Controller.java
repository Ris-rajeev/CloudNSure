package com.realnet.Wf_library.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.realnet.Wf_library.Entity.Wf_library_t;
import com.realnet.Wf_library.Services.Wf_library_Service;

@RequestMapping(value = "/Wf_library")
@RestController
public class Wf_library_Controller {

	@Autowired
	private Wf_library_Service Service;

	@PostMapping("/Wf_library")
	public Wf_library_t Savedata(@RequestBody Wf_library_t data) {
		Wf_library_t save = Service.Savedata(data);
		return save;
	}

	@GetMapping("/Wf_library")
	public List<Wf_library_t> getdetails() {
		List<Wf_library_t> get = Service.getdetails();
		return get;
	}

	@GetMapping("/Wf_library/{id}")
	public Wf_library_t getdetailsbyId(@PathVariable Long id) {
		Wf_library_t get = Service.getdetailsbyId(id);
		return get;

	}

	@DeleteMapping("/Wf_library/{id}")
	public void delete_by_id(@PathVariable Long id) {
		Service.delete_by_id(id);

	}

	@PutMapping("/Wf_library/{id}")
	public Wf_library_t update(@RequestBody Wf_library_t data, @PathVariable Long id) {
		Wf_library_t update = Service.update(data, id);
		return update;
	}
}