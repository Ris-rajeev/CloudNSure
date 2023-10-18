package com.realnet.bi.service;

import java.util.List;

import com.realnet.bi.entity.Rn_Dashboard_Widgets;
import com.realnet.fnd.entity.DropDownDTO;
import com.realnet.rb.entity.Rn_report_builder;

public interface Rn_dashboard_widget_service {

	List<Rn_Dashboard_Widgets> getByModule(int id);
	Rn_Dashboard_Widgets getWidgetsByName(String id);
	Rn_Dashboard_Widgets saveWidget(Rn_Dashboard_Widgets widget, int moduleId);
	Rn_Dashboard_Widgets save(Rn_Dashboard_Widgets rn_report_builder);
	List<DropDownDTO> getWidgetsForDropDown(int moduleId);
	public Rn_Dashboard_Widgets getWidgetsByid(int  name);
	Rn_Dashboard_Widgets updatewidgets(int id,Rn_Dashboard_Widgets  name);
}
