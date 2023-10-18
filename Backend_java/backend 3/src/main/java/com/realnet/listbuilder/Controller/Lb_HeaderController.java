package com.realnet.listbuilder.Controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.fnd.repository.Rn_ProjectSetup_Repository;
import com.realnet.listbuilder.Entity.Lb_Header;
import com.realnet.listbuilder.Entity.Lb_Line;
import com.realnet.listbuilder.Repository.Lb_HeaderRepository;
import com.realnet.listbuilder.Service.Lb_HeaderService;
import com.realnet.wfb.entity.Rn_Fb_Header;
import com.realnet.wfb.repository.Rn_Fb_Header_Repository;

@RestController
@RequestMapping("/listbuilder/lb")
public class Lb_HeaderController {

	@Autowired
	private Lb_HeaderService headerService;

	@Autowired
	private Lb_HeaderRepository lb_headerRepository;

	@Autowired
	private Rn_ProjectSetup_Repository projectSetup_Repository;

	@Autowired
	private Rn_Fb_Header_Repository fb_header_Repository;

	@PostMapping("/Savedata")

	public Lb_Header Savedata(@RequestBody Lb_Header Lb_Header) {
		Lb_Header dash = headerService.Savedata(Lb_Header);
		return dash;
	}

	@GetMapping("/get_lb_header")
	public List<Lb_Header> getdetails() {
		List<Lb_Header> dash = headerService.getdetails();
		return dash;
	}

	@GetMapping("/get_all_lines")
	public List<Lb_Line> get_all_lines() {
		List<Lb_Line> dash = headerService.get_all_lines();
		return dash;
	}

	@GetMapping("/get_module_id")
	public List<Lb_Header> get_by_module_id(@RequestParam(value = "module_id") int module_id) {

		List<Lb_Header> module = headerService.get_by_module_id(module_id);
		return module;

	}

	@GetMapping("/get_lb_headerbyid/{id}")
	public Lb_Header getdetailsbyId(@PathVariable int id) {
		Lb_Header dash = headerService.getdetailsbyId(id);
		return dash;
	}

	@PutMapping("/update_lb_header")
	public Lb_Header update_dashboard_header(@RequestBody Lb_Header Lb_Header) {
		Lb_Header dash = headerService.update_Lb_header(Lb_Header);
		return dash;
	}

//	update lb line by id

	@PutMapping("/update_Lb_Lineby_id/{id}")
	public Lb_Line update_Lb_Lineby_id(@PathVariable int id, @RequestBody Lb_Line Lb_Line) {

		Lb_Line dash = headerService.update_Lb_Lineby_id(id, Lb_Line);
		return dash;

	}

	@PostMapping("/update_Lb_Line")
	public Lb_Line update_Lb_Line(@RequestBody Lb_Line Lb_Line) {
		Lb_Line dash1 = headerService.update_Lb_Line(Lb_Line);
		return dash1;
	}

	@DeleteMapping("/delete_by_header_id/{id}")
	public void delete_by_id(@PathVariable int id) {
		headerService.delete_by_id(id);

	}

	// COUNT OF LIST BUILDER
	@GetMapping("/get_listbuilder/{module_id}")
	public ResponseEntity<?> getREPORT(@PathVariable Integer module_id) {
		String count_wireframe = lb_headerRepository.count_lbheader(module_id);

		if (count_wireframe.isEmpty()) {
			return new ResponseEntity<>(0, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(count_wireframe, HttpStatus.OK);

		}
	}

//	GET NUMBERS OF IDLIST

	@GetMapping("/getobject")
	public List<Object> getobject() {
		return this.lb_headerRepository.findCount();

	}

//	GET ALL LIST NAME BY PROJECT ID

	@GetMapping(value = "/getall_list/{proj_id}")
	public ResponseEntity<?> getalllistby_projId(@PathVariable Integer proj_id) throws IOException {

		List<String> list = new ArrayList<>();

		projectSetup_Repository.findById(proj_id).get().getModules().forEach(
				mid -> lb_headerRepository.findbylbdmodule(mid.getId()).forEach(li -> list.add(li.getLb_name())));

//		List<Rn_Module_Setup> modules = project.get().getModules();
//
//		modules.forEach(mid -> headerRepository.findbylbdmodule(mid.getId()).forEach(li -> list.add(li.getLb_name())));

		return new ResponseEntity<>(list, HttpStatus.OK);

	}

//	GET ALL LIST NAME BY WIREFRAME ID

	@GetMapping(value = "/wireframe/all_list/{wfId}")
	public ResponseEntity<?> getalllist_bywfId(@PathVariable Integer wfId) throws IOException {

		List<String> list = new ArrayList<>();

		Rn_Fb_Header fb_Header = fb_header_Repository.findById(wfId).get();
		Long backend_id = fb_Header.getBackend_id();

		lb_headerRepository.findbyBackendId(backend_id).forEach(lb -> list.add(lb.getLb_name()));

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
//	GET ALL COLUMN NAME BY MODULE ID ID AND LIST NAME

	@GetMapping(value = "/getall_cols/{module_id}/{listname}")
	public ResponseEntity<?> getallcolsbylist(@PathVariable int module_id, @PathVariable String listname)
			throws IOException {

		List<String> columnlist = new ArrayList<>();
		columnlist.add("id");

		JsonParser parser = new JsonParser();

		Lb_Header header = lb_headerRepository.getbylistname(module_id, listname);
		List<Lb_Line> lb_Line = header.getLb_Line();
		for (Lb_Line line : lb_Line) {
			String model = line.getModel();
			JsonElement model_element = parser.parse(model);
			if (!model_element.isJsonNull()) {

				JsonArray models = model_element.getAsJsonArray();

				for (JsonElement mod : models) {
					JsonObject modobject = mod.getAsJsonObject();

					JsonElement SelectedColumn = modobject.get("SelectedColumn");
					JsonArray array = SelectedColumn.getAsJsonArray();

					for (JsonElement ar : array) {
						String col = ar.getAsString();
						columnlist.add(col);
						System.out.println(col);

					}

				}
			} else {
				return new ResponseEntity<>("no json found", HttpStatus.BAD_REQUEST);

			}
		}

		return new ResponseEntity<>(columnlist, HttpStatus.OK);
	}

}
