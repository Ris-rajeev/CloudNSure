package com.realnet.Bookmarks.Controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realnet.Bookmarks.Entity.Bookmarks;
import com.realnet.Bookmarks.Services.BookmarksFileuploadService;
import com.realnet.Bookmarks.Services.BookmarksService;

@RequestMapping(value = "/Bookmarks")
@CrossOrigin("*")
@RestController
public class BookmarksController {
	@Autowired
	private BookmarksService Service;

	@Value("${projectPath}")
	private String projectPath;

	public final String UPLOAD_DIREC = "/Files";

	@Autowired
	private BookmarksFileuploadService fileuploadService;

	@PostMapping("/Bookmarks")

	public Bookmarks Savedata(@RequestParam String data, @RequestParam MultipartFile file)
			throws JsonMappingException, JsonProcessingException {

		Bookmarks tdata;

		tdata = new ObjectMapper().readValue(data, Bookmarks.class);
		Date d = new Date();
		String addString = "_" + d.getTime();

		if (!file.isEmpty()) {

			System.out.println(file.getOriginalFilename());

			boolean f = fileuploadService.uploadFile(file,addString);

			if (f) {
				System.out.println("file uploaded successfully");

				tdata.setFileupload_path(projectPath + UPLOAD_DIREC);
				tdata.setFileupload_name(file.getOriginalFilename()+addString);

			}
		} else {
			tdata.setFileupload_name("No file");

		}
		Bookmarks save = Service.Savedata(tdata);

		System.out.println(save);
		return save;
	}

	@PutMapping("/Bookmarks/{id}")
	public Bookmarks update(@RequestBody Bookmarks data, @PathVariable Integer id) {
		Bookmarks update = Service.update(data, id);
		return update;
	}

	@GetMapping("/Bookmarks")
	public List<Bookmarks> getdetails() {
		List<Bookmarks> get = Service.getdetails();
		return get;
	}

	@GetMapping("/Bookmarks/{id}")
	public Bookmarks getdetailsbyId(@PathVariable Integer id) {
		Bookmarks get = Service.getdetailsbyId(id);
		return get;
	}

	@DeleteMapping("/Bookmarks/{id}")
	public void delete_by_id(@PathVariable Integer id) {
		Service.delete_by_id(id);

	}

}