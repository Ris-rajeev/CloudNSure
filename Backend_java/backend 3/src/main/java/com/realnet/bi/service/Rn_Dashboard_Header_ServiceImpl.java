package com.realnet.bi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.bi.entity.Rn_Dashboard_Header;
import com.realnet.bi.entity.Rn_Dashboard_Line;
import com.realnet.bi.repository.Rn_Dashboard_Header_Repository;
import com.realnet.bi.repository.Rn_Dashboard_Line_Repository;
import com.realnet.exceptions.ResourceNotFoundException;
import com.realnet.fnd.entity.Rn_Forms_Setup;

@Service
public class Rn_Dashboard_Header_ServiceImpl implements Rn_Dashboard_Header_Service {
	
	@Autowired
	private Rn_Dashboard_Header_Repository dashRepo;
	
	@Autowired
	private Rn_Dashboard_Line_Repository lineDashRepo;
	
	
	@Override
	public List<Rn_Dashboard_Header> getByModule(int id) {
		List<Rn_Dashboard_Header> headerList=dashRepo.getByModule(id);
		return headerList;
	}
	
	@Override
	public List<Rn_Dashboard_Line> getWidgets(int id) {
		List<Rn_Dashboard_Line> headerList=lineDashRepo.getWidgets(id);
		return headerList;
	}

	@Override
	public Rn_Dashboard_Header save(Rn_Dashboard_Header rn_forms_setup) {
		Rn_Dashboard_Header saveddash = dashRepo.save(rn_forms_setup);
		return saveddash;
	}
	
	@Override
	public Rn_Dashboard_Header getById(int id) {
		Rn_Dashboard_Header rn_forms_setup = dashRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rn_Forms_Setup not found :: " + id));
		return rn_forms_setup;
	}
	
	@Override
	public Rn_Dashboard_Line getLineId(int id) {
		Rn_Dashboard_Line rn_forms_setup = lineDashRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rn_Forms_Setup not found :: " + id));
		return rn_forms_setup;
	}
	
	
	@Override
	public Rn_Dashboard_Line updateById(int id, Rn_Dashboard_Line rn_forms_setupRequest) {
		Rn_Dashboard_Line old_rn_forms_setup = lineDashRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rn_Forms_Setup not found :: " + id));
		old_rn_forms_setup.setWidgets1(rn_forms_setupRequest.getWidgets1());
		
		old_rn_forms_setup.setWidgets2(rn_forms_setupRequest.getWidgets2());
		old_rn_forms_setup.setWidgets3(rn_forms_setupRequest.getWidgets3());
		old_rn_forms_setup.setWidgets4(rn_forms_setupRequest.getWidgets4());
		old_rn_forms_setup.setWidgets5(rn_forms_setupRequest.getWidgets5());
		old_rn_forms_setup.setWidgets6(rn_forms_setupRequest.getWidgets6());
		
		final Rn_Dashboard_Line updated_rn_forms_setup = lineDashRepo.save(old_rn_forms_setup);
		return updated_rn_forms_setup;
	}
	
	@Override
	public Rn_Dashboard_Header updateDashHeader(int id, Rn_Dashboard_Header rnHeader) {
		Rn_Dashboard_Header old_rn_forms_setup = dashRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rn_Forms_Setup not found :: " + id));
		//old_rn_forms_setup.setWidgets1(rn_forms_setupRequest.getWidgets1());
		old_rn_forms_setup.setRouting_add(rnHeader.getRouting_add());
		final Rn_Dashboard_Header updated_rn_forms_setup = dashRepo.save(old_rn_forms_setup);
		return updated_rn_forms_setup;
	}
	
	
}
