package com.zerobase.wifi.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zerobase.wifi.DB.DBConnect;
import com.zerobase.wifi.service.APIService;
import com.zerobase.wifi.service.BookmarkService;
import com.zerobase.wifi.service.DBService;
import com.zerobase.wifi.vo.WifiVo_DB;
import com.zerobase.wifi.vo.bookmarkGroupVo;
import com.zerobase.wifi.vo.posVo;
import com.zerobase.wifi.vo.WifiVo_API;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}

	@RequestMapping(value = "/loadFromDB", method = RequestMethod.GET)
	public String loadFromDB(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam double lat, @RequestParam double lnt) throws SQLException {
		ArrayList<WifiVo_DB> wifiList = DBService.getList(lat, lnt);
		model.addAttribute("lat", lat);
		model.addAttribute("lnt", lnt);
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies)
			if (cookie.getName().equals("lat") || cookie.getName().equals("lnt")) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		Cookie cookieA = new Cookie("lat", String.valueOf(lat));
		Cookie cookieN = new Cookie("lnt", String.valueOf(lnt));
		cookieA.setMaxAge(60 * 60);
		cookieN.setMaxAge(60 * 60);
		response.addCookie(cookieA);
		response.addCookie(cookieN);
		model.addAttribute("wifiList", wifiList);
//		DBConnect.closeConnection();
		return "loadedWifi";
	}

	@RequestMapping(value = "/bringAPI", method = RequestMethod.GET)
	public String bring(Model model) throws IOException, ParseException, SQLException {
		List<WifiVo_API> wifis = APIService.getWifiFirst();
		System.out.println("가져온 와이파이수: " + wifis.size());
		List<WifiVo_DB> convertedWifis = DBService.APItoDB(wifis);
		System.out.println("변환된 와이파이수: " + convertedWifis.size());
		DBService.addAll(convertedWifis);
		model.addAttribute("count", wifis.size());
		if (wifis.isEmpty())
			return "bringAPIFailed";
		return "bringAPI";
	}

	@RequestMapping(value = "/history", method = { RequestMethod.GET, RequestMethod.POST })
	public String history(Model model) {
		List<posVo> posList = DBService.getHistory();
		model.addAttribute("posList", posList);
		return "history";
	}

	@RequestMapping(value = "/history/delete", method = RequestMethod.POST)
	public String deleteHistory(Model model, @RequestParam int ID) throws SQLException {
		DBService.deleteHistory(ID);
		return "redirect:/history";
	}

	@RequestMapping(value = "/wifiDetail", method = RequestMethod.GET)
	public String wifiDetail(Model model, @RequestParam String manageID, @RequestParam double lat,
			@RequestParam double lnt) throws SQLException {
		// 와이파이 한개 조회해서 보여주기
		System.out.println(manageID);
		WifiVo_DB wifi = DBService.selectOne(manageID, lat, lnt);
		model.addAttribute("wifi", wifi);
		model.addAttribute("lat", lat);
		model.addAttribute("lnt", lnt);
		// 북마크 추가를 위해 북마크 그룹도 조회
		List<bookmarkGroupVo> groups = BookmarkService.getBookmarkGroups();
		model.addAttribute("groups", groups);
		model.addAttribute("size", groups.size());
		return "wifiDetail";
	}

}
