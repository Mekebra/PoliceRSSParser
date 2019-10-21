package com.ekebratt.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ekebratt.model.Nominatim;
import com.ekebratt.model.NominatimModel;
import com.ekebratt.model.RSSHandler;
import com.ekebratt.model.RSSModel;

@Controller
public class RSSController {
	private static final Logger log = Logger.getLogger(RSSController.class);
	private static final Double TOP_LEFT_LAT = 55.7331;
	private static final Double TOP_LEFT_LON = 12.7798;
	private static final Double BOT_RIGHT_LAT = 55.5293;
	private static final Double BOT_RIGHT_LON = 13.1826;

	@RequestMapping("/search")
	public String search(Model model) {
		String response = "index";
		try {

			RSSHandler handler = new RSSHandler("https://polisen.se/aktuellt/rss/skane/handelser-rss---skane/");

			Nominatim nomi = new Nominatim();
			List<RSSModel> rssFeedList = handler.getRSSFeed();
			List<RSSModel> savedList = nomi.readObjectFromFile();
			List<RSSModel> rssList = new ArrayList<>();
			for(RSSModel rssModel : rssFeedList) {
				boolean match = false;
				for(RSSModel rssModel1 : savedList) {
					if(rssModel.getDate().isEqual(rssModel1.getDate())) {
						match = true;
						break;
					}
				}
				if(!match) {
					rssList.add(rssModel);
				}
			}
			List<RSSModel> rssFinalList = new ArrayList<>();
			LocalDateTime twoWeeksAgo = LocalDateTime.now().minus(2, ChronoUnit.WEEKS);
			for(int i = 0; i < rssList.size(); i++) {

				NominatimModel[] nm = nomi.searchNominatim(rssList.get(i).getLocation() + ", " + rssList.get(i).getCity());

				if(nm != null && nm.length > 0) {

					int substringLatLength = 6;
					int substringLonLength = 6;
					if(nm[0].getLat().length() < 6) {
						substringLatLength = nm[0].getLat().length();
					}
					if(nm[0].getLon().length() < 6) {
						substringLonLength = nm[0].getLon().length();
					}
					String lati = nm[0].getLat().substring(0, substringLatLength);
					String longi = nm[0].getLon().substring(0, substringLonLength);
					Double lat = Double.valueOf(lati);
					Double lon = Double.valueOf(longi);
					if(rssList.get(i).getDate().isAfter(twoWeeksAgo)) {
						if(lat < TOP_LEFT_LAT && lon > TOP_LEFT_LON && lat > BOT_RIGHT_LAT && lon < BOT_RIGHT_LON) {

							rssList.get(i).setLat(lati+i);
							rssList.get(i).setLon(longi+i);

						} else {
							rssList.get(i).setLat(null);
							rssList.get(i).setLon(null);
						}
					}
					rssFinalList.add(rssList.get(i));
				}
			}
			rssFinalList.addAll(savedList);
			nomi.writeObjectToFile(rssFinalList);
			model.addAttribute("nomis", rssFinalList);
		} catch (Exception e) {
			log.error("Unhandled Exception!", e);

		}
		return response;
	}
}
