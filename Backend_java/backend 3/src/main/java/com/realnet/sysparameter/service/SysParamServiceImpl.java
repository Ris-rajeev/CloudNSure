package com.realnet.sysparameter.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.realnet.exceptions.ResourceNotFoundException;
import com.realnet.exceptions.StorageException;
import com.realnet.fnd.service.FileStorageService;
import com.realnet.sysparameter.entity.SysParamEntity;
import com.realnet.sysparameter.entity.SysParamUpload;
import com.realnet.sysparameter.repository.SysParamRepository;
import com.realnet.sysparameter.repository.SysparamUploadRepo;
import com.realnet.users.entity1.AppUser;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class SysParamServiceImpl implements SysParamService {
	
	@Autowired
	private SysParamRepository sysParamRepo;
	
	@Autowired
	private SysparamUploadRepo sysparamUploadRepo;

	@Override
	public SysParamEntity getById(int id) {
		// TODO Auto-generated method stub
		SysParamEntity sysparam=sysParamRepo.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("favourite not found :: " + id));
		return sysparam;
	}

	@Override
	public SysParamEntity save(SysParamEntity sysparam) {
		// TODO Auto-generated method stub
		SysParamEntity savedParam=sysParamRepo.save(sysparam);
		return savedParam;
	}

	@Override
	public SysParamEntity updateSysParamById(int id, SysParamEntity sysparam) {
		// TODO Auto-generated method stub
		SysParamEntity NewSysparam = sysParamRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("sysprameter not found :: " + id));
			
		NewSysparam.setSchedulerTime(sysparam.getSchedulerTime());			
		NewSysparam.setLeaseTaxCode(sysparam.getLeaseTaxCode());		
		NewSysparam.setVesselConfProcessLimit(sysparam.getVesselConfProcessLimit());		
		NewSysparam.setRowToDisplay(sysparam.getRowToDisplay());		
		NewSysparam.setLinkToDisplay(sysparam.getLinkToDisplay());
		NewSysparam.setRowToAdd(sysparam.getRowToAdd());
		NewSysparam.setLovRowToDisplay(sysparam.getLovRowToDisplay());
		NewSysparam.setLovLinkToDisplay(sysparam.getLovLinkToDisplay());
		NewSysparam.setOidserverName(sysparam.getOidserverName());
		NewSysparam.setOidBase(sysparam.getOidBase());
		NewSysparam.setOidAdminUser(sysparam.getOidAdminUser());
		NewSysparam.setOidServerPort(sysparam.getOidServerPort());
		NewSysparam.setUserDefaultGroup(sysparam.getUserDefaultGroup());
		NewSysparam.setDefaultDepartment(sysparam.getDefaultDepartment());
		NewSysparam.setDefaultPosition(sysparam.getDefaultPosition());
		NewSysparam.setSingleCharge(sysparam.getSingleCharge());
		NewSysparam.setFirstDayOftheWeek(sysparam.getFirstDayOftheWeek());
		NewSysparam.setHourPerShift(sysparam.getHourPerShift());
		NewSysparam.setCnBillingFrequency(sysparam.getCnBillingFrequency());
		NewSysparam.setBillingDepartmentCode(sysparam.getBillingDepartmentCode());
		NewSysparam.setBasePriceList(sysparam.getBasePriceList());
		NewSysparam.setNonContainerServiceOrder(sysparam.getNonContainerServiceOrder());
		NewSysparam.setEdiMaeSchedulerONOFF(sysparam.getEdiMaeSchedulerONOFF());
		NewSysparam.setEdiSchedulerONOFF(sysparam.getEdiSchedulerONOFF());
		NewSysparam.setCompany_Display_Name(sysparam.getCompany_Display_Name());		
		sysParamRepo.save(NewSysparam);
		
		return NewSysparam;
	}

//	@Override
//	public String uplaodImage(String path, MultipartFile file) throws IOException {
//		
////		 File name
//		String name=  file.getOriginalFilename();
//		
////		 Fullpath
//		String filePath= path + File.separator + name;
//		
////		create folder
//		File f =new File(path);
//		if(!f.exists()) {
//			f.mkdir();
//		}
//		
////		file copy
//		
//		Files.copy(file.getInputStream(), Paths.get(filePath));
//		
//		return name;
////		copy(file.getInputStream(), Paths.get(filePath));
//		
//		
////		SysParamEntity sys = new SysParamEntity();
////		
////		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
////		if(fileName.contains(".."))
////		{
////			System.out.println("not a a valid file");
////		}
////		try {
////			sys.setUpload_Logo(Base64.getEncoder().encodeToString(file.getBytes()));
////		} catch (IOException e) {
////			e.printStackTrace();
////		}	
////		
////		sysParamRepo.save(sys);
//	}

	@Override
	public boolean upload_logo(MultipartFile file, String uploadPath) {
		if (file.isEmpty()) {
			throw new StorageException("Failed to store empty file");
		}
		try {		
			String fileName = file.getOriginalFilename();

			String ext = fileName.substring(fileName.lastIndexOf("."));
			String extension = FilenameUtils.getExtension(fileName); // lol
			String newName = "profile-pic-" +  ext;
			
			System.out.println(System.getProperty("java.io.tmpdir"));
		    File convFile = new File(System.getProperty("java.io.tmpdir")+System.getProperty("file.separator")+newName);
		    file.transferTo(convFile);
		    return true;
		} catch (IOException e) {
			String msg = String.format("Failed to store file %s", file.getName());
			log.info(msg);
			return false;
		}
		

	}

	@Override
	public List<SysParamUpload> addallattachments(List<SysParamUpload> attachments) {

     for(SysParamUpload at : attachments) {
    	 sysparamUploadRepo.save(at);
     }
		return attachments;
	}

	// ========================== BCF EXTRACTOR =============================
	// max size 100 mb
//	public void uploadFile(MultipartFile file, String location) {
//		if (file.isEmpty()) {
//			throw new StorageException("Failed to store empty file");
//		}
//		try {
//			String fileName = file.getOriginalFilename();
//			InputStream is = file.getInputStream();
//			Files.copy(is, Paths.get(location + fileName), StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			String msg = String.format("Failed to store file : %s", file.getName());
//			log.debug(msg);
//			throw new StorageException("Failed to store file", e);
//		}


@Override
	
	public List<SysParamUpload> getallattachmentsbyid(int sys_param_entity_id) {
		
		List<SysParamUpload> list = sysparamUploadRepo.findAllById(sys_param_entity_id);
		return list;
	}
		
	}
	
	
	


