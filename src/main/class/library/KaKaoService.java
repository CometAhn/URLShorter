package library;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class KaKaoService { public String getToken(String code) throws IOException {
	// 인가코드로 토큰받기
	String host = "https://kauth.kakao.com/oauth/token";
	URL url = new URL(host);
	HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	String token = "";
	try {
		urlConnection.setRequestMethod("POST");
		urlConnection.setDoOutput(true); // 데이터 기록 알려주기

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
		StringBuilder sb = new StringBuilder();
		sb.append("grant_type=authorization_code");
		sb.append("&client_id=e6f60f053eb85f6437f9f7e99973580c");
		sb.append("&redirect_uri=http://localhost/Lib/kakao");
		sb.append("&code=" + code);

		bw.write(sb.toString());
		bw.flush();

		int responseCode = urlConnection.getResponseCode();
		System.out.println("responseCode = " + responseCode);

		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String line = "";
		String result = "";
		while ((line = br.readLine()) != null) {
			result += line;
		}
		System.out.println("result = " + result);

		// json parsing
		JSONParser parser = new JSONParser();
		JSONObject elem = (JSONObject) parser.parse(result);

		String access_token = elem.get("access_token").toString();
		String refresh_token = elem.get("refresh_token").toString();
		System.out.println("refresh_token = " + refresh_token);
		System.out.println("access_token = " + access_token);

		token = access_token;

		br.close();
		bw.close();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}


	return token;
}


	public HashMap<String, Object> getUserInfo (String access_Token) {

		//    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
		HashMap<String, Object> userInfo = new HashMap<>();
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			//    요청에 필요한 Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer " + access_Token);

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
			JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

			String nickname = properties.getAsJsonObject().get("nickname").getAsString();
			String profile_image = properties.getAsJsonObject().get("profile_image").getAsString();
			//String email = kakao_account.getAsJsonObject().get("email").getAsString();

			userInfo.put("nickname", nickname);
			//userInfo.put("email", email);
			userInfo.put("profile_image", profile_image);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userInfo;
	}

	public String getAgreementInfo(String access_token)
	{
		String result = "";
		String host = "https://kapi.kakao.com/v2/user/scopes";
		try{
			URL url = new URL(host);
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Authorization", "Bearer "+access_token);

			BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line = "";
			while((line=br.readLine())!=null)
			{
				result+=line;
			}

			int responseCode = urlConnection.getResponseCode();
			System.out.println("responseCode = " + responseCode);

			// result is json format
			br.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
