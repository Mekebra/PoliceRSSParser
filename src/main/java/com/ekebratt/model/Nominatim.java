package com.ekebratt.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Nominatim {

	private static final Logger log = LoggerFactory.getLogger(Nominatim.class);

	public NominatimModel[] searchNominatim(String searchString) throws IOException, InterruptedException {
		Thread.sleep(1000); // Nominatim enforces a 1req/sec rule
		NominatimModel[] nmArr = {};
		try {
			String encodedString = URLEncoder.encode(searchString + ", Skåne, Sweden", StandardCharsets.UTF_8.toString());
			URL u = new URL("https://nominatim.openstreetmap.org/search.php?q=" +encodedString+"&viewbox=&format=json");
			String result = "";
			URLConnection urlConn = u.openConnection();
			urlConn.setRequestProperty("HTTP Referer", "http://localhost");
			urlConn.setRequestProperty("User-Agent", "SWEPoliceRSSReader/0.10");
			BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null)
				result+= inputLine;
			in.close();
			ObjectMapper objectMapper = new ObjectMapper();
			nmArr = objectMapper.readValue(result, NominatimModel[].class);
		} catch(Exception e) {
			log.error("Nominatim Failed!", e);
		}
		
		return nmArr;
	}

	public List<RSSModel> readObjectFromFile() throws JsonParseException, JsonMappingException, IOException {

		List<RSSModel> result = new ArrayList<>();

		try {
			ObjectMapper om = new ObjectMapper();
			result = om.readValue(new File("/Users/michaelekebratt/Documents/test.json"), new TypeReference<List<RSSModel>>(){});
		} catch(Exception e) {
			log.error("Read Error", e);
		}
		return result;
	}
	public void writeObjectToFile(List<RSSModel> serObj) {

		try {
			new ObjectMapper().writeValue(new File("/Users/michaelekebratt/Documents/test.json"), serObj);

		} catch (Exception e) {
			log.error("Write Error", e);
		}
	}
}
