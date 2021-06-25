package com.abhinav.cowin.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.cowin.mail.MailHandler;

@RestController
public class ServiceHandler {

	@Autowired
	MailHandler mailHandler;
	
	@Autowired
	CoWinHandler cowinHandler;
	
	private static String daysForDataFetch = "cowin.vaccine.data.days";
	
	//@Scheduled(cron = "0 0 */4 * * *")
	public void triggerHourly() {
		//496 - Mohali
		//start("COVISHIELD",496);
		//start("COVAXIN",496);
		
		//108- Chandigarh
		//start("COVISHIELD",108);
		//start("COVAXIN",108);	
	}
	
	@Scheduled(cron = "0 0 8 * * *")
	public void trigger8AM() {
		//496 - Mohali
		start("COVISHIELD",496,'O');
		start("COVAXIN",496,'O');
		
		//108- Chandigarh
		start("COVISHIELD",108,'O');
		start("COVAXIN",108,'O');	
	}
	
	@Scheduled(cron = "0 0 20 * * *")
	public void trigger8PM() {
		//496 - Mohali
		start("COVISHIELD",496,'C');
		start("COVAXIN",496,'C');
		
		//108- Chandigarh
		start("COVISHIELD",108,'C');
		start("COVAXIN",108,'C');	
	}
	
	//@Scheduled(cron = "0 0/50 * * * *")
	public void cronJobSchCovax() throws Exception {
		System.out.println(new Date()+"-----"+"Executing start process...COVAXIN..");
		//start("COVAXIN");
	}
	
	//@Scheduled(cron = "0 0/45 * * * *")
	public void cronJobSchCovi() throws Exception {
		System.out.println(new Date()+"-----"+"Executing start process...COVISHIELD..");
		//start("COVISHIELD");
	}
	
	@GetMapping(value = "/start")
	public String startManually(String vaccine) {
		start("COVISHIELD",496,'T');
		start("COVAXIN",496,'T');
		
		start("COVISHIELD",108,'T');
		start("COVAXIN",108,'T');
		return "Started Successfully";
	}
	
	@GetMapping(value = "/")
	public String health() {
		return "Application is Up and Running!! - v2.1";
	}
	
	public String start(String vaccine, int districtId,char eventCd) {
		List<String> list = new ArrayList<>();
		cowinHandler.totalDose1Map.clear();
		cowinHandler.totalDose2Map.clear();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		for (int i = 0; i < Integer.parseInt(CoWinHandler.propertiesUtility(daysForDataFetch)); i++) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, i);
			String date = dateFormat.format(c.getTime());
			try {
				//list.add(CoWinHandler.printDetails(CoWinHandler.getDetails(CoWinHandler.getSessionsByDistrict(districtId, date), vaccine), date));
				list.add(cowinHandler.printDetails(
						cowinHandler.getDetails(cowinHandler.getSessionsByDistrict(districtId, date), vaccine), date,
						vaccine, districtId,eventCd));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println(printDetails(getDetails(getSessionsByDistrict(496,date),"COVISHIELD"),date));
		}
		/*
		 * System.out.println(list.get(0)); System.out.println(list.get(1));
		 * System.out.println(list.get(2)); System.out.println(list.get(3));
		 */
		System.out.println("Sending Mail........");
		mailHandler.sendEmail(list,vaccine,districtId);
		return "Started Successfully";
	}

}
