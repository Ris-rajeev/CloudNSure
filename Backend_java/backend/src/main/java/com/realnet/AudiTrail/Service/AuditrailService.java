package com.realnet.AudiTrail.Service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.Accesstype_back.Entity.Accesstype;
import com.realnet.AudiTrail.Entity.AudiTrail_t;
import com.realnet.AudiTrail.Repos.AudiTrail_Repo;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;

@Service
public class AuditrailService {

	@Autowired
	private AudiTrail_Repo audiTrail_Repo;

	@Autowired
	private AppUserServiceImpl userService;

	public List<AudiTrail_t> getdetails() {
		List<AudiTrail_t> findAll = audiTrail_Repo.findAll();
		return findAll;
	}

	public AudiTrail_t getdetailsbyId(Long id) {
		return audiTrail_Repo.findById(id).get();
	}

	public AudiTrail_t saveaudiTrail_t(Object olddata, Object newdata, String entityname)
			throws JsonProcessingException {

		AppUser loggedInUser = userService.getLoggedInUser();
		String username = loggedInUser.getUsername();
		HashMap<String, String> map = new HashMap<>();
		HashMap<String, String> map1 = new HashMap<>();
		ArrayList<Object> list = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		JsonParser parser = new JsonParser();

		// for old data
		String str = mapper.writeValueAsString(olddata);
		JsonElement element = parser.parse(str);

		JsonObject obj = element.getAsJsonObject();
		Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
		for (Map.Entry<String, JsonElement> entry : entries) {
			String key =  entry.getKey().toString();
			String value1 = entry.getValue().getAsString();

			map.put("\""+"old" + key+"\"", "\""+value1+"\"");
		}
		String replaceAll = map.toString().replaceAll("=",":");	
		list.add(replaceAll);

		// for new data
		String n = mapper.writeValueAsString(newdata);
		JsonElement e = parser.parse(n);

		JsonObject o = e.getAsJsonObject();
		Set<Map.Entry<String, JsonElement>> es = o.entrySet();
		for (Map.Entry<String, JsonElement> en : es) {
			String key = en.getKey().toString();
			String value1 = en.getValue().getAsString();

			map1.put("\""+"new" + key+"\"", "\""+value1+"\"");
		}
		String replaceAll2 = map1.toString().replaceAll("=",":");
		list.add(replaceAll2);

		InetAddress ip = null;
		StringBuilder sb = new StringBuilder();

		try {

			ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			System.out.print("Current MAC address : ");

			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));
			}
			System.out.println(sb.toString());
		} catch (Exception e1) {

			e1.printStackTrace();

		}

		AudiTrail_t trail = new AudiTrail_t();
		trail.setJson(list.toString());
		trail.setAction("update");
		trail.setMac_id(sb.toString());
		trail.setIp(ip.toString());
		trail.setUser(username);
		trail.setEntity_name(entityname);
		AudiTrail_t t = audiTrail_Repo.save(trail);

		return t;

	}

}
