package com.realnet.fnd.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.realnet.icon.entity.ArchivedEntity;
import com.realnet.icon.entity.FavouriteEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = { "modules" })
@Entity
@Table(name = "RN_PROJECT_SETUP")
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
@Data
public class Rn_Project_Setup extends Rn_Who_AccId_Column {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
//    @Column(name = "TECH_STACK_ID")
//    private int techStackId;
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
	private String tags;

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

	private Integer private_deployid;

	@OneToMany(mappedBy = "project", targetEntity = Rn_Module_Setup.class, orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Rn_Module_Setup> modules;

//  @OneToMany(cascade = CascadeType.ALL)
//  @JoinColumn(name = "object_id")
//  private List<FavouriteEntity> favourite;  

	@Transient
	private FavouriteEntity favourite;

	@Transient
	private ArchivedEntity archived;

	private int workflow_id;
	private String gitea_url;
}
