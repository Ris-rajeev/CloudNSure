package com.realnet.Wf_library.Services;

import com.realnet.Wf_library.Repository.Wf_library_Repository;
import com.realnet.formdrag.entity.Rn_wf_lines_3;
import com.realnet.wfb.entity.Rn_Fb_Header;
import com.realnet.Wf_library.Entity.Wf_library_t;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Wf_library_Service {
	@Autowired
	private Wf_library_Repository Repository;

	public Wf_library_t Savedata(Wf_library_t data) {
		return Repository.save(data);
	}

	public List<Wf_library_t> getdetails() {
		return (List<Wf_library_t>) Repository.getall();
	}

	public Wf_library_t getdetailsbyId(Long id) {
		return Repository.findById(id).get();
	}

	public void delete_by_id(Long id) {
		Repository.deleteById(id);
	}

	public Wf_library_t update(Wf_library_t oldHeader, Long id) {
		Wf_library_t newHeader = Repository.findById(id).get();

		newHeader.setTechStack(oldHeader.getTechStack());
		newHeader.setObjectType(oldHeader.getObjectType());
		newHeader.setSubObjectType(oldHeader.getSubObjectType());
		newHeader.setUiName(oldHeader.getUiName());
		newHeader.setFormType(oldHeader.getFormType());
		newHeader.setFormCode(oldHeader.getFormCode());
		newHeader.setTableName(oldHeader.getTableName());
		newHeader.setLineTableName(oldHeader.getLineTableName());
		newHeader.setMultilineTableName(oldHeader.getMultilineTableName());
		newHeader.setJspName(oldHeader.getJspName());
		newHeader.setControllerName(oldHeader.getControllerName());
		newHeader.setServiceName(oldHeader.getServiceName());
		newHeader.setServiceImplName(oldHeader.getServiceImplName());
		newHeader.setDaoName(oldHeader.getDaoName());
		newHeader.setDaoImplName(oldHeader.getDaoImplName());
		newHeader.setBuild(false);
		newHeader.setUpdated(oldHeader.isUpdated());
		newHeader.setMenuName(oldHeader.getMenuName());
		newHeader.setHeaderName(oldHeader.getHeaderName());
		newHeader.setConvertedTableName(oldHeader.getControllerName());
		newHeader.setTesting(true);

		newHeader.setModel(oldHeader.getModel());

		final Wf_library_t test = Repository.save(newHeader);
		return test;
	}
}