package com.abhinav.cowin.pojo;

public class StatsForCSV {

	private String systemDate;
	private String slotDate;
	private Character eventCd;
	private String vaccineName;
	private String city;
	private Integer totalDose1;
	private Integer totalDose2;

	public String getSystemDate() {
		return systemDate;
	}

	public void setSystemDate(String systemDate) {
		this.systemDate = systemDate;
	}

	public String getSlotDate() {
		return slotDate;
	}

	public void setSlotDate(String slotDate) {
		this.slotDate = slotDate;
	}

	public Character getEventCd() {
		return eventCd;
	}

	public void setEventCd(Character eventCd) {
		this.eventCd = eventCd;
	}

	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getTotalDose1() {
		return totalDose1;
	}

	public void setTotalDose1(Integer totalDose1) {
		this.totalDose1 = totalDose1;
	}

	public Integer getTotalDose2() {
		return totalDose2;
	}

	public void setTotalDose2(Integer totalDose2) {
		this.totalDose2 = totalDose2;
	}



}
