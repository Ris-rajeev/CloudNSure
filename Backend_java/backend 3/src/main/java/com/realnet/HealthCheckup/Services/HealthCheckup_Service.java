package com.realnet.HealthCheckup.Services;

import com.realnet.HealthCheckup.Repository.HealthCheckup_Repository;
import com.realnet.HealthCheckup.Entity.HealthCheckup_t;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckup_Service {
	@Autowired
	private HealthCheckup_Repository Repository;

	public HealthCheckup_t Savedata(HealthCheckup_t data) {
		return Repository.save(data);
	}

	public List<HealthCheckup_t> getdetails() {
		return (List<HealthCheckup_t>) Repository.findAll();
	}
	
	//get by job type
	public List<HealthCheckup_t> getdetailsbyjobtype() {
		return (List<HealthCheckup_t>) Repository.findAll();
	}

	public HealthCheckup_t getdetailsbyId(Long id) {
		return Repository.findById(id).get();
	}

	public void delete_by_id(Long id) {
		Repository.deleteById(id);
	}

	public HealthCheckup_t update(HealthCheckup_t data, Long id) {
		HealthCheckup_t old = Repository.findById(id).get();
		old.setIp(data.getIp());
		old.setPort(data.getPort());
		old.setServiceName(data.getServiceName());
		old.setCreateProject(data.isCreateProject());
		old.setBuildProject(data.isBuildProject());
		old.setCreateDeployment(data.isCreateDeployment());
		old.setDeployApp(data.isDeployApp());
		final HealthCheckup_t test = Repository.save(old);
		return test;
	}

	
	
	
	public boolean isAppRunning(HealthCheckup_t healthCheckup) {
	    try {
	        URL url = new URL("http://" + healthCheckup.getIp() + ":" + healthCheckup.getPort() + "/");
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setConnectTimeout(5000);
	        connection.setReadTimeout(5000);
	        int responseCode = connection.getResponseCode();
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	
	
	
	public boolean isAppRunningOrNot(String ip, Long port) {
	    try {
	        URL url = new URL("http://" + ip + ":" + port + "/");
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setConnectTimeout(5000);
	        connection.setReadTimeout(5000);
	        int responseCode = connection.getResponseCode();
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}


}