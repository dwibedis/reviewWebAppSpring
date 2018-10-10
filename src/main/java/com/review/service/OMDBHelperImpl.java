package com.review.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Service;

@Service
public class OMDBHelperImpl implements OMDBHelper {

	private static final String API_KEY = "fc7fd464";
	private static final String URL = "http://www.omdbapi.com/?t=%s&apikey=%s";

	public JSONObject fetchMovieDetails(String movieName) {
		String url = String.format(URL, URLEncoder.encode(movieName), API_KEY);
		try {
			return new JSONObject(readUrl(url));
		} catch (JSONException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	private String readUrl(String urlString) throws IOException {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null)
				reader.close();
		}
	}

}
