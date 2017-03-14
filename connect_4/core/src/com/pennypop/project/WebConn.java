package com.pennypop.project;

import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

/*
 * THis is the connection class to api, and fetch weather data to weather class
 * 
 * created by Wenjin
 */
public class WebConn {

	//http://api.openweathermap.org/data/2.5/weather?
	//q=San%20Francisco,US
	//&appid=2e32d2b4b825464ec8c677a49531e9ae
	private static final String WEB_URL = "http://api.openweathermap.org/data/2.5/weather?";
	//q=San%20Francisco,US
	private static final String APP_ID="2e32d2b4b825464ec8c677a49531e9ae";//appid=?
	ClientResource client;
	Weather weather;
	

	public WebConn() {
		//client = new ClientResource(URL);
		weather = new Weather();
	}
	
	public void getConnection(String city, String country){
		String url = WEB_URL+"q="+city.replaceAll(" ", "%20")+","+country+"&appid="+APP_ID;
		client = new ClientResource(url);
	}

	public void getWeatherInfo() {
		String order_string;
		try {
			order_string = new JsonRepresentation(client.get()).getText();
			JSONObject json = new JSONObject(order_string);
			JSONArray json_weather = json.getJSONArray("weather");
			weather.setDescription(json_weather.getJSONObject(0).getString("description"));
			weather.setLocation(json.getString("name")); 
			weather.setTemperature(json.getJSONObject("main").getDouble("temp"));
			weather.setWind(json.getJSONObject("wind").getDouble("speed"));
		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(desc + loc + temp + wind);
	}
}
