package com.abhinav.cowin.pojo;

public class StatsLocation {
	public StatsLocation(String name, String address, String fee, int dose1, int dose2) {
		this.name = name;
		this.address = address;
		this.fee = fee;
		this.dose1 = dose1;
		this.dose2 = dose2;
				
	}
	private String name;
	private String address;
	private String fee;
	private int dose1;
	private int dose2;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public int getDose1() {
		return dose1;
	}
	public void setDose1(int dose1) {
		this.dose1 = dose1;
	}
	public int getDose2() {
		return dose2;
	}
	public void setDose2(int dose2) {
		this.dose2 = dose2;
	}
	@Override
	public String toString() {
		return "StatsLocation [name=" + name + ", address=" + address + ", fee=" + fee + ", dose1=" + dose1 + ", dose2="
				+ dose2 + "]";
	}

}
