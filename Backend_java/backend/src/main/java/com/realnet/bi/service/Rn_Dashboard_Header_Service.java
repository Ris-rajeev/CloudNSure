package com.realnet.bi.service;

import java.util.List;

import com.realnet.bi.entity.Rn_Dashboard_Header;
import com.realnet.bi.entity.Rn_Dashboard_Line;
import com.realnet.fnd.entity.Rn_Forms_Setup;

public interface Rn_Dashboard_Header_Service {
	List<Rn_Dashboard_Header> getByModule(int id);
	List<Rn_Dashboard_Line> getWidgets(int h_id);
	Rn_Dashboard_Header save(Rn_Dashboard_Header rn_dash);
	Rn_Dashboard_Header getById(int id);
	
	Rn_Dashboard_Line getLineId(int id);
	Rn_Dashboard_Line updateById(int id, Rn_Dashboard_Line rn_dash);

	Rn_Dashboard_Header updateDashHeader(int id,Rn_Dashboard_Header rnHeader);
}
