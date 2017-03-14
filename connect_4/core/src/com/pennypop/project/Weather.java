package com.pennypop.project;

/*
 * This is class to initiate and store weather data from api
 * 
 * created by Wenjin
 */
public class Weather {

	private String location;
	private String description;
	private Double temperature;
	private Double wind;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getTemperature() {
		return temperature;
	}
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	public Double getWind() {
		return wind;
	}
	public void setWind(Double wind) {
		this.wind = wind;
	}

}
