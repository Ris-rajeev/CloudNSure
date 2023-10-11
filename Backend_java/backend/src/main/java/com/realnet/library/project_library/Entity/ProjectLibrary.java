package com.realnet.library.project_library.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.realnet.fnd.entity.Rn_Who_AccId_Column;
import com.realnet.library.module_library.Entity.ModuleLibrary;

import lombok.Data;
@Entity
@Data
public class ProjectLibrary extends Rn_Who_AccId_Column {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    @Column(name = "ID")
    private Integer id;
    
    private String owner;
    private Long owned_by;
    @Column(name = "PROJECT_NAME")
    private String projectName;
    @Column(name = "DESCRIPTION")
    private String description;
 
    @Column(name = "COPY_TO")
    private String copyTo;
    @Column(name = "Technology_stack")
    private String technologyStack;

    @Column(name = "PROJECT_PREFIX")
    private String projectPrefix;
    @Column(name = "DB_NAME")
    private String dbName;
    @Column(name = "DB_USERNAME")
    private String dbUserName;
    @Column(name = "DB_PASSWORD")
    private String dbPassword;
    @Column(name = "PORT_NO")
    private String portNumber;
    
    @Column(name = "DB_NAMESPACE")
    private String namespace;
    
    @Column(name = "DB_TAGS")
    private  String tags;
    
    @Column(name = "DB_CATEGORY")
    private String category;
    
    @Column(name = "DB_ACCESSIBILITY")
    private Boolean accessibility;
    
    @Column(name = "IS_ARCHIVED")
    private Boolean is_archived;
    
    @Column(name = "IS_AGED")
    private Boolean is_aged;
    
    @Column(name = "IS_FAV")
    private Boolean is_fav; 
    
    @Column(name = "FAV_CNT")
    private Integer favCnt;
    
    @Column(name = "IS_STARED")
    private Boolean is_stared;  
    
    @Column(name = "STARED_CNT")
    private Integer staredCnt;
    
    @Column(name = "IS_WATCHLISTED")
    private Boolean is_watchlisted;
    
    @Column(name = "WATCHLISTED_CNT")
    private Integer watchlistedCnt;
    
    @Column(name = "IS_FUTURISTIC")
    private Boolean is_futuristic;
    
    @Column(name = "FUTURISTIC_CNT")
    private Integer futuristicCnt;
    
    @Column(name = "IS_PINNED")
    private Boolean is_pinned;
    
    @Column(name = "PINNED_CNT")
    private Integer pinnedCnt;
        
    @OneToMany(mappedBy = "project", targetEntity = ModuleLibrary.class, orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ModuleLibrary> modules=new ArrayList<>();
         
  	
  	private int workflow_id;
  	private String gitea_url;
	
}
