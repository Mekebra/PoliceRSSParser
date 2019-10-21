package com.ekebratt.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;


public class RSSModel implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime date;
	private String jsDate;
	private String title;
	private String city;
	private String location;
	private String description;
	private String lat;
	private String lon;
	private int category;
	
	public RSSModel() {}
	
	public RSSModel(LocalDateTime date, String title, String city, String location, String description, int category) {
		super();
		this.date = date;
		setJsDate(date);
		this.title = title;
		this.city = city;
		if(location.isEmpty()) {
			this.location = city;
		} else {
			this.location = location;
		}
		this.description = description;
		this.category = category;
	}


	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public void setJsDate(String jsDate) {
		this.jsDate = jsDate;
	}
	public void setJsDate(LocalDateTime jsDate) {
		this.jsDate = jsDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
	}
	public String getJsDate() {
		return jsDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
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
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	@Override
	public String toString() {
		final String comma = ", ";
		String string = "RSSModel[date=" + date + comma
				+"title=" + title + comma
				+"city=" + city+ comma
				+"location=" + location+ comma
				+"description="+description+comma
				+"lat="+lat+comma
				+"lon="+lon+comma
				+"category="+ category+ "]";
		return string;
	}

}
