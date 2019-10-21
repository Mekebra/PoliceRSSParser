package com.ekebratt.model;

public enum Months {

	JANUARY("Januari", "01"),
	FEBRUARY("Februari", "02"),
	MARCH("Mars", "03"),
	APRIL("April", "04"),
	MAY("Maj", "05"),
	JUNE("Juni", "06"),
	JULY("Juli", "07"),
	AUGUST("Augusti", "08"),
	SEPTEMBER("September", "09"),
	OCTOBER("Oktober", "10"),
	NOVEMBER("November", "11"),
	DECEMBER("December", "12");

	private String swedish;
	private String english;

	private Months(String swedish, String english) {
		this.swedish = swedish;
		this.english = english;
	}
	public static String fromSwedish(String month) {
		if(month != null) {
			for(Months m : Months.values()) {
				if(m.swedish.toLowerCase().equals(month.toLowerCase())) {
					return m.english;
				}
			}
		}
		return "";

	}
}
