package library.DAO;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

@Service
public class reCAPTCHA {

	public JSONObject getJSONResponse(String gRecaptchaResponse) {
		String url = "https://www.google.com/recaptcha/api/siteverify";
		String secretKey = "6LctdVYiAAAAAOZQ5nBD4kg90QPId5W9SW9iIliX";

		String response = getResponse(url, secretKey, gRecaptchaResponse);
		JSONObject json = getJSONObject(response);

		return json;
	}

	public JSONObject getJSONObject(String jsonString) {
		JSONObject json = new JSONObject();

		try {
			JSONParser parser = new JSONParser();
			json = (JSONObject)parser.parse(jsonString);
			System.out.println("json: " + json.toJSONString());

		} catch (Exception e) {

		}

		return json;
	}

	public String getResponse(String url, String secretKey, String gRecaptchaResponse) {
		String response = "";

		try {
			URL urlObject = new URL(url);
			HttpsURLConnection connection = (HttpsURLConnection) urlObject.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			String param = "secret=" + secretKey + "&response=" + gRecaptchaResponse;

			DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
			stream.writeBytes(param);
			stream.flush();
			stream.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;

			while ((inputLine = reader.readLine()) != null) {
				response += inputLine;
			}
			reader.close();

		} catch (Exception e) {

		}

		return response;
	}

}
