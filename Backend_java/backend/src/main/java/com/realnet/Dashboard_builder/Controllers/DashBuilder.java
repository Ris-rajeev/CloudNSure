package com.realnet.Dashboard_builder.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.fnd.entity.Rn_Project_Setup;
import com.realnet.fnd.repository.Rn_ProjectSetup_Repository;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;

@RequestMapping(value = "/Dashboard_builder/dashbuild")
@RestController
public class DashBuilder {
	@Autowired
	private AppUserServiceImpl userService;

	@Autowired
	private Rn_ProjectSetup_Repository projectSetupRepository;

	@GetMapping(value = "/dashbuild/{job_type}")
	public ResponseEntity<?> dashbuilder(@PathVariable String job_type) {
		AppUser loggedInUser = userService.getLoggedInUser();
		String myproject = projectSetupRepository.countmyproject(loggedInUser.getUserId()).toString();
		String sharewithme = "10";
		String json = "{ \"name\": \"Project B\", \"progress\": 100 },";
		ArrayList<Object> list = new ArrayList<>();
		
		if (job_type.contains("line chart")) {
			list.add("{\n"
					+ "      \"chartData\": [\n"
					+ "        { \"data\": [85, 72, 78, 75, 77, 75, 43,85, 72, 78, 75, 77, 75, 43], \"label\": \"Test Projects\" }\n"
					+ "      ],\n"
					+ "      \"chartLabels\": [\"Jan\", \"Feb\", \"March\", \"April\", \"May\", \"June\",\"july\",\"Jan\", \"Feb\", \"March\", \"April\", \"May\", \"June\",\"july\"]\n"
					+ "    }");

		}
		else if (job_type.contains("doughnut chart")) {
			list.add("{\n"
					+ "      \"chartData\": [\n"
					+ "        { \"data\": [85, 72, 78, 75, 77, 75, 43,85, 72, 78, 75, 77, 75, 43], \"label\": \"Test Projects\" }\n"
					+ "      ],\n"
					+ "      \"chartLabels\": [\"Jan\", \"Feb\", \"March\", \"April\", \"May\", \"June\",\"july\",\"Jan\", \"Feb\", \"March\", \"April\", \"May\", \"June\",\"july\"]\n"
					+ "    }");

		}
		else if (job_type.contains("status")) {
			list.add("{ \"name\": \"" + myproject + "\", \"" + sharewithme + "\": 100 },");

		}

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
