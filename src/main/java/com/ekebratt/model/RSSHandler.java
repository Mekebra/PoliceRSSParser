package com.ekebratt.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class RSSHandler {

	private final URL url;

	public RSSHandler(String url) throws MalformedURLException {
		this.url = new URL(url);
	}
	public List<RSSModel> getRSSFeed() throws IllegalArgumentException, FeedException, IOException, ParseException {
		LocalDate twoWeeksAgo = LocalDate.now().minusWeeks(2);
		List<RSSModel> response = new ArrayList<>();

		List<SyndEntryImpl> entryList = getRSSList();
		for(int i = 0; i < entryList.size(); i++) {

			SyndEntryImpl entry = entryList.get(i);

			LocalDate publishDate = convertToLocalDate(entry.getPublishedDate());

			if(twoWeeksAgo.isBefore(publishDate)) {
				if(entry.getTitle() != null) {
					String entryTitle = entry.getTitle();
					if(entryTitle.contains("Sammanfattning")) {
						Document doc = Jsoup.connect(entry.getLink()).get();

						Elements el = doc.select("div.editorial-html > p");
						for(Element ele : el) {
							try {
								TextNode textNode = (TextNode) ele.childNode(0).childNode(0);
								TextNode summaryText = null;
								try {
									summaryText = (TextNode) ele.childNode(1);
								} catch(ClassCastException e) {
									summaryText = (TextNode) ele.childNode(2);
								}
								String[] timeLocation = textNode.text().split(", ");
								String[] date = entryTitle.split(", ")[0].split(" ");
								String newDateTime = date[0] + " " + date[1] + " " + timeLocation[0];
								String title = Incidents.findIncident(summaryText.text());
								response.add(new RSSModel(
										getDateTime(newDateTime), 
										title, 
										timeLocation[1].split(":")[0], 
										getLocation(summaryText.getWholeText()),
										summaryText.getWholeText(),
										Incidents.findCategory(title)));
							} catch(IndexOutOfBoundsException | ClassCastException e) {
								// Do nothing, the website returned a bad node
							}
						}

					} else if(entryTitle.startsWith("Uppdaterad ")) {
						entryTitle = entryTitle.substring(11);
						entryTitle = entryTitle.replaceAll(": \\d{2} [a-zA-Z]+ \\d{2}:\\d{2}", "");
						addToList(response, entryTitle, entry.getDescription().getValue());
					} else {
						addToList(response, entryTitle, entry.getDescription().getValue());
					}
				}
			}
		}

		return response;
	}

	private LocalDate convertToLocalDate(Date dateToConvert) {
		return Instant.ofEpochMilli(dateToConvert.getTime())
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
	}
	
	private void addToList(List<RSSModel> response, String title, String description) throws ParseException {
		if(title.length() > 0) {
			response.add(new RSSModel(
					getDateTime(title.split(",")[0]), 
					getIncident(title), 
					getCity(title), 
					getLocation(description),
					description,
					Incidents.findCategory(title)));
		}
	}
	private LocalDateTime getDateTime(String dateString) throws ParseException {

		LocalDateTime date;
		dateString = dateString.replace(".", ":");
		String[] dateArr = dateString.split(" ");
		String day = dateArr[0];
		String month = Months.fromSwedish(dateArr[1]);
		String time = dateArr[2];
		date = LocalDateTime.parse(
				Calendar.getInstance(Locale.ENGLISH).get(Calendar.YEAR) 
				+ "-"
				+ month
				+ "-"
				+ day
				+ "T"
				+ time);

		return date;

	}
	private String getIncident(String title) {
		String parsedTitle = "";
		String[] titleArr = title.split(", ");
		if(titleArr.length == 3) {
			parsedTitle = titleArr[1];
		} else {
			int arrSize = titleArr.length - 1;
			for(int i = 1; i < arrSize; i++) {
				if(i < arrSize) {
					parsedTitle += titleArr[i];
					if(i < arrSize -1) {
						parsedTitle += ", ";
					}
				}
			}

		}
		return parsedTitle;
	}
	private String getCity(String title) {
		String[] titleArr = title.split(", ");
		return titleArr[titleArr.length-1];
	}
	private String getLocation(String description) {
		String newDescription = description.replaceAll("\\. {1}[A-ZÅÄÖ][a-zåäö]{1,}", "");
		newDescription = newDescription.replaceAll("\\. {1}[A-ZÅÄÖ][a-zåäö]{1,}", "");
		Pattern patternMatcher = Pattern.compile("[A-ZÅÄÖ1-9][a-zåäö1-9]{1,}");
		Matcher m = patternMatcher.matcher(newDescription);
		String matchedLocations = "";
		boolean first = true;
		while (m.find()) {
			if(!first) {
				matchedLocations += m.group() + " ";
			} else {
				first = false;
			}
		}
		return matchedLocations;
	}

	private List<SyndEntryImpl> getRSSList() throws IllegalArgumentException, FeedException, IOException {

		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = input.build(new XmlReader(url));
		@SuppressWarnings("unchecked")
		List<SyndEntryImpl> entries = feed.getEntries();

		return entries;

	}
}
