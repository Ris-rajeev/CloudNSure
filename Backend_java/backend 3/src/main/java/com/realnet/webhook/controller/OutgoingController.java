package com.realnet.webhook.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.sql.DataSource;
import javax.xml.bind.DatatypeConverter;

import org.json.simple.JSONArray;
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

import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;
import com.realnet.webhook.entity.OutgoingEntity;
import com.realnet.webhook.entity.Outgoing_lines;
import com.realnet.webhook.repository.OutgoingLineRepository;
import com.realnet.webhook.repository.OutgoingRepository;

@RestController
@RequestMapping("/api/outgoing")
public class OutgoingController {

	@Autowired
	private OutgoingRepository repo;

	@Autowired
	private OutgoingLineRepository line_repo;
//	outgoing Webhook genrate url    
	
	@Autowired
	private DataSource ent;
	@Autowired
	private AppUserServiceImpl userService;

	@PostMapping("/save")
	public ResponseEntity<?> outgoing(@RequestBody OutgoingEntity outgoing) {
		System.out.println(outgoing);

		AppUser loggedInUser = userService.getLoggedInUser();
		outgoing.setUser_id(loggedInUser.getUserId());
		OutgoingEntity save = repo.save(outgoing);

		return new ResponseEntity<>(save, HttpStatus.ACCEPTED);

	}
	
	

	@GetMapping("/getall")
	public ResponseEntity<?> getAll() {

		return new ResponseEntity<>(repo.findAll(), HttpStatus.ACCEPTED);

	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {

		return new ResponseEntity<>(repo.findById(id).get(), HttpStatus.ACCEPTED);

	}

	//delete by id for outgoing entity
	@DeleteMapping("/delete/{id}")
	public String deteleIncooming(@PathVariable Long id) {

		repo.deleteById(id);
		return "Succesfully Deleted";
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody OutgoingEntity outgoing_data) {


		OutgoingEntity outgoing = repo.findById(id).get();
		outgoing.setDescription(outgoing_data.getDescription());
		outgoing.setWebhook_name(outgoing_data.getWebhook_name());
		outgoing.setOn_entity(outgoing_data.getOn_entity());
		outgoing.setOn_event(outgoing_data.getOn_event());
		repo.save(outgoing);

		return new ResponseEntity<>(repo.findById(id).get(), HttpStatus.ACCEPTED);

	}
	
//	get All project entity name 
	@GetMapping("/getentity")
	public ResponseEntity<?> getAllEntity() throws SQLException {


		DatabaseMetaData m =ent.getConnection().getMetaData();
		ResultSet tables = m.getTables(null, null, null, new String[] {"TABLE"});
		ArrayList<String> l =new ArrayList<String>();
	
		
		while(tables.next()) {
			String table=tables.getString("TABLE_NAME");
			l.add(table);
			
		}
		return new ResponseEntity<>(l, HttpStatus.ACCEPTED);

	}
	
	private String genratekey() throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		SecretKey secretKey = keyGen.generateKey();
		byte[] encoded = secretKey.getEncoded();

		String key = DatatypeConverter.printHexBinary(encoded).toLowerCase();
		System.out.println(key);
		return key;
	}

}
