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
	
	@Scheduled(cron = "0 0 */3 * * *")
	public void triggerHourly() {
		start("COVISHIELD");
		start("COVAXIN");
	}
	
	//@Scheduled(cron = "0 0/50 * * * *")
	public void cronJobSchCovax() throws Exception {
		System.out.println(new Date()+"-----"+"Executing start process...COVAXIN..");
		start("COVAXIN");
	}
	
	//@Scheduled(cron = "0 0/45 * * * *")
	public void cronJobSchCovi() throws Exception {
		System.out.println(new Date()+"-----"+"Executing start process...COVISHIELD..");
		start("COVISHIELD");
	}
	
	@GetMapping(value = "/start")
	public String startManually(String vaccine) {
		start("COVISHIELD");
		start("COVAXIN");
		return "Started Successfully";
	}
	
	@GetMapping(value = "/")
	public String health() {
		return "Application is Up and Running!!";
	}
	
	public String start(String vaccine) {
		List<String> list = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		for (int i = 0; i < 4; i++) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, i);
			String date = dateFormat.format(c.getTime());
			try {
				list.add(CoWinHandler.printDetails(CoWinHandler.getDetails(CoWinHandler.getSessionsByDistrict(496, date), vaccine), date));
				//list.add(CoWinHandler.printDetails(CoWinHandler.getDetails(CoWinHandler.getSessionsByDistrict(496, date), "COVISHIELD"), date));
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
		mailHandler.sendEmail(list,vaccine);
		return "Started Successfully";
	}

}
