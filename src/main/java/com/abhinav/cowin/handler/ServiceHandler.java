package com.abhinav.cowin.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.cowin.mail.MailHandler;
import com.abhinav.cowin.pojo.Session;
import com.abhinav.cowin.pojo.StatsLocation;
import com.abhinav.cowin.utils.Utils;

@RestController
public class ServiceHandler {

	@Autowired
	MailHandler mailHandler;
	
	@Autowired
	CoWinHandler cowinHandler;
	
	@Autowired
	CSVHandler csvHandler;
	
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
	
	@Scheduled(cron = "0 0 9 * * *")
	public void trigger9AM() {
		//496 - Mohali
		start("COVISHIELD",496,'O');
		start("COVAXIN",496,'O');
		
		//108- Chandigarh
		start("COVISHIELD",108,'O');
		start("COVAXIN",108,'O');	
	}
	
	@Scheduled(cron = "0 0 21 * * *")
	public void trigger9PM() {
		//496 - Mohali
		start("COVISHIELD",496,'C');
		start("COVAXIN",496,'C');
		
		//108- Chandigarh
		start("COVISHIELD",108,'C');
		start("COVAXIN",108,'C');	
	}
	
	@Scheduled(cron = "0 0 23 * * *")
	public void takeDailyBackup() {
		backUpData();	
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
		return "Application is Up and Running!! - v4.3";
	}
	
	public String start(String vaccine, int districtId,char eventCd) {
		List<String> list = new ArrayList<>();
		cowinHandler.totalDose1Map.clear();
		cowinHandler.totalDose2Map.clear();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		for (int i = 0; i < Integer.parseInt(Utils.propertiesUtility(daysForDataFetch)); i++) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, i);
			String date = dateFormat.format(c.getTime());
			try {
				list.add(cowinHandler.printDetails(
						cowinHandler.getDetails(cowinHandler.getSessionsByDistrict(districtId, date), vaccine), date,
						vaccine, districtId,eventCd));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Sending Mail........");
		mailHandler.sendEmail(list,vaccine,districtId);
		return "Started Successfully";
	}
	
	@GetMapping(value = "/backup")
	public String backUpData() {
		if (csvHandler.generateCSV() == 0) {
			return "Backup Generated Successfully!!";
		} else {
			return "Could not create backup!!";
		}
	}
	
	@GetMapping(value = "/get/{vaccine}/{date}")
	public List<StatsLocation> getDataFor29July(@PathVariable String vaccine, @PathVariable String date){
		try {
			List<Session> listChd = cowinHandler.getSessionsByDistrict(108, date);
			List<Session> listMhl = cowinHandler.getSessionsByDistrict(496, date);
			Set<Session> set = new HashSet<>();
			set.addAll(listChd);
			set.addAll(listMhl);			
			List<StatsLocation> newList = new ArrayList<StatsLocation>();
			for (Session session : set) {
				if (session.getMinAgeLimit() == 18 && session.getVaccine().equalsIgnoreCase(vaccine)
						&& session.getAvailableCapacityDose2().intValue() != 0) {
					newList.add(new StatsLocation(session.getName(), session.getAddress(), session.getFee(),
							session.getAvailableCapacityDose1(), session.getAvailableCapacityDose2()));
				}
			}
			return newList;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
