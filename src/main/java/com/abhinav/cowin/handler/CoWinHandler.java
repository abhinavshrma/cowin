package com.abhinav.cowin.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import com.abhinav.cowin.pojo.ResponseSession;
import com.abhinav.cowin.pojo.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CoWinHandler {
	
	public static List<Session> getSessionsByDistrict(int districtId,String date)
			throws MalformedURLException, IOException, JsonProcessingException, JsonMappingException {
		URL url = new URL("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByDistrict?district_id="+districtId+"&date="+date+"");
		//URL url = new URL("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode=160062&date=05-06-2021");
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

	public static List<Session> getDetails(List<Session> sessionsList, String vaccineName) {
		List<Session> covaxinList = new ArrayList<>();
		for (Session session : sessionsList) {
			if (session.getVaccine().equals(vaccineName) && session.getMinAgeLimit() == 18) {
				covaxinList.add(session);
			}
		}
		return covaxinList;
	}

	public static String printDetails(List<Session> list, String date) {
		StringBuffer sb = new StringBuffer();
		sb.append("18+ Slots for date = " + date);		
		sb.append("\n");
		if (!list.isEmpty()) {
			for (Session session : list) {
				sb.append(session.getName() + ", " + session.getBlockName() + ": Vaccine="
						+ session.getVaccine() + ", Dose1 Available=" + session.getAvailableCapacityDose1()
						+ ", Dose2 Available=" + session.getAvailableCapacityDose2());
				sb.append("\n");
			}
		} else {
			sb.append("No Slots Available!\n");
		}
		sb.append("\n");
		return sb.toString();
	}

}
