package com.realnet.formio.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.formio.Services.BoardService;
import com.realnet.formio.Services.CardService;
import com.realnet.formio.entity.Card;
import com.realnet.formio.repository.BoardRepository;
import com.realnet.formio.repository.CardRepository;
@RequestMapping("/special")
@RestController
public class SpecialController {

	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardRepository boardRepository;
	 
	@Autowired
	private CardService cardService;
	
	@Autowired
	private CardRepository cardRepository;
	
	

//	 GET ALL CARD DATA FILTER BY ONLY ASSIGNED_TO
	
	@GetMapping("/Owned_by_me")
	public List<Card> get_all_owned_by( @RequestParam(value = "assigned_to") Long assigned_to){
			
		return this.cardService.get_all_owned_by(assigned_to);
	}  
	
//  GET ALL DATA FROM BOARD, COLUMN AND CARD FILTER BY B_ID AND ASSIGNED_TO
	@GetMapping("/get_all_owned_by")
	public List<Card> GetOwnedBy(@RequestParam(value = "b_id") Long b_id,
          @RequestParam(value = "assigned_to") Long assigned_to){
		
		return this.cardRepository.GetOwnedBy(b_id,assigned_to);
	}
    
	
//	 GET ALL CARD DATA FILTER BY ONLY REQUESTED_BY
	
	@GetMapping("/requestedby")
	public List<Card> get_all_requestedby(
			                            @RequestParam(value = "requested_by") Long requested_by){
		return this.cardService.get_all_requestedby(requested_by);
	}
  
  
//GET ALL DATA FROM BOARD, COLUMN AND CARD FILTER BY B_ID AND REQUESTED_BY
  @GetMapping("/get_all_requestedby")
	public List<Card> GetRequestedBy(@RequestParam(value = "b_id") Long b_id,
        @RequestParam(value = "requested_by") Long requested_by){
		
		return this.cardRepository.GetRequestedBy(b_id,requested_by);
	}   
  
	
	
	
	
//	GET ALL DATA FILTER BY C_NAME
	@GetMapping("/ByCname")
  public ResponseEntity<?> getbycname(
  		@RequestParam(value = "b_id") Long b_id) 
	{
  	
  	List<Card> s = cardRepository.findByC_name(b_id);
  	
			return new ResponseEntity<>(s,HttpStatus.OK);
		}    
	
	
//	GET BY DATE
	@GetMapping("/Bydate")
  public ResponseEntity<?> getbyupdatedat(@RequestParam(value = "b_id") Long b_id,
  		                                 @RequestParam(value = "date") String date) 
	{
  	
  	List<Card> s = cardRepository.findByDate(b_id,date);
  	
			return ResponseEntity.ok().body(s);
		}    
	
//	GET DATA BY DATE BETWEEN STARD AND END DATE FILTER BY BID
	@GetMapping("/BetweenDate")
	public ResponseEntity<?> getData_between(
//			@RequestParam(value = "b_id") Long b_id,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

				List<Card> d = cardRepository.findByate_created(startDate,endDate);

			return new ResponseEntity<>(d,HttpStatus.OK);
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
