package com.abhinav.cowin.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abhinav.cowin.mail.MailHandler;
import com.abhinav.cowin.pojo.Statistics;
import com.abhinav.cowin.pojo.StatsForCSV;
import com.abhinav.cowin.repository.CoWinRepository;
import com.abhinav.cowin.utils.Utils;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

@Component
public class CSVHandler {

	@Autowired
	CoWinRepository coWinRepository;

	@Autowired
	MailHandler mailHandler;

	public int generateCSV() {
		Iterator<Statistics> it = getAll();
		String finalPath = "CoWin_" + Utils.getDateTimeToday() + ".csv";
		File file = new File(finalPath);
		BufferedWriter writer = null;
		FileWriter fr = null;
		try {
			fr = new FileWriter(file);
			writer = new BufferedWriter(fr);
			StatefulBeanToCsv<StatsForCSV> beanToCsv = new StatefulBeanToCsvBuilder<StatsForCSV>(writer)
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
			while (it.hasNext()) {
				Statistics statistics = it.next();
				StatsForCSV csvStats = new StatsForCSV();
				csvStats.setCity(statistics.getStatisticsKey().getCity());
				csvStats.setEventCd(statistics.getStatisticsKey().getEventCd());
				csvStats.setSlotDate(statistics.getStatisticsKey().getSlotDate());
				csvStats.setSystemDate(statistics.getStatisticsKey().getSystemDate());
				csvStats.setVaccineName(statistics.getStatisticsKey().getVaccineName());
				csvStats.setTotalDose1(statistics.getTotalDose1());
				csvStats.setTotalDose2(statistics.getTotalDose2());
				beanToCsv.write(csvStats);
			}
		} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException ex) {
			System.out.println(ex.getStackTrace().toString());
			return 1;
		} finally {
			try {
				writer.close();
				fr.close();
				mailHandler.sendMailWithAttachment("CoWin Data Backup!", "Backup taken at " + new Date(), file);
				file.delete();
				return 0;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	public Iterator<Statistics> getAll() {
		return coWinRepository.findAll().iterator();
	}

}
