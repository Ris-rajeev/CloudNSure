package com.realnet.formio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.formio.Services.impl.BoardServiceImpl;
import com.realnet.formio.entity.Board;

@RestController
@RequestMapping("/formio/category")
public class CategoryController {
	
	@Autowired
	private BoardServiceImpl serviceImpl;
	
	
	
	
	@GetMapping("/get_bygoal_byid/{bId}")
	public Board get(@PathVariable Long bId) {
		Board board= serviceImpl.getgoal(bId);
			
		return board;
	}

}
