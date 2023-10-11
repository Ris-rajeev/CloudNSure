package com.realnet.formio.controller;


import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.exceptions.ResourceNotFoundException;
import com.realnet.formio.Services.BoardService;
import com.realnet.formio.Services.CardService;
import com.realnet.formio.entity.Boardmicro;
import com.realnet.formio.entity.Boardmix;
import com.realnet.formio.entity.Card;
import com.realnet.formio.entity.Column;
import com.realnet.formio.repository.BoardRepository;
import com.realnet.formio.repository.CardRepository;

import lombok.Data;

@RestController
public class NewController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardRepository boardRepository;
	 
	@Autowired
	private CardService cardService;
	
	@Autowired
	private CardRepository cardRepository;
	
	
	  
//    GET ALL DATA FROM BOARD, COLUMN AND CARD FILTER BY B_ID AND ASSIGNED_TO
    @GetMapping("/ownedby")
    public ResponseEntity<?> get(@RequestParam(value = "b_id") Long b_id,
            @RequestParam(value = "assigned_to") Long assigned_to) {
    	
    	List<Boardmix> s = boardRepository.findAssigned(b_id,assigned_to);
    	
    	 if (s == null) {
				throw new ResourceNotFoundException("fav not found with id " + b_id+assigned_to);
			}
			return ResponseEntity.ok().body(s);
		}    
    
    
    
    
    
    
//  GET ALL DATA FROM BOARD, COLUMN AND CARD FILTER BY B_ID AND REQUESTED_BY
    @GetMapping("/requestedby")
    public ResponseEntity<List<Boardmix>> getrequestedby(@RequestParam(value = "b_id") Long b_id,
            @RequestParam(value = "requested_by") Long requested_by) {
    	
    	List<Boardmix> s = boardRepository.findrequested_by(b_id,requested_by);
    	 if (s == null) {
				return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
			}
			return ResponseEntity.ok().body(s);
		}    
    
	
//	 GET ALL CARD DATA FILTER BY ONLY ASSIGNED_TO
	
	@GetMapping("/get_all_owned_by")
	public List<Card> get_all_owned_by(
			                            @RequestParam(value = "assigned_to") Long assigned_to){
		
		
		return this.cardService.get_all_owned_by(assigned_to);
	}
	
//	 GET ALL CARD DATA FILTER BY ONLY REQUESTED_BY
	
	@GetMapping("/get_all_requestedby")
	public List<Card> get_all_requestedby(
			                            @RequestParam(value = "requested_by") Long requested_by){
		return this.cardService.get_all_requestedby(requested_by);
	}
	
	
//	GET ALL DATA FILTER BY C_NAME
	@GetMapping("/ByCname")
    public ResponseEntity<?> getbycname(
    		@RequestParam(value = "b_id") Long b_id) 
	{
    	
    	List<Boardmix> s = boardRepository.findByC_name(b_id);
    	
			return new ResponseEntity<>(s,HttpStatus.OK);
		}    
	
	
//	GET BY UPDATED AT
	@GetMapping("/Byupdatedat")
    public ResponseEntity<?> getbyupdatedat(@RequestParam(value = "b_id") Long b_id,
    		@RequestParam(value = "date") String date) {
    	
    	List<Boardmix> s = boardRepository.findByDate(b_id,date);
    	 if (s == null) {
				throw new ResourceNotFoundException("fav not found with id " + b_id);
			}
			return ResponseEntity.ok().body(s);
		}    
	
//	GET DATA BY DATE
	@GetMapping("/fetch")
	public ResponseEntity<?> getData_between(
//			@PathVariable(value = "one_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate, 
//			@PathVariable(value = "two_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
	     List<Boardmix> d = cardRepository.findByDate_createdBetween(startDate,endDate);
	     if (d == null) {
				throw new ResourceNotFoundException("fav not found with id " + endDate);
			}
			return ResponseEntity.ok().body(d);
		}
		

	@GetMapping("/fetch11")
	public ResponseEntity<?> getcurrentweek(){
		List<Card> f = cardRepository.findAll();
		LocalDate today = LocalDate.now();
		

		
		
//		for(Boardmicro b: d) {
//			Calendar c = GregorianCalendar.getInstance();
//			Card card= new Card();
//			b.setAssigned_to(card.getAssigned_to());
//			b.setAttachmentDoD(card.getAttachmentDoD());
//			b.setDate_created(card.getDate_created());
////			b.setDate(card.getDate());
//			d.add(b);
//			
		return null;

		}
		
	
	
	
	
}
