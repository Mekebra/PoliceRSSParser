package com.ekebratt.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class NominatimModel {

	@JsonProperty(value = "place_id")
	private String place_id;
	@JsonProperty(value = "license")
	private String license;
	@JsonProperty(value = "osm_type")
	private String osm_type;
	@JsonProperty(value = "osm_id")
	private String osm_id;
	@JsonProperty(value = "lat")
	private String lat;
	@JsonProperty(value = "lon")
	private String lon;
	@JsonProperty(value = "display_name")
	private String display_name;
	@JsonProperty(value = "class")
	private String _class;
	@JsonProperty(value = "type")
	private String _type;
	@JsonProperty(value = "importance")
	private String importance;
	@JsonProperty(value = "geojson")
	private Object geojson;

	public NominatimModel() {}

	public NominatimModel(String place_id, String license, String osm_type, String osm_id, String lat, String lon,
			String display_name, String _class, String _type, String importance, Object geojson) {
		super();
		this.place_id = place_id;
		this.license = license;
		this.osm_type = osm_type;
		this.osm_id = osm_id;
		this.lat = lat;
		this.lon = lon;
		this.display_name = display_name;
		this._class = _class;
		this._type = _type;
		this.importance = importance;
		this.geojson = geojson;
	}

	public String getPlace_id() {
		return place_id;
	}
	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getOsm_type() {
		return osm_type;
	}
	public void setOsm_type(String osm_type) {
		this.osm_type = osm_type;
	}
	public String getOsm_id() {
		return osm_id;
	}
	public void setOsm_id(String osm_id) {
		this.osm_id = osm_id;
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
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String get_class() {
		return _class;
	}
	public void set_class(String _class) {
		this._class = _class;
	}
	public String get_type() {
		return _type;
	}
	public void set_type(String _type) {
		this._type = _type;
	}
	public String getImportance() {
		return importance;
	}
	public void setImportance(String importance) {
		this.importance = importance;
	}
	public Object getGeojson() {
		return geojson;
	}
	public void setGeojson(Object geojson) {
		this.geojson = geojson;
	}


}
