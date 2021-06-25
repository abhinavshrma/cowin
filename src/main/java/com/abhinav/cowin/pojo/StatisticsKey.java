package com.abhinav.cowin.pojo;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class StatisticsKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2500403779362010650L;
	private String systemDate;
	private String slotDate;
	private Character eventCd;
	private String vaccineName;
	private String city;

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
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		return "Statistics [systemDate=" + systemDate + ", slotDate=" + slotDate + ", eventCd=" + eventCd
				+ ", vaccineName=" + vaccineName + ", city=" + city + "]";
	}

}
