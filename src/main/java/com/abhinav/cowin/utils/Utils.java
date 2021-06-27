package com.abhinav.cowin.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.abhinav.cowin.handler.CoWinHandler;

public class Utils {

	public static String propertiesUtility(String key) {
		Properties properties = new Properties();
		InputStream inputStream = CoWinHandler.class.getClassLoader().getResourceAsStream("application.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return properties.getProperty(key);
	}

	public static String getCity(int districtId) {
		if (districtId == 496) {
			return "Mohali";
		} else if (districtId == 108) {
			return "Chandigarh";
		}
		return null;
	}

	public static String getDateToday() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		return dateFormat.format(new Date());
	}

	public static String getDateTimeToday() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
		return dateFormat.format(new Date());
	}
}
