package com.zerobase.wifi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.zerobase.wifi.DB.DBConnect;
import com.zerobase.wifi.vo.WifiVo_API;

public class APIService {
	// 맨처음 최대 천개까지 정보 받아오기
	public static List<WifiVo_API> getWifiFirst() throws IOException {
		URL url = new URL(
				"http://openapi.seoul.go.kr:8088/685a426a7a776f643731674f4d4759/json/TbPublicWifiInfo/1/1000/");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-type", "application/xml");
		int rpCode = con.getResponseCode();
		System.out.println("Response code:" + rpCode);
		BufferedReader rd;

		// 응답코드가 정상이면 200~300사이
		if (rpCode >= 200 && rpCode < 300)
			rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
		else
			rd = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null)
			sb.append(new String(line.getBytes("UTF-8"), "UTF-8"));
		rd.close();
		con.disconnect();
		JsonParser parser = new JsonParser();
		JsonObject root = ((JsonObject) parser.parse(sb.toString())).get("TbPublicWifiInfo").getAsJsonObject();
		// 와이파이 총 갯수
		int max = root.get("list_total_count").getAsInt();
		System.out.println("API 와이파이 갯수:" + max);
		JsonArray item = root.get("row").getAsJsonArray();
		Gson gson = new Gson();
		List<WifiVo_API> wifis = gson.fromJson(item.toString(), new TypeToken<List<WifiVo_API>>() {
		}.getType());
		// 얻은 와이파이 갯수가 처음에 알아낸 총 갯수가 될때까지 계속 조회
		int index = 1;
		while (index * 1000 < max) {
			getWifi(wifis, index++, max);
		}
		System.out.println("받은 와이파이리스트 size:" + wifis.size());
		System.out.println(wifis.get(0));
		DBConnect.closeConnection();
		return wifis;
	}

	// 반복페이지 작업

	public static void getWifi(List<WifiVo_API> wifis, int index, int max) throws IOException {
//		System.out.print(index + "번째 페이지");
		int start = index * 1000 + 1;
		int end = Math.min(start + 999, max);
//		System.out.println(":" + start + "~" + end);
		URL url = new URL("http://openapi.seoul.go.kr:8088/685a426a7a776f643731674f4d4759/json/TbPublicWifiInfo/"
				+ start + "/" + end);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-type", "application/xml");
		int rpCode = con.getResponseCode();
//		System.out.println("Response code:" + rpCode);
		BufferedReader rd;

		// 응답코드가 정상이면 200~300사이
		if (rpCode >= 200 && rpCode < 300)
			rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
		else
			rd = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null)
			sb.append(line);
		rd.close();
		con.disconnect();
		JsonParser parser = new JsonParser();
		JsonObject root = ((JsonObject) parser.parse(sb.toString())).get("TbPublicWifiInfo").getAsJsonObject();
		Gson gson = new Gson();
		JsonArray item = root.get("row").getAsJsonArray();
		wifis.addAll(gson.fromJson(item.toString(), new TypeToken<List<WifiVo_API>>() {
		}.getType()));
	}

}
