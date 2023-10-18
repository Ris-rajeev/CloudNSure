package com.realnet.SuperUser.Services;

import com.realnet.SuperUser.Repository.SuperUser_Repository;
import com.realnet.SuperUser.Entity.SuperUser_t;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuperUser_Service {
	@Autowired
	private SuperUser_Repository Repository;

	public SuperUser_t Savedata(SuperUser_t data) {
		return Repository.save(data);
	}

	public List<SuperUser_t> getdetails() {
		return (List<SuperUser_t>) Repository.findAll();
	}

	public SuperUser_t getdetailsbyId(Long id) {
		return Repository.findById(id).get();
	}

	public void delete_by_id(Long id) {
		Repository.deleteById(id);
	}

	public SuperUser_t update(SuperUser_t data, Long id) {
		SuperUser_t old = Repository.findById(id).get();
		old.setUsername(data.getUsername());
		old.setUserPassw(data.getUserPassw());
		old.setTitle(data.getTitle());
		old.setShortName(data.getShortName());
		old.setFullName(data.getFullName());
		old.setExpiryDate(data.getExpiryDate());
		old.setDaysMth(data.getDaysMth());
		old.setNoDaysMth(data.getNoDaysMth());
		old.setStatus(data.getStatus());
		old.setChangePassw(data.getChangePassw());
		old.setCreateby(data.getCreateby());
		old.setCreatedate(data.getCreatedate());
		old.setUpdateby(data.getUpdateby());
		old.setUpdatedate(data.getUpdatedate());
		old.setUsrGrp(data.getUsrGrp());
		old.setLangCode(data.getLangCode());
		old.setFirstLogin(data.getFirstLogin());
		old.setPositionCode(data.getPositionCode());
		old.setDepartmentCode(data.getDepartmentCode());
		old.setAccount(data.getAccount());
		old.setEmail(data.getEmail());
		old.setNotification(data.getNotification());
		old.setCustomerId(data.getCustomerId());
		old.setPassword1(data.getPassword1());
		old.setPassword2(data.getPassword2());
		old.setPassword3(data.getPassword3());
		old.setPassword4(data.getPassword4());
		old.setPwdChangedCnt(data.getPwdChangedCnt());
		old.setLastPwdChangedDate(data.getLastPwdChangedDate());
		old.setPhoto(data.getPhoto());
		old.setPhotoName(data.getPhotoName());
		old.setProvider(data.getProvider());
		old.setCountry(data.getCountry());
		old.setDepString(data.getDepString());
		old.setIsBlocked(data.getIsBlocked());
		old.setAbout(data.getAbout());
		old.setChecknumber(data.getChecknumber());
		old.setWorking(data.getWorking());
		old.setAccesstype(data.getAccesstype());
		old.setAccess_duration(data.getAccess_duration());
		old.setRandom_no(data.getRandom_no());
		old.setMob_no(data.getMob_no());
		old.setIsComplete(old.isIsComplete());
		old.setActive(old.isActive());
		old.setCustomerNumer(data.getCustomerNumer());
		old.setPositionCodeString(data.getPositionCodeString());
		old.setDepartmentCodeString(data.getDepartmentCodeString());
		old.setUsrGrpId(data.getUsrGrpId());
		old.setConfirmPassword(data.getConfirmPassword());
		old.setUsrGrpName(data.getUsrGrpName());
		old.setTotalLogInfo(data.getTotalLogInfo());
		old.setPass(data.getPass());
		old.setRoles(data.getRoles());
		final SuperUser_t test = Repository.save(old);
		return test;
	}
}