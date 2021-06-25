package com.abhinav.cowin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

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
}
