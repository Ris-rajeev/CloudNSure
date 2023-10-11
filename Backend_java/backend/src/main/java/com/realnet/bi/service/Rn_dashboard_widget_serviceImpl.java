package com.realnet.bi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.bi.entity.Rn_Dashboard_Widgets;
import com.realnet.bi.repository.Rn_dashboard_widget_repository;
import com.realnet.fnd.entity.DropDownDTO;


@Service
public class Rn_dashboard_widget_serviceImpl implements Rn_dashboard_widget_service{

	
	@Autowired
	private Rn_dashboard_widget_repository widRepo;

	@Override
	public List<Rn_Dashboard_Widgets> getByModule(int id) {
		List<Rn_Dashboard_Widgets> bcf_extractor = widRepo.getByModule(id);
		return bcf_extractor;
	}
	
	@Override
	public Rn_Dashboard_Widgets getWidgetsByName(String name) {
		Rn_Dashboard_Widgets bcf_extractor = widRepo.getWidgetsByName(name);
		return bcf_extractor;
	}
	
	@Override
	public Rn_Dashboard_Widgets getWidgetsByid(int  name) {
		Optional<Rn_Dashboard_Widgets> bcf_extractor = widRepo.findById(name);
		Rn_Dashboard_Widgets rn_Dashboard_Widgets = bcf_extractor.get();
		return rn_Dashboard_Widgets;
	}
	
	
	@Override
	public Rn_Dashboard_Widgets save(Rn_Dashboard_Widgets rn_report_builder) {
		Rn_Dashboard_Widgets savereport = widRepo.save(rn_report_builder);
		return savereport;
	}

	@Override
	public Rn_Dashboard_Widgets saveWidget(Rn_Dashboard_Widgets widget, int moduleId) {
		    
//		    User user = userService.getLoggedInUser();
//			Long userId = user.getUserId();
//			Long accountId = user.getSys_account().getId();

			String widget_name = widget.getWidget_name();
			String widget_description=widget.getWidget_description();
			String chart_type=widget.getChart_type();
			String sql_query=widget.getSql_query();
			String label=widget.getLabel();
			String color_scheme=widget.getColor_scheme();
			
			Rn_Dashboard_Widgets dashWidget=new Rn_Dashboard_Widgets();
			dashWidget.setWidget_name(widget_name);
			dashWidget.setChart_type(chart_type);
			dashWidget.setWidget_description(widget_description);
			dashWidget.setSql_query(sql_query);
			dashWidget.setLabel(label);
			dashWidget.setColor_scheme(color_scheme);
			dashWidget.setModule_id(moduleId);
			
			return save(dashWidget); 
		
		
	}
	
	
	@Override
	public List<DropDownDTO> getWidgetsForDropDown(int moduleId) {
		List<Rn_Dashboard_Widgets> moduleList = widRepo.findWidgetsForDropDown(moduleId);

		ArrayList<DropDownDTO> dtos = new ArrayList<DropDownDTO>();
		for (Rn_Dashboard_Widgets module : moduleList) {
			DropDownDTO dto = new DropDownDTO();
			dto.setId(module.getId());
			dto.setName(module.getWidget_name());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public Rn_Dashboard_Widgets updatewidgets(int id,Rn_Dashboard_Widgets name) {
		Optional<Rn_Dashboard_Widgets> bcf_extractor = widRepo.findById(id);
		Rn_Dashboard_Widgets oldwidgets = bcf_extractor.get();
		oldwidgets.setWidget_name(name.getWidget_name());
		oldwidgets.setChart_type(name.getChart_type());
		oldwidgets.setColor_scheme(name.getColor_scheme());
		oldwidgets.setWidget_description(name.getWidget_description());
		oldwidgets.setLabel(name.getLabel());
		oldwidgets.setSql_query(name.getSql_query());
		
		final Rn_Dashboard_Widgets updated=widRepo.save(oldwidgets);
		return updated;
	}
	
	
}
