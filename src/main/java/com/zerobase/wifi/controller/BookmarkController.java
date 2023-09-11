package com.zerobase.wifi.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zerobase.wifi.service.BookmarkService;
import com.zerobase.wifi.vo.bookmarkGroupVo;
import com.zerobase.wifi.vo.bookmarkVo;

@Controller
@RequestMapping(value = "/bookmark")
public class BookmarkController {

	private static final Logger logger = LoggerFactory.getLogger(BookmarkController.class);

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String bookmark(Model model) throws SQLException {
		List<bookmarkVo> bookmarks = BookmarkService.getBookmarks();
		model.addAttribute("bookmarks", bookmarks);
		return "bookmark/list";
	}

	@RequestMapping(value = "/groupList", method = RequestMethod.GET)
	public String bookmarkGroup(Model model) throws SQLException {
		List<bookmarkGroupVo> groups = BookmarkService.getBookmarkGroups();
		model.addAttribute("groups", groups);
		return "bookmark/groupList";
	}

	@RequestMapping(value = "/list/delete", method = RequestMethod.GET)
	public String deleteCheckBookmark(Model model, @RequestParam int id, @RequestParam String wifiName,
			@RequestParam String groupName, @RequestParam String date) throws SQLException {
		model.addAttribute("id", id);
		model.addAttribute("wifiName", wifiName);
		model.addAttribute("groupName", groupName);
		model.addAttribute("date", date);
		return "bookmark/delete";
	}

	@RequestMapping(value = "/list/delete.do", method = RequestMethod.POST)
	public String deleteBookmark(Model model, @RequestParam int id) throws SQLException {
		BookmarkService.deleteBookmark(id);
		return "redirect:/bookmark/list";
	}

	@RequestMapping(value = "/groupList/delete", method = RequestMethod.GET)
	public String deleteCheckGroup(Model model, @RequestParam int id, @RequestParam String name,
			@RequestParam int order) throws SQLException {
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("order", order);
		return "bookmark/deleteGroup";
	}

	@RequestMapping(value = "/groupList/delete.do", method = RequestMethod.POST)
	public String deleteGroup(Model model, @RequestParam int id) throws SQLException {
		BookmarkService.deleteGroup(id);
		return "redirect:/bookmark/groupList";
	}

	@RequestMapping(value = "/groupList/edit", method = RequestMethod.GET)
	public String editCheckGroup(Model model, @RequestParam int id, @RequestParam String name, @RequestParam int order)
			throws SQLException {
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("order", order);
		return "bookmark/editGroup";
	}

	@RequestMapping(value = "/groupList/edit.do", method = RequestMethod.POST)
	public String editGroup(Model model, @RequestParam int id, @RequestParam String oldName,
			@RequestParam String newName, @RequestParam int order) throws SQLException, UnsupportedEncodingException {
		oldName = new String(oldName.getBytes("8859_1"), "UTF-8");
		newName = new String(newName.getBytes("8859_1"), "UTF-8");
		System.out.println("old name:" + oldName + "new name:" + newName);

		boolean availableName = BookmarkService.isAvailableName(oldName, newName);
		if (!availableName) {
			model.addAttribute("msg", "해당 이름이 이미 존재합니다");
			String URI = String.format("/bookmark/groupList/edit?id=%d&name=%s&order=%d", id, newName, order);
			System.out.println(URI);
			model.addAttribute("url", URI);
			return "alert";
		}
		boolean availableOrder = BookmarkService.isAvailableOrder(id, order);
		if (availableOrder)
			BookmarkService.editGroup(id, newName, order);
		else {
			model.addAttribute("msg", "해당 순서가 이미 존재합니다");
			String URI = String.format("/bookmark/groupList/edit?id=%d&name=%s&order=%d", id, newName, order);
			System.out.println(URI);
			model.addAttribute("url", URI);
			return "alert";
		}
		return "redirect:/bookmark/groupList";
	}

	@RequestMapping(value = "/groupList/add", method = RequestMethod.GET)
	public String addCheckGroup(Model model) throws SQLException {
		return "bookmark/addGroup";
	}

	@RequestMapping(value = "/groupList/add.do", method = RequestMethod.POST)
	public String addGroup(Model model, @RequestParam String name, @RequestParam int order)
			throws SQLException, UnsupportedEncodingException {
		name = new String(name.getBytes("8859_1"), "UTF-8");
		boolean availableName = BookmarkService.isAvailableName(name);
		if (!availableName) {
			model.addAttribute("msg", "해당 이름이 이미 존재합니다");
			String URI = "/bookmark/groupList/add";
			System.out.println(URI);
			model.addAttribute("url", URI);
			return "alert";
		}
		boolean availableOrder = BookmarkService.isAvailableOrder(order);
		if (availableOrder)
			BookmarkService.addGroup(name, order);
		else {
			model.addAttribute("msg", "해당 순서가 이미 존재합니다");
			String URI = "/bookmark/groupList/add";
			System.out.println(URI);
			model.addAttribute("url", URI);
			return "alert";
		}
		return "redirect:/bookmark/groupList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addBookmark(Model model, @RequestParam int groupId, @RequestParam String manageId,
			@RequestParam double lat, @RequestParam double lnt) throws SQLException, UnsupportedEncodingException {
		manageId = new String(manageId.getBytes("8859_1"), "UTF-8");
		boolean available = BookmarkService.isAvailableBookmark(groupId, manageId);
		if (!available) {
			String URI = String.format("../wifiDetail?manageID=%s&lat=%f&lnt=%f", manageId, lat, lnt);
			String msg = "해당 북마크는 이미 해당 그룹에 있습니다";
			model.addAttribute("url", URI);
			model.addAttribute("msg", msg);
			return "alert";
		}
		BookmarkService.addBookmark(groupId, manageId);
		String URI = String.format("../wifiDetail?manageID=%s&lat=%f&lnt=%f", manageId, lat, lnt);
		String msg = "북마크를 추가했습니다";
		model.addAttribute("url", URI);
		model.addAttribute("msg", msg);
		return "alert";
	}
}
