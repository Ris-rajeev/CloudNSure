package com.realnet.tour.Controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.realnet.tour.Entity.Roadmap;
import com.realnet.tour.Repos.RoadmapRepo;

@RestController
@RequestMapping("/tourRoad")
public class RoadMapController {
	
	@Autowired
	private RoadmapRepo roadmapRepo;
//	CREATE DATA 
	@PostMapping("/roadmap")
	public Roadmap create(@RequestBody Roadmap roadmap){
		Roadmap sql = roadmapRepo.save(roadmap);
		return sql;
	}
//	UPDATE BY ID 
	@PutMapping("/roadmap/{id}")
	public Roadmap update(
			@PathVariable int id, 
			@RequestBody Roadmap ro){
		Roadmap newsq = roadmapRepo.findById(id).orElseThrow(null);
		newsq.setSeries(ro.getSeries());
		newsq.setTitle(ro.getTitle());
		newsq.setXAxis(ro.getXAxis());
		
		Roadmap sql = roadmapRepo.save(newsq);
		return sql;
		
	}
//	GET BY ID 
	@GetMapping("/roadmap/{id}")
	public Roadmap getbyid(	@PathVariable int id){
		Roadmap newsq = roadmapRepo.findById(id).orElseThrow(null);
		return newsq;
	}
//	GET ALL 
	@GetMapping("/roadmap")
	public ResponseEntity<?> getall(){
		 List<Roadmap> sql = roadmapRepo.findAll();
		 return new ResponseEntity<>(sql,HttpStatus.OK);
	}
//	DELETE BY ID 
	@DeleteMapping("/roadmap/{id}")
	public void delete(	@PathVariable int id){
		Roadmap newsq = roadmapRepo.findById(id).orElseThrow(null);
		roadmapRepo.delete(newsq);
		
	}

}
