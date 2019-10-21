package com.ekebratt.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Incidents {

	ROBBERY("rån", "Rån", 1),
	THREAT("hot", "Olaga hot", 1),
	WEAPON("vapen", "Vapenlagen", 1),
	MURDER("mord", "Mord/dråp", 1),
	ASSAULT("misshandel", "Misshandel", 1),
	ASSAULT1("stick", "Misshandel", 1),
	ASSAULT2("skär", "Misshandel", 1),
	ASSAULT3("bråk", "Misshandel", 1),
	DAMAGE("skadegör", "Skadegörelse", 2),
	BURGLARY("inbrott", "Inbrott", 2),
	DETONATION("deton", "Detonation", 2),
	EXPLOSION("explo", "Explosion", 2),
	THEF("stöld", "Stöld", 2),
	DUI("rattf", "Rattfylla", 2),
	NARCOTICS("narkotika", "Narkotika", 3),
	COLLISION("kollision", "Trafikolycka", 3),
	FIRE("brand", "Brand", 3),
	ACCIDENT("olyck", "Olycksfall", 3),
	CARACCIDENT("trafikolycka", "Trafikolycka", 3),
	CAR("bil", "Trafikolycka", 3),
	TRAFFIC("trafikbrott", "Trafikbrott", 3),
	MISSING("försvunnen person", "Försvunnen person", 3),
	DEATH("avlid", "Avliden", 3);

	private static final Logger log = LoggerFactory.getLogger(Incidents.class);
	private Incidents(String text, String incident, int category) {
		this.text = text;
		this.incident = incident;
		this.category = category;
	}
	
	private String text;
	private String incident;
	private int category;
	
	public static int findCategory(String inc) {
		for(Incidents in : Incidents.values()) {
			if(inc.toLowerCase().contains(in.text)) {
				log.info("Returning category " + in.category + " for text [" + inc + "]");
				return in.category;
			}
		}
		log.info("Returning category (3) for text [" + inc + "]");
		return 3;
	}
	public static String findIncident(String description) {
		for(Incidents in : Incidents.values()) {
			if(description.toLowerCase().contains(in.text)) {
				return in.incident;
			}
		}
		return "Unknown";
	}
}
