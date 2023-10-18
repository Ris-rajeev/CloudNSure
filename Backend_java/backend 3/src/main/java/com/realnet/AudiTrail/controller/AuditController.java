package com.realnet.AudiTrail.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.Accesstype_back.Entity.Accesstype;
import com.realnet.Accesstype_back.Services.AccesstypeService;
import com.realnet.AudiTrail.Entity.AudiTrail_t;
import com.realnet.AudiTrail.Repos.AudiTrail_Repo;
import com.realnet.AudiTrail.Service.AuditrailService;

@RestController
@RequestMapping("/audit")
public class AuditController {

	@Autowired
	private AuditrailService Service;

	@Autowired
	private AudiTrail_Repo auditrepo;

	// getall
	@GetMapping("/auditall")
	public List<AudiTrail_t> getdetails() {
		List<AudiTrail_t> get = Service.getdetails();
		return get;
	}

	// getby id
	@GetMapping("/auditall/{id}")
	public AudiTrail_t getdetailsbyId(@PathVariable Long id) {
		AudiTrail_t get = Service.getdetailsbyId(id);
		return get;
	}

	// getbyusername
	@GetMapping("/listusername/{user}")
	public List<AudiTrail_t> getbyusername(@PathVariable String user) {
		List<AudiTrail_t> get = auditrepo.findbyuser(user);
		return get;
	}

	// getbyentityname
	@GetMapping("/listentityname/{entity_name}")
	public List<AudiTrail_t> getbyentityname(@PathVariable String entity_name) {
		List<AudiTrail_t> get = auditrepo.findbyentity(entity_name);
		return get;
	}
	// get data between date
		@GetMapping("/betweendate")
		public List<AudiTrail_t> databetweentwodate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
				@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
			List<AudiTrail_t> get = auditrepo.findByDate_createdBetween(startDate, endDate);
			return get;
		}
		
		// get data between date by user and entity name
			@GetMapping("/betweendate_byuser")
			public List<AudiTrail_t> databetweentwodatebyuser(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
					@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, 
					@RequestParam String user,@RequestParam String entity_name) {
				List<AudiTrail_t> get = auditrepo.findByDate_createdBetweenbyuser(user,entity_name, startDate, endDate);
				return get;
			}
}
