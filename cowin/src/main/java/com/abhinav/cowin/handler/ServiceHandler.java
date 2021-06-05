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
	
	
	@Scheduled(cron = "0 0/10 * * * *")
	public void cronJobSch() throws Exception {
		System.out.println(new Date()+"-----"+"Executing start process...");
		start();
	}

	//@GetMapping(value = "/start")
	public String start() {
		List<String> list = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		for (int i = 0; i < 4; i++) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, i);
			String date = dateFormat.format(c.getTime());
			try {
				list.add(CoWinHandler.printDetails(CoWinHandler.getDetails(CoWinHandler.getSessionsByDistrict(496, date), "COVAXIN"), date));
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
		mailHandler.sendEmail(list);
		return "Started Successfully";
	}

}
