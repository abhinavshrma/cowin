package com.abhinav.cowin.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abhinav.cowin.pojo.ResponseSession;
import com.abhinav.cowin.pojo.Session;
import com.abhinav.cowin.pojo.Statistics;
import com.abhinav.cowin.pojo.StatisticsKey;
import com.abhinav.cowin.repository.CoWinRepository;
import com.abhinav.cowin.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class CoWinHandler {
	
	@Autowired
	public CoWinRepository coWinRepository;
	
	public Map<String,Integer> totalDose1Map = new HashMap<>();
	public Map<String,Integer> totalDose2Map = new HashMap<>();
	
	private String isVaccineDetailsEnabled = "cowin.vaccine.details.enabled" ;
		
	public List<Session> getSessionsByDistrict(int districtId,String date)
			throws MalformedURLException, IOException, JsonProcessingException, JsonMappingException {
		URL url = new URL("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByDistrict?district_id="+districtId+"&date="+date+"");
		System.out.println("Going to CoWin to fetch data for date "+date);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36 Edge/12.0");
		int responseCode = conn.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			ObjectMapper mapper = new ObjectMapper();
			ResponseSession rs = mapper.readValue(response.toString(), ResponseSession.class);
			System.out.println("Data fetched successfully for date "+date);
			return rs.getSessions();
		} else {
			System.out.println("Error in request. Response Code:" + responseCode);
			return null;
		}
	}

	public List<Session> getDetails(List<Session> sessionsList, String vaccineName) {
		List<Session> vaccineList = new ArrayList<>();
		for (Session session : sessionsList) {
			if (session.getVaccine().equals(vaccineName) && session.getMinAgeLimit() == 18
					&& session.getAvailableCapacityDose2() != 0) {
				vaccineList.add(session);
				if (null != totalDose1Map.get(vaccineName) && null != totalDose2Map.get(vaccineName)) {
					totalDose1Map.put(vaccineName,
							totalDose1Map.get(vaccineName) + session.getAvailableCapacityDose1());
					totalDose2Map.put(vaccineName,
							totalDose2Map.get(vaccineName) + session.getAvailableCapacityDose2());
				}else {
					totalDose1Map.put(vaccineName, session.getAvailableCapacityDose1());
					totalDose2Map.put(vaccineName, session.getAvailableCapacityDose2());
				}
				
			}
		}
		return vaccineList;
	}

	public String printDetails(List<Session> list, String date) {
		StringBuffer sb = new StringBuffer();
		sb.append("18+ Slots for date = " + date);
		sb.append("\n");
		if (!list.isEmpty()) {
			if ("true".equals(Utils.propertiesUtility(isVaccineDetailsEnabled))) {
				for (Session session : list) {
					sb.append(session.getName() + ", " + session.getBlockName() + ": Vaccine=" + session.getVaccine()
							+ ", Dose1 Available=" + session.getAvailableCapacityDose1() + ", Dose2 Available="
							+ session.getAvailableCapacityDose2());
					sb.append("\n");
				}
			}
			sb.append("**Total Dose1 for " + list.get(0).getVaccine() + " = "
					+ totalDose1Map.get(list.get(0).getVaccine()));
			sb.append("\n**Total Dose2 for " + list.get(0).getVaccine() + " = "
					+ totalDose2Map.get(list.get(0).getVaccine()));
			sb.append("\n");
		} else {
			sb.append("No Slots Available!\n");
		}
		totalDose1Map.clear();
		totalDose2Map.clear();
		sb.append("\n");
		return sb.toString();
	}
	
	public String printDetails(List<Session> list, String date,String vaccineName, int districtId, char eventCd) {
		StringBuffer sb = new StringBuffer();
		StatisticsKey key = new StatisticsKey();
		Statistics stats = new Statistics();
		key.setSlotDate(date);
		key.setVaccineName(vaccineName);
		key.setSystemDate(Utils.getDateToday());
		key.setEventCd(eventCd);
		key.setCity(Utils.getCity(districtId));
		stats.setStatisticsKey(key);
		sb.append("18+ Slots for date = " + date);
		sb.append("\n");
		if (!list.isEmpty()) {
			if ("true".equals(Utils.propertiesUtility(isVaccineDetailsEnabled))) {
				for (Session session : list) {
					sb.append(session.getName() + ", " + session.getBlockName() + ": Vaccine=" + vaccineName
							+ ", Dose1 Available=" + session.getAvailableCapacityDose1() + ", Dose2 Available="
							+ session.getAvailableCapacityDose2());
					sb.append("\n");
				}
			}
			sb.append("**Total Dose1 for " + vaccineName + " = "
					+ totalDose1Map.get(vaccineName));
			sb.append("\n**Total Dose2 for " + vaccineName + " = "
					+ totalDose2Map.get(vaccineName));
			sb.append("\n");
			stats.setTotalDose1(totalDose1Map.get(vaccineName));
			stats.setTotalDose2(totalDose2Map.get(vaccineName));
		} else {
			sb.append("No Slots Available!\n");
			stats.setTotalDose1(0);
			stats.setTotalDose2(0);
		}
		totalDose1Map.clear();
		totalDose2Map.clear();
		sb.append("\n");
		coWinRepository.save(stats);
		return sb.toString();
	}
}
