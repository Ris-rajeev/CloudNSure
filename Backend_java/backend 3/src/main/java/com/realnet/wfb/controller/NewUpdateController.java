package com.realnet.wfb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.wfb.entity.Rn_Fb_Header;
import com.realnet.wfb.service.Rn_WireFrame_Service;

@RestController
@RequestMapping("/wfb/newupdate")
public class NewUpdateController {

	@Autowired
	private Rn_WireFrame_Service wireframeService;

	@PutMapping("/headersnew/{id}")
	public ResponseEntity<Rn_Fb_Header> updateHeader(@PathVariable(value = "id") Integer headerId,
			@RequestBody Rn_Fb_Header headerDetails) {
		Rn_Fb_Header updatedHeader = wireframeService.updateById(headerId, headerDetails);
		return ResponseEntity.ok(updatedHeader);
	}

}
