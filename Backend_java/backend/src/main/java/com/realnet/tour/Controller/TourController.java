package com.realnet.tour.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.tour.Entity.Tour;
import com.realnet.tour.Repos.TourRepositor;

@RequestMapping("/Tour")
@RestController
public class TourController {
	@Autowired
	private TourRepositor tourRepositor;
	
	
//	CREATE DATA 
	@PostMapping("/tour")
	public Tour create(@RequestBody Tour tour){
		Tour sql = tourRepositor.save(tour);
		return sql;
	}
//	GET BY ID 
	@GetMapping("/tour/{id}")
	public Tour getbyid(	@PathVariable Integer id){
		Tour newsq = tourRepositor.findById(id).orElseThrow(null);
		return newsq;
	}
//	GET ALL 
	@GetMapping("/tour")
	public ResponseEntity<?> getall(){
		 List<Tour> sql = tourRepositor.findAll();
		 return new ResponseEntity<>(sql,HttpStatus.OK);
	}

}
