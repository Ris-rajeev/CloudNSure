package com.realnet.bi.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.bi.entity.Rn_Dashboard_Header;
import com.realnet.bi.entity.Rn_Dashboard_Line;
import com.realnet.bi.entity.Rn_Dashboard_Widgets;
import com.realnet.bi.service.Rn_Dashboard_Header_Service;
import com.realnet.bi.service.Rn_dashboard_widget_service;
import com.realnet.fnd.entity.Error;
import com.realnet.fnd.entity.ErrorPojo;
import com.realnet.fnd.entity.Rn_Forms_Setup;
import com.realnet.fnd.entity.Success;
import com.realnet.fnd.entity.SuccessPojo;
import com.realnet.fnd.response.CustomResponse;
import com.realnet.utils.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Rn_dash_builder_header" })
public class Rn_Dashboard_Header_Controller {
	
	@Autowired
	private Rn_Dashboard_Header_Service dashServices;
	
	@Autowired
	private Rn_dashboard_widget_service widgetService;
	
	@Value("${projectPath}")
	private String projectPath;
	
	@ApiOperation(value = "List of dashboard", response = CustomResponse.class)
	@GetMapping("/dashboard-details")
	public CustomResponse getWireframes(@RequestParam(value = "moduleId") Integer moduleId,
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "20", required = false) Integer size) {
		System.out.println("Calling Controller id:"+moduleId);
		List<Rn_Dashboard_Header> headers = dashServices.getByModule(moduleId);
		Pageable paging = PageRequest.of(page, size, Sort.by(Constant.SORT_BY_CREATION_DATE).descending());
		Page<Rn_Dashboard_Header> result = new PageImpl<>(headers, paging, headers.size());
		CustomResponse resp = new CustomResponse();
		resp.setPageStats(result, true);
		resp.setItems(result.getContent());
		return resp;
	}
	
		// SAVE
		@ApiOperation(value = "Add A New Dash", response = Rn_Dashboard_Header.class)
		@PostMapping("/add-dashboard")
		public ResponseEntity<Rn_Dashboard_Header> createRn_Forms_Setup(@RequestParam(value = "moduleId") Integer moduleId,
				@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
				@Valid @RequestBody Rn_Dashboard_Header rn_dash) {
//			String userId = tokenUtil.getUserId(authToken);
//			rn_forms_setup.setCreatedBy(userId);
			rn_dash.setModule_id(moduleId);
			rn_dash.setRouting_add('N');
			Rn_Dashboard_Header savedDash = dashServices.save(rn_dash);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedDash);
		}
		
		
		// GET BY ID
		@ApiOperation(value = "Get a Dash", response = Rn_Dashboard_Header.class)
		@GetMapping("/dashboard/{id}")
		public ResponseEntity<Rn_Dashboard_Header> getDashById(@PathVariable(value = "id") int id) {
			Rn_Dashboard_Header rn_dash = dashServices.getById(id);
			return ResponseEntity.ok().body(rn_dash);
		}
		
		
		     // GET BY ID
				@ApiOperation(value = "Get a Dash", response = Rn_Dashboard_Header.class)
				@GetMapping("/widget-information/{id}")
				public ResponseEntity<Rn_Dashboard_Line> getLineDetails(@PathVariable(value = "id") int id) {
					Rn_Dashboard_Line rn_dash = dashServices.getLineId(id);
					return ResponseEntity.ok().body(rn_dash);
				}
				
				// GET BY ID
				@ApiOperation(value = "Get a Dash", response = Rn_Dashboard_Header.class)
				@GetMapping("/getwidget/{id}")
				public ResponseEntity<Rn_Dashboard_Widgets> getwidgetDetails(@PathVariable(value = "id") int id) {
					Rn_Dashboard_Widgets widgetsByid = widgetService.getWidgetsByid(id);
					return ResponseEntity.ok().body(widgetsByid);
				}
				
				// update BY ID
				@ApiOperation(value = "Get a Dash", response = Rn_Dashboard_Widgets.class)
				@PutMapping("/updatewidget/{id}")
				public ResponseEntity<Rn_Dashboard_Widgets> getwidgetDetails(@PathVariable(value = "id") int id,
						@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
					 @RequestBody Rn_Dashboard_Widgets widgets) {
					Rn_Dashboard_Widgets widgetsByid = widgetService.updatewidgets(id, widgets);
					return ResponseEntity.ok().body(widgetsByid);
				}
				
				
				// UPDATE
				@ApiOperation(value = "Update A Form", response = Rn_Dashboard_Line.class)
				@PutMapping("/widget-update/{id}")
				public ResponseEntity<Rn_Dashboard_Line> updateDashLine(
						@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
						@PathVariable(value = "id") Integer id, @Valid @RequestBody Rn_Dashboard_Line rn_dash) {
					Rn_Dashboard_Line updatedRn_Forms_Setup = dashServices.updateById(id, rn_dash);
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedRn_Forms_Setup);
				}
				
				
				
				
				@GetMapping(value = "/build_dashboard")
				public ResponseEntity<?> build_dash(@RequestParam("id") Integer id) throws IOException 
				{
					
					StringBuilder tsFile=new StringBuilder();
					StringBuilder htmlFile=new StringBuilder();
					StringBuilder cssFile=new StringBuilder();
					StringBuilder dataFields=new StringBuilder();
					StringBuilder services=new StringBuilder();
					StringBuilder addChart=new StringBuilder();
					Rn_Dashboard_Header rn_dash = dashServices.getById(id);
					String dashboard_name=rn_dash.getDashboard_name();
					char routingCheck=rn_dash.getRouting_add();
					
					List<Rn_Dashboard_Line> line=dashServices.getWidgets(id);
					
					
					
					int count =0;
					for(int i=0;i<line.size();i++) 
					{
						String section_type=line.get(i).getSection_type();
						String widgets1=line.get(i).getWidgets1();
						String widgets2=line.get(i).getWidgets2();
						String widgets3=line.get(i).getWidgets3();
						String widgets4=line.get(i).getWidgets4();
						String widgets5=line.get(i).getWidgets5();
						String widgets6=line.get(i).getWidgets6();
						
						
						Rn_Dashboard_Widgets widgetList1=widgetService.getWidgetsByName(widgets1);
						Rn_Dashboard_Widgets widgetList2=widgetService.getWidgetsByName(widgets2);
						Rn_Dashboard_Widgets widgetList3=widgetService.getWidgetsByName(widgets3);
						Rn_Dashboard_Widgets widgetList4=widgetService.getWidgetsByName(widgets4);
						Rn_Dashboard_Widgets widgetList5=widgetService.getWidgetsByName(widgets5);
						Rn_Dashboard_Widgets widgetList6=widgetService.getWidgetsByName(widgets6);

						
						
						String chart_type1=null;
			        	String chart_type2=null;
			        	String chart_type3=null;
			        	String chart_type4=null;
			        	String chart_type5=null;
			        	String chart_type6=null;
			        	
			        	String widgets_name1=null;
			        	String widgets_name2=null;
			        	String widgets_name3=null;
			        	String widgets_name4=null;
			        	String widgets_name5=null;
			        	String widgets_name6=null;
			        	
			        	
			        	String sqlQuery=null;
			        	String sqlQuery2=null;
			        	String sqlQuery3=null;
			        	String sqlQuery4=null;
			        	String sqlQuery5=null;
			        	String sqlQuery6=null;
			        	String label=null;
						
			        	if(widgetList1!=null){
							 widgets_name1=widgetList1.getWidget_name();
							 chart_type1=widgetList1.getChart_type();

			        	}
			        	if(widgetList2!=null){
							 widgets_name2=widgetList2.getWidget_name();
					         chart_type2=widgetList2.getChart_type();


			        	}
						if(widgetList3!=null){
				        	 widgets_name3=widgetList3.getWidget_name();
					         chart_type3=widgetList3.getChart_type();


			        	}
						if(widgetList4!=null){
				        	 widgets_name4=widgetList4.getWidget_name();
					         chart_type4=widgetList4.getChart_type();


			        	}
						if(widgetList5!=null){
				        	 widgets_name5=widgetList5.getWidget_name();
					         chart_type5=widgetList5.getChart_type();

	        		
						}
						if(widgetList6!=null){
				        	 widgets_name6=widgetList6.getWidget_name();
					         chart_type6=widgetList6.getChart_type();


						}
						count = 1+i;
						
						if(widgetList1!=null)
			        	{ 
							System.out.println("WidgetList 1::"+widgets_name1+" count "+count+" i "+i);
							
				        	sqlQuery=widgetList1.getSql_query();
				        	label=widgetList1.getLabel();
							dataFields.append(widgets_name1+count+"Data : any[] = [];\r\n");
							services.append("\n\nme.orderService.getOrderStats2(\""+sqlQuery+"\").subscribe(function("+widgets_name1+count+"Data2)\r\n"
											+ "         {\r\n"
											+ "            me."+widgets_name1+count+"Data = "+widgets_name1+count+"Data2.items;\r\n"
											+ "        });");
						    if(section_type!=null){
						    	if(section_type.equals("1*1")) {
						    		if(chart_type1.equals("pie-chart")){
						    			addChart.append("\n<div class=\"chart-box\">\r\n"
						    					+ "        <h4> "+label+" </h4>\r\n"
						    					+ "        <ngx-charts-advanced-pie-chart\r\n"
						    					+ "          style=\"position:relative\"\r\n"
						    					+ "          [view]=\"[990, 270]\"\r\n"
						    					+ "          [scheme]=\"colorScheme\"\r\n"
						    					+ "          [results]=\""+widgets_name1+count+"Data\"\r\n"
						    					+ "        >\r\n"
						    					+ "        </ngx-charts-advanced-pie-chart>\r\n"
						    					+ "      </div>");
									}
						    		if(chart_type1.equals("bar-chart")){
						    			addChart.append("\n<div class=\"chart-box\">\r\n"
						    					+ "    <h4> "+label+"</h4>\r\n"
						    					+ "        <ngx-charts-bar-vertical\r\n"
						    					+ "            [view]=\"[990, 270]\"\r\n"
						    					+ "            [barPadding]=\"1\"\r\n"
						    					+ "            [scheme]=\"barColorScheme\"\r\n"
						    					+ "            [results]=\""+widgets_name1+count+"Data\"\r\n"
						    					+ "            [roundDomains]=\"true\"\r\n"
						    					+ "            [showGridLines]=\"true\"\r\n"
						    					+ "            [xAxis]=\"true\"\r\n"
						    					+ "            [yAxis]=\"true\"\r\n"
						    					+ "            [legend]=\"false\"\r\n"
						    					+ "            [roundEdges]=\"false\"\r\n"
						    					+ "        >\r\n"
						    					+ "        </ngx-charts-bar-vertical>\r\n"
						    					+ "  </div>");
									}
						    	}//section 1 if end
								if(section_type.equals("1*2")){
									if(chart_type1.equals("pie-chart")){
										addChart.append("\n<div class=\"chart-box\">\r\n"
												+ "      <h4> "+label+" </h4>\r\n"
												+ "      <ngx-charts-advanced-pie-chart\r\n"
												+ "        style=\"position:relative\"\r\n"
												+ "        [view]=\"view\"\r\n"
												+ "        [scheme]=\"colorScheme\"\r\n"
												+ "        [results]=\""+widgets_name1+count+"Data\"\r\n"
												+ "      >\r\n"
												+ "      </ngx-charts-advanced-pie-chart>\r\n"
												+ "    </div>");
									}
									if(chart_type1.equals("bar-chart")){
										addChart.append("\n<div class=\"chart-box\">\r\n"
												+ "    <h4> "+label+" </h4>\r\n"
												+ "        <ngx-charts-bar-vertical\r\n"
												+ "            [view]=\"view\"\r\n"
												+ "            [barPadding]=\"1\"\r\n"
												+ "            [scheme]=\"barColorScheme\"\r\n"
												+ "            [results]=\""+widgets_name1+count+"Data\"\r\n"
												+ "            [roundDomains]=\"true\"\r\n"
												+ "            [showGridLines]=\"true\"\r\n"
												+ "            [xAxis]=\"true\"\r\n"
												+ "            [yAxis]=\"true\"\r\n"
												+ "            [legend]=\"false\"\r\n"
												+ "            [roundEdges]=\"false\"\r\n"
												+ "        >\r\n"
												+ "        </ngx-charts-bar-vertical>\r\n"
												+ "  </div>");
									}
									
									
									   
								}
								if(section_type.equals("1*3")){
									if(chart_type1.equals("pie-chart")){
										addChart.append("\n<div class=\"chart-box\">\r\n"
												+ "        <h4> Orders By Payment Type </h4>\r\n"
												+ "        <ngx-charts-advanced-pie-chart\r\n"
												+ "          style=\"position:relative\"\r\n"
												+ "          [view]=\"[284, 155]\"\r\n"
												+ "          [scheme]=\"colorScheme\"\r\n"
												+ "          [results]=\""+widgets_name1+count+"Data\"\r\n"
												+ "        >\r\n"
												+ "        </ngx-charts-advanced-pie-chart>\r\n"
												+ "    </div>");
									}
									if(chart_type1.equals("bar-chart")){
										addChart.append("\n<div class=\"chart-box\">\r\n"
												+ "    <h4> "+label+" </h4>\r\n"
												+ "        <ngx-charts-bar-vertical\r\n"
												+ "            [view]=\"[284, 155]\"\r\n"
												+ "            [barPadding]=\"1\"\r\n"
												+ "            [scheme]=\"barColorScheme\"\r\n"
												+ "            [results]=\""+widgets_name1+count+"Data\"\r\n"
												+ "            [roundDomains]=\"true\"\r\n"
												+ "            [showGridLines]=\"true\"\r\n"
												+ "            [xAxis]=\"true\"\r\n"
												+ "            [yAxis]=\"true\"\r\n"
												+ "            [legend]=\"false\"\r\n"
												+ "            [roundEdges]=\"false\"\r\n"
												+ "        >\r\n"
												+ "        </ngx-charts-bar-vertical>\r\n"
												+ "  </div>");
									}
									
									
								}
						    }
						    count++;
						    count=1+count++;
						    	
						}//widgets1 if end
						//count = 2+i;
						if(widgetList2!=null)
			        	{ 
							
							System.out.println("WidgetList 2::"+widgets_name2+" count "+count+" i "+i);
							 sqlQuery=widgetList2.getSql_query();
							
							
							
                            label=widgetList2.getLabel();
							
							dataFields.append(widgets_name2+count+"Data : any[] = [];\r\n");
								
						    services.append("me.orderService.getOrderStats2(\""+sqlQuery+"\").subscribe(function("+widgets_name2+count+"Data2)\r\n"
											+ "         {\r\n"
											+ "            me."+widgets_name2+count+"Data = "+widgets_name2+count+"Data2.items;\r\n"
											+ "        });");
							
							
						    if(section_type!=null){
						    	if(section_type.equals("1*1")) {
						    		if(chart_type2.equals("pie-chart")){
						    			addChart.append("\n<div class=\"chart-box\">\r\n"
						    					+ "        <h4> "+label+" </h4>\r\n"
						    					+ "        <ngx-charts-advanced-pie-chart\r\n"
						    					+ "          style=\"position:relative\"\r\n"
						    					+ "          [view]=\"[990, 270]\"\r\n"
						    					+ "          [scheme]=\"colorScheme\"\r\n"
						    					+ "          [results]=\""+widgets_name2+count+"Data\"\r\n"
						    					+ "        >\r\n"
						    					+ "        </ngx-charts-advanced-pie-chart>\r\n"
						    					+ "      </div>");
									}
						    		if(chart_type2.equals("bar-chart")){
						    			addChart.append("\n<div class=\"chart-box\">\r\n"
						    					+ "    <h4> "+label+"</h4>\r\n"
						    					+ "        <ngx-charts-bar-vertical\r\n"
						    					+ "            [view]=\"[990, 270]\"\r\n"
						    					+ "            [barPadding]=\"1\"\r\n"
						    					+ "            [scheme]=\"barColorScheme\"\r\n"
						    					+ "            [results]=\""+widgets_name2+count+"Data\"\r\n"
						    					+ "            [roundDomains]=\"true\"\r\n"
						    					+ "            [showGridLines]=\"true\"\r\n"
						    					+ "            [xAxis]=\"true\"\r\n"
						    					+ "            [yAxis]=\"true\"\r\n"
						    					+ "            [legend]=\"false\"\r\n"
						    					+ "            [roundEdges]=\"false\"\r\n"
						    					+ "        >\r\n"
						    					+ "        </ngx-charts-bar-vertical>\r\n"
						    					+ "  </div>");
									}
						    	}//section 1 if end
								if(section_type.equals("1*2")){
									if(chart_type2.equals("pie-chart")){
										addChart.append("\n<div class=\"chart-box\">\r\n"
												+ "      <h4> "+label+" </h4>\r\n"
												+ "      <ngx-charts-advanced-pie-chart\r\n"
												+ "        style=\"position:relative\"\r\n"
												+ "        [view]=\"view\"\r\n"
												+ "        [scheme]=\"colorScheme\"\r\n"
												+ "        [results]=\""+widgets_name2+count+"Data\"\r\n"
												+ "      >\r\n"
												+ "      </ngx-charts-advanced-pie-chart>\r\n"
												+ "    </div>");
									}
									if(chart_type2.equals("bar-chart")){
										addChart.append("\n<div class=\"chart-box\">\r\n"
												+ "    <h4> Orders By Country </h4>\r\n"
												+ "        <ngx-charts-bar-vertical\r\n"
												+ "            [view]=\"view\"\r\n"
												+ "            [barPadding]=\"1\"\r\n"
												+ "            [scheme]=\"barColorScheme\"\r\n"
												+ "            [results]=\""+widgets_name2+count+"Data\"\r\n"
												+ "            [roundDomains]=\"true\"\r\n"
												+ "            [showGridLines]=\"true\"\r\n"
												+ "            [xAxis]=\"true\"\r\n"
												+ "            [yAxis]=\"true\"\r\n"
												+ "            [legend]=\"false\"\r\n"
												+ "            [roundEdges]=\"false\"\r\n"
												+ "        >\r\n"
												+ "        </ngx-charts-bar-vertical>\r\n"
												+ "  </div>");
									}
								}
								if(section_type.equals("1*3")){
									if(chart_type2.equals("pie-chart")){
										addChart.append("\n<div class=\"chart-box\">\r\n"
												+ "        <h4> Orders By Payment Type </h4>\r\n"
												+ "        <ngx-charts-advanced-pie-chart\r\n"
												+ "          style=\"position:relative\"\r\n"
												+ "          [view]=\"[284, 155]\"\r\n"
												+ "          [scheme]=\"colorScheme\"\r\n"
												+ "          [results]=\""+widgets_name2+count+"Data\"\r\n"
												+ "        >\r\n"
												+ "        </ngx-charts-advanced-pie-chart>\r\n"
												+ "    </div>");
									}
									if(chart_type2.equals("bar-chart")){
										addChart.append("\n<div class=\"chart-box\">\r\n"
												+ "    <h4> "+label+" </h4>\r\n"
												+ "        <ngx-charts-bar-vertical\r\n"
												+ "            [view]=\"[284, 155]\"\r\n"
												+ "            [barPadding]=\"1\"\r\n"
												+ "            [scheme]=\"barColorScheme\"\r\n"
												+ "            [results]=\""+widgets_name2+count+"Data\"\r\n"
												+ "            [roundDomains]=\"true\"\r\n"
												+ "            [showGridLines]=\"true\"\r\n"
												+ "            [xAxis]=\"true\"\r\n"
												+ "            [yAxis]=\"true\"\r\n"
												+ "            [legend]=\"false\"\r\n"
												+ "            [roundEdges]=\"false\"\r\n"
												+ "        >\r\n"
												+ "        </ngx-charts-bar-vertical>\r\n"
												+ "  </div>");
									}
								}
							}//section null check
						    count++;
						    count=1+count++;
						}//widgets2 if end
						//count = 3+i;
						if(widgetList3!=null)
			        	{ 
							System.out.println("WidgetList 3::"+widgets_name3+" count "+count+" i "+i);
							
							sqlQuery=widgetList3.getSql_query();
							
							label=widgetList3.getLabel();
							
							dataFields.append(widgets_name3+count+"Data : any[] = [];\r\n");
								
						    services.append("me.orderService.getOrderStats2(\""+sqlQuery+"\").subscribe(function("+widgets_name3+count+"Data2)\r\n"
											+ "         {\r\n"
											+ "            me."+widgets_name3+count+"Data = "+widgets_name3+count+"Data2.items;\r\n"
											+ "        });");
							
						    if(section_type!=null){
						    	if(section_type.equals("1*1")) {
						    		if(chart_type3.equals("pie-chart")){
						    			addChart.append("\n<div class=\"chart-box\">\r\n"
						    					+ "        <h4> "+label+" </h4>\r\n"
						    					+ "        <ngx-charts-advanced-pie-chart\r\n"
						    					+ "          style=\"position:relative\"\r\n"
						    					+ "          [view]=\"[990, 270]\"\r\n"
						    					+ "          [scheme]=\"colorScheme\"\r\n"
						    					+ "          [results]=\""+widgets_name3+count+"Data\"\r\n"
						    					+ "        >\r\n"
						    					+ "        </ngx-charts-advanced-pie-chart>\r\n"
						    					+ "      </div>");
									}
						    		if(chart_type3.equals("bar-chart")){
						    			addChart.append("\n<div class=\"chart-box\">\r\n"
						    					+ "    <h4> "+label+"</h4>\r\n"
						    					+ "        <ngx-charts-bar-vertical\r\n"
						    					+ "            [view]=\"[990, 270]\"\r\n"
						    					+ "            [barPadding]=\"1\"\r\n"
						    					+ "            [scheme]=\"barColorScheme\"\r\n"
						    					+ "            [results]=\""+widgets_name3+count+"Data\"\r\n"
						    					+ "            [roundDomains]=\"true\"\r\n"
						    					+ "            [showGridLines]=\"true\"\r\n"
						    					+ "            [xAxis]=\"true\"\r\n"
						    					+ "            [yAxis]=\"true\"\r\n"
						    					+ "            [legend]=\"false\"\r\n"
						    					+ "            [roundEdges]=\"false\"\r\n"
						    					+ "        >\r\n"
						    					+ "        </ngx-charts-bar-vertical>\r\n"
						    					+ "  </div>");
									}
						    	}//section 1 if end
								if(section_type.equals("1*2")){
									if(chart_type3.equals("pie-chart")){
										addChart.append("\n<div class=\"chart-box\">\r\n"
												+ "      <h4> "+label+" </h4>\r\n"
												+ "      <ngx-charts-advanced-pie-chart\r\n"
												+ "        style=\"position:relative\"\r\n"
												+ "        [view]=\"view\"\r\n"
												+ "        [scheme]=\"colorScheme\"\r\n"
												+ "        [results]=\""+widgets_name3+count+"Data\"\r\n"
												+ "      >\r\n"
												+ "      </ngx-charts-advanced-pie-chart>\r\n"
												+ "    </div>");
									}
									if(chart_type3.equals("bar-chart")){
										addChart.append("\n<div class=\"chart-box\">\r\n"
												+ "    <h4> Orders By Country </h4>\r\n"
												+ "        <ngx-charts-bar-vertical\r\n"
												+ "            [view]=view\"\r\n"
												+ "            [barPadding]=\"1\"\r\n"
												+ "            [scheme]=\"barColorScheme\"\r\n"
												+ "            [results]=\""+widgets_name3+count+"Data\"\r\n"
												+ "            [roundDomains]=\"true\"\r\n"
												+ "            [showGridLines]=\"true\"\r\n"
												+ "            [xAxis]=\"true\"\r\n"
												+ "            [yAxis]=\"true\"\r\n"
												+ "            [legend]=\"false\"\r\n"
												+ "            [roundEdges]=\"false\"\r\n"
												+ "        >\r\n"
												+ "        </ngx-charts-bar-vertical>\r\n"
												+ "  </div>");
									}
								}
								if(section_type.equals("1*3")){
									if(chart_type3.equals("pie-chart")){
										addChart.append("\n<div class=\"chart-box\">\r\n"
												+ "        <h4> Orders By Payment Type </h4>\r\n"
												+ "        <ngx-charts-advanced-pie-chart\r\n"
												+ "          style=\"position:relative\"\r\n"
												+ "          [view]=\"[284, 155]\"\r\n"
												+ "          [scheme]=\"colorScheme\"\r\n"
												+ "          [results]=\""+widgets_name3+count+"Data\"\r\n"
												+ "        >\r\n"
												+ "        </ngx-charts-advanced-pie-chart>\r\n"
												+ "    </div>");
									}
									if(chart_type3.equals("bar-chart")){
										addChart.append("\n<div class=\"chart-box\">\r\n"
												+ "    <h4> "+label+" </h4>\r\n"
												+ "        <ngx-charts-bar-vertical\r\n"
												+ "            [view]=\"[284, 155]\"\r\n"
												+ "            [barPadding]=\"1\"\r\n"
												+ "            [scheme]=\"barColorScheme\"\r\n"
												+ "            [results]=\""+widgets_name3+count+"Data\"\r\n"
												+ "            [roundDomains]=\"true\"\r\n"
												+ "            [showGridLines]=\"true\"\r\n"
												+ "            [xAxis]=\"true\"\r\n"
												+ "            [yAxis]=\"true\"\r\n"
												+ "            [legend]=\"false\"\r\n"
												+ "            [roundEdges]=\"false\"\r\n"
												+ "        >\r\n"
												+ "        </ngx-charts-bar-vertical>\r\n"
												+ "  </div>");
									}
								}
							}
						    count++;
						    count=1+count++;
							
						}//widgets3 if end
						//count = 4+i;
						if(widgetList4!=null)
			        	{ 
							
							 
							sqlQuery=widgetList4.getSql_query();
							
							label=widgetList4.getLabel();
							
							dataFields.append(widgets_name4+"Data : any[] = [];\r\n");
								
						    services.append("me.orderService.getOrderStats2(\""+sqlQuery+"\").subscribe(function("+widgets_name4+"Data2)\r\n"
											+ "         {\r\n"
											+ "            me."+widgets_name4+"Data = "+widgets_name4+"Data2.items;\r\n"
											+ "        });");
							
							
							
							
						}//widgets4 if end
						if(widgetList5!=null)
			        	{ 
							
							 
							sqlQuery=widgetList5.getSql_query();
							
							label=widgetList5.getLabel();
							
							dataFields.append(widgets_name5+"Data : any[] = [];\r\n");
								
						    services.append("me.orderService.getOrderStats2(\""+sqlQuery+"\").subscribe(function("+widgets_name5+"Data2)\r\n"
											+ "         {\r\n"
											+ "            me."+widgets_name5+"Data = "+widgets_name5+"Data2.items;\r\n"
											+ "        });");
							
							
							
							
						}//widgets5 if end
						if(widgetList6!=null)
			        	{ 
							
							 
							sqlQuery=widgetList6.getSql_query();
							
							label=widgetList6.getLabel();
							
							dataFields.append(widgets_name6+"Data : any[] = [];\r\n");
								
						    services.append("me.orderService.getOrderStats2(\""+sqlQuery+"\").subscribe(function("+widgets_name6+"Data2)\r\n"
											+ "         {\r\n"
											+ "            me."+widgets_name6+"Data = "+widgets_name6+"Data2.items;\r\n"
											+ "        });");
							
							
							
							
						}//widgets6 if end
						
						count++;
					}//loop end
					
					
					
					
					tsFile.append("import { Component, OnInit } from '@angular/core';\r\n"
							+ "import { mergeMap } from 'rxjs/operators';\r\n"
							+ "import { OrderService } from 'src/app/services/api/order.service';\r\n"
							+ "import { Router, ActivatedRoute } from '@angular/router';\r\n"
							+ "import { productSalesMulti } from 'src/app/data/products';\r\n"
							+ "import { BiWidgetSetupService } from 'src/app/services/api/bi-widget-setup.service';\r\n"
							+ ""
							+ "\r\n"
							+ "@Component({\r\n"
							+ "  selector: '"+dashboard_name+"',\r\n"
							+ "  templateUrl: './"+dashboard_name+".component.html',\r\n"
							+ "  styleUrls: ['./"+dashboard_name+".component.scss']\r\n"
							+ "})\r\n"
							+ "export class "+dashboard_name+" implements OnInit {\r\n"
							+ "\r\n"
							+ "    view: any[] = [460, 180];\r\n"
							+ "    \r\n"
							+ dataFields
							+ "\r\n"
							+ "    showXAxis = true;\r\n"
							+ "    showYAxis = true;\r\n"
							+ "    gradient = false;\r\n"
							+ "    showLegend = true;\r\n"
							+ "    showXAxisLabel = true;\r\n"
							+ "    xAxisLabel = 'Country';\r\n"
							+ "    showYAxisLabel = true;\r\n"
							+ "    yAxisLabel = 'Sales';\r\n"
							+ "    timeline = true;\r\n"
							+ "    xAxis: boolean = true;\r\n"
							+ "    yAxis: boolean = true;\r\n"
							+ "    id:number;\r\n"
							+ "    \r\n"
							+ "    barColorScheme = {\r\n"
							+ "        domain: ['#007cbb']\r\n"
							+ "    }\r\n"
							+ "    colorScheme = {\r\n"
							+ "      domain: ['#5AA454', '#E44D25', '#CFC0BB', '#7aa3e5', '#a8385d', '#aae3f5']\r\n"
							+ "    };\r\n"
							+ "\r\n"
							+ "   constructor(private router: Router, \r\n"      
							+ "        private route: ActivatedRoute,\r\n"
							+ "        private orderService: BiWidgetSetupService"
							+ "        ) {}\r\n"
							+ "\r\n"
							+ "    ngOnInit() {\r\n"
							+ "        this.id = this.route.snapshot.params[\"id\"];\r\n"
							+ "        //this.getPageData()\r\n"
							+ "        this.getPageData()\r\n"
							+ "    }"
							+ "\n\n"
							+ "getPageData() {\r\n"
							+ "         var me = this;\r\n"
							+services
							
							+ "    }"
							+ "\n}");		
					
					
					
					htmlFile.append("<div class=\"s-order-dash-pg\">\n\n"
							+addChart
							+ "</div>");
					
					cssFile.append("@import '../../../assets/scss/var';\r\n"
							+ "h4{margin-top: 16px;}\r\n"
							+ ".s-order-dash-pg {\r\n"
							+ "  margin: 0px;\r\n"
							+ "  display:flex;\r\n"
							+ "  flex-wrap:wrap;\r\n"
							+ "  flex-direction:row;\r\n"
							+ "  justify-content:center;\r\n"
							+ "  .chart-box{\r\n"
							+ "    margin:16px;\r\n"
							+ "    padding:0 16px;\r\n"
							+ "    background-color: #fff;\r\n"
							+ "    border:1px solid #ccc;\r\n"
							+ "    border-radius: 2px;\r\n"
							+ "  }\r\n"
							+ "}");
					
					
					File log= new File(projectPath + "/webui/src/app/app-routing.module.ts");
					String search = "//abc";  // <- changed to work with String.replaceAll()
					String replacement = "something";
					//file reading
					FileReader fr = new FileReader(log);
					String s;
					try {
					    BufferedReader br = new BufferedReader(fr);

					    while ((s = br.readLine()) != null) {
					        s.replaceAll(search, replacement);
					        // do something with the resulting line
					    }
					}catch(Exception e) {
						e.printStackTrace();
					}
					
					BufferedWriter br=null;
					if(routingCheck=='N') 
					{
						//update the rounting file
						System.out.println("In an static files loop");
						File fileTest = new File(projectPath + "/webui/src/app/app-routing.module.ts");
						Path path = Paths.get(projectPath + "/webui/src/app/app-routing.module.ts");
						StringBuilder code = new StringBuilder();
						List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
						for (String line2 : lines) 
						{
							if (line2.startsWith("//add_import")) 
							{
							    line2="import { "+dashboard_name+" } from './pages/"+dashboard_name+"/"+dashboard_name+".component';\n//add_import";
							 }
							
							if (line2.startsWith("//add_routingdash")) 
							{
								line2 = "{ path: '"+dashboard_name+"'   , component:  "+dashboard_name+"},\n//add_routingdash";
							}
							
							code.append(line2 + "\n");
						}
						//System.out.println(code);
						br = new BufferedWriter(new FileWriter(fileTest)); // replaced string
						br.write(code.toString());
						br.close();
						
						//update the module file
						File fileModule = new File(projectPath + "/webui/src/app/app.module.ts");
						Path pathModule = Paths.get(projectPath + "/webui/src/app/app.module.ts");
						StringBuilder codeModule = new StringBuilder();
						List<String> linesModule = Files.readAllLines(pathModule, StandardCharsets.UTF_8);
						for (String line3 : linesModule) 
						{
							if (line3.startsWith("//add_module_import")) 
							{
							    line3="import { "+dashboard_name+" } from './pages/"+dashboard_name+"/"+dashboard_name+".component';\n//add_module_import";
							 }
							
							if (line3.startsWith("//add_module")) 
							{
								line3 = ""+dashboard_name+",\n//add_module";
							}
							codeModule.append(line3 + "\n");
						}
						br = new BufferedWriter(new FileWriter(fileModule)); // replaced string
						br.write(codeModule.toString());
						br.close();
						
						
						
						
						Rn_Dashboard_Header dashHeader=new Rn_Dashboard_Header();
						dashHeader.setRouting_add('Y');
						Rn_Dashboard_Header updatedDashHeader = dashServices.updateDashHeader(id, dashHeader);

					}
					
					
					if(routingCheck=='N') {
						Rn_Dashboard_Header dashHeader=new Rn_Dashboard_Header();
						dashHeader.setRouting_add('Y');
						Rn_Dashboard_Header updatedDashHeader = dashServices.updateDashHeader(id, dashHeader);

					}else {
						
					}
					
					
					
					FileWriter fw = null;
					BufferedWriter bw = null;
					try {
						
						//create component folder
						File file = new File(projectPath + "/webui/src/app/pages/"+dashboard_name+"/");
						System.out.println("Ganesh File Path = " + file.getAbsolutePath());
						File dir = new File(file.getParent());
						if(!file.exists()) {
							file.mkdirs();
						}
						
						
						//create ts file
						File file2 = new File(projectPath + "/webui/src/app/pages/"+dashboard_name+"/"+dashboard_name+".component.ts");
						File dir2 = new File(file.getParent());
						if(!dir2.exists()) {
							dir2.mkdirs();
						}
						if (!file2.exists()) {
							file2.createNewFile();
						}

						fw = new FileWriter(file2.getAbsoluteFile());
						bw = new BufferedWriter(fw);
						bw.write(tsFile.toString());
						bw.close();
						
						//create html file
						File file3 = new File(projectPath + "/webui/src/app/pages/"+dashboard_name+"/"+dashboard_name+".component.html");
						File dir3 = new File(file.getParent());
						if(!dir3.exists()) {
							dir3.mkdirs();
						}
						if (!file3.exists()) {
							file3.createNewFile();
						}
						
						fw = new FileWriter(file3.getAbsoluteFile());
						bw = new BufferedWriter(fw);
						bw.write(htmlFile.toString());
						bw.close();
						
						//create css file
						File file4 = new File(projectPath + "/webui/src/app/pages/"+dashboard_name+"/"+dashboard_name+".component.scss");
						File dir4 = new File(file.getParent());
						if(!dir4.exists()) {
							dir4.mkdirs();
						}
						if (!file4.exists()) {
							file4.createNewFile();
						}

						fw = new FileWriter(file4.getAbsoluteFile());
						bw = new BufferedWriter(fw);
						bw.write(cssFile.toString());
						bw.close();
						
					}catch (FileNotFoundException e) {
							e.printStackTrace();
							ErrorPojo errorPojo = new ErrorPojo();
							Error error = new Error();
							error.setTitle(Constant.FORM_BUILDER_API_TITLE);
							error.setMessage(Constant.FORM_BUILD_FAILURE);
							errorPojo.setError(error);
							return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
						}
						SuccessPojo successPojo = new SuccessPojo();
						Success success = new Success();
						success.setTitle(Constant.FORM_BUILDER_API_TITLE);
						success.setMessage(Constant.FORM_BUILD_SUCCESS);
						successPojo.setSuccess(success);
						return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);

				}
				

				
}
