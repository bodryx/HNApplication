package net.ddns.hnmirror.readload;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HackerNewsReader {
	private  JSONParser jsonParser = new JSONParser();
	public HackerNewsReader() {
		
	}
	
	private String getResponseStr(String urlStrRequest) {
		String urlStr, resStr = "";
		String urlPrefix = "https://hacker-news.firebaseio.com/v0/", urlSuffix = ".json?print=pretty";
		urlStr = urlPrefix + urlStrRequest + urlSuffix;
		try {

			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
		
			while ((output = br.readLine()) != null) {
				resStr = resStr + output + "\n";
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return resStr;
	}

	public JSONObject getStory(long id) throws ParseException { // get item id
		return (JSONObject) jsonParser.parse(getResponseStr("item/" + id));
	}

	public JSONArray getNewStories() throws ParseException { // 500
		return (JSONArray) jsonParser.parse(getResponseStr("newstories"));
	}

	public JSONArray getTopStories() throws ParseException { // 500
		return (JSONArray) jsonParser.parse(getResponseStr("topstories"));
	}

	public JSONArray getAskStories() throws ParseException { // Up to 200 of the latest
		return (JSONArray) jsonParser.parse(getResponseStr("askstories"));
	}

	public JSONArray getShowStories() throws ParseException { // Up to 200 of the latest
		return (JSONArray) jsonParser.parse(getResponseStr("showstories"));
	}

	public JSONArray getJobStories() throws ParseException { // Up to 200 of the latest
		return (JSONArray) jsonParser.parse(getResponseStr("jobstories"));
	}

	public JSONArray getUpdatesStories() throws ParseException { // The item and profile changed
		return (JSONArray) jsonParser.parse(getResponseStr("updates"));		
	}

	public int getMaxItem() { // The current largest item id
	    try
	    {
	      int maxItem = Integer.parseInt(getResponseStr("maxitem").trim());
	      return  maxItem;
	    }
	    catch (NumberFormatException nfe)
	    {
	      System.out.println("NumberFormatException: " + nfe.getMessage());
	    }
		return 0; 
	}

	
	
}
