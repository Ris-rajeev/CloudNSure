package com.realnet.listbuilder.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.BackendConfig.Entity.BackendConfig_t;
import com.realnet.BackendConfig.Repository.BackendConfig_Repository;
import com.realnet.listbuilder.Entity.Lb_Header;
import com.realnet.listbuilder.Entity.Lb_Line;
import com.realnet.listbuilder.Repository.Lb_HeaderRepository;
import com.realnet.listbuilder.Repository.Lb_lineRepository;
import com.realnet.wfb.entity.Rn_Fb_Header;

@Service
public class Lb_HeaderService {

	@Autowired
	private Lb_HeaderRepository headerRepository;

	@Autowired
	private Lb_lineRepository lineRepository;

	@Autowired
	private BackendConfig_Repository backendConfig_Repository;

	public Lb_Header Savedata(Lb_Header Lb_Header) {

		BackendConfig_t backendConfig_t = backendConfig_Repository.findById(Lb_Header.getBackend_id()).get();

		Lb_Header.setTech_Stack(backendConfig_t.getTechstack());
		Lb_Header.setObject_type("form");
		Lb_Header.setSub_object_type("only header");
		return headerRepository.save(Lb_Header);
	}

	public List<Lb_Header> getdetails() {
		// TODO Auto-generated method stub
		return (List<Lb_Header>) headerRepository.findAll();
	}

	public Lb_Header getdetailsbyId(int id) {
		// TODO Auto-generated method stub
		return headerRepository.findById(id);
	}

	public void delete_by_id(int id) {
		// TODO Auto-generated method stub
		headerRepository.deleteById(id);
	}

	public Lb_Header update_Lb_header(Lb_Header Lb_Header) {

		BackendConfig_t backendConfig_t = backendConfig_Repository.findById(Lb_Header.getBackend_id()).get();

		Lb_Header.setTech_Stack(backendConfig_t.getTechstack());
		Lb_Header.setObject_type("form");
		Lb_Header.setSub_object_type("only header");
		return headerRepository.save(Lb_Header);
	}

	public Lb_Line update_Lb_Line(Lb_Line Lb_Line) {
		return lineRepository.save(Lb_Line);
	}

	public List<Lb_Header> get_by_module_id(int module_id) {
		// TODO Auto-generated method stub
		return (List<Lb_Header>) headerRepository.findbylbdmodule(module_id);
	}

	public List<Lb_Line> get_all_lines() {
		// TODO Auto-generated method stub
		return (List<Lb_Line>) lineRepository.findAll();
	}

	public Lb_Line update_Lb_Lineby_id(int id, Lb_Line Lb_Line) {

		Lb_Line oldline = lineRepository.findById(id);
//				.orElseThrow(() -> new ResourceNotFoundException(Constant.NOT_FOUND_EXCEPTION + " :" + id));

		oldline.setAccountId(Lb_Line.getAccountId());
		oldline.setHeader_id(Lb_Line.getHeader_id());
		oldline.setModel(Lb_Line.getModel());
		final Lb_Line newline = lineRepository.save(oldline);
		return newline;
	}

}
