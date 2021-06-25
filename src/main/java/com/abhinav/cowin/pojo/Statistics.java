package com.abhinav.cowin.pojo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Statistics {
	
	@EmbeddedId
	StatisticsKey statisticsKey;
	private Integer totalDose1;
	private Integer totalDose2;
	
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
	public StatisticsKey getStatisticsKey() {
		return statisticsKey;
	}
	public void setStatisticsKey(StatisticsKey statisticsKey) {
		this.statisticsKey = statisticsKey;
	}
	@Override
	public String toString() {
		return "Statistics [statisticsKey=" + statisticsKey + ", totalDose1=" + totalDose1 + ", totalDose2="
				+ totalDose2 + "]";
	}
	
}
