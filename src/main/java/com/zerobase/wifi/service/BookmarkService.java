package com.zerobase.wifi.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.zerobase.wifi.DB.DBConnect;
import com.zerobase.wifi.vo.bookmarkGroupVo;
import com.zerobase.wifi.vo.bookmarkVo;

public class BookmarkService {
	static Connection con;

	public static List<bookmarkVo> getBookmarks() throws SQLException {
		con = DBConnect.getConnection();
		System.out.println("getBookmarks:DB 연결 성공");
		ArrayList<bookmarkVo> list = new ArrayList<>();
		Statement stmt;
		int count = 0;
		try {
			String sql = "SELECT * FROM VIEW_BOOKMARK";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("조회 시작");
			while (rs.next()) {
				bookmarkVo vo = new bookmarkVo();
				vo.setId(rs.getInt("ID"));
				vo.setGroupName(rs.getString("GROUP_NAME"));
				vo.setWifiName(rs.getString("WIFI_NAME"));
				vo.setDate(rs.getString("DATE"));
				list.add(vo);
			} // while 끝
			System.out.println("조회 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBConnect.closeConnection();
		return list;
	}

	public static void deleteBookmark(int id) throws SQLException {
		con = DBConnect.getConnection();
		System.out.println("deleteBookmark:DB 연결 성공");
		try {
			String sql = "DELETE FROM BOOKMARK WHERE ID=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.execute();
		} catch (Exception e) {
			System.out.println("오류 발생");
			System.out.println(e.getMessage());
			System.out.println(Arrays.toString(e.getStackTrace()));
		}
		con.commit();
		System.out.println(id + "번 그룹 삭제 성공");
		DBConnect.closeConnection();
	}

	public static void editGroup(int id, String name, int order) throws SQLException {
		con = DBConnect.getConnection();
		System.out.println("editGroup:DB 연결 성공");
		try {
			String sql = "UPDATE BOOKMARK_GROUP SET NAME=?, ORDERS=?, EDIT_DATE=CURRENT_TIMESTAMP  WHERE ID=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, order);
			pstmt.setInt(3, id);
			pstmt.execute();
		} catch (Exception e) {
			System.out.println("오류 발생");
			System.out.println(e.getMessage());
			System.out.println(Arrays.toString(e.getStackTrace()));
		}
		con.commit();
		System.out.println(name + "그룹 수정 성공");
		DBConnect.closeConnection();
	}

	public static List<bookmarkGroupVo> getBookmarkGroups() throws SQLException {
		con = DBConnect.getConnection();
		System.out.println("getbookmarkGroups:DB 연결 성공");
		ArrayList<bookmarkGroupVo> list = new ArrayList<>();
		Statement stmt;
		int count = 0;
		try {
			String sql = "SELECT * FROM BOOKMARK_GROUP ORDER BY ORDERS ASC";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("조회 시작");
			while (rs.next()) {
				bookmarkGroupVo vo = new bookmarkGroupVo();
				vo.setId(rs.getInt("ID"));
				vo.setName(rs.getString("NAME"));
				vo.setOrder(rs.getInt("ORDERS"));
				vo.setAddDate(rs.getString("ADD_DATE"));
				vo.setEditDate(rs.getString("EDIT_DATE"));
				list.add(vo);
			} // while 끝
			System.out.println("조회 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBConnect.closeConnection();
		return list;
	}

	public static void deleteGroup(int id) throws SQLException {
		con = DBConnect.getConnection();
		System.out.println("DB 연결 성공");
		// 그룹 삭제하기전 해당 그룹 소속 북마크 전부 제거
		try {
			String sql = "DELETE FROM BOOKMARK WHERE GROUP_ID=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.execute();
		} catch (Exception e) {
			System.out.println("오류 발생");
			System.out.println(e.getMessage());
			System.out.println(Arrays.toString(e.getStackTrace()));
		}
		con.commit();
		System.out.println(id + "번 그룹에 속한 북마크 전체 삭제 성공");

		try {
			String sql = "DELETE FROM BOOKMARK_GROUP WHERE ID=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.execute();
		} catch (Exception e) {
			System.out.println("오류 발생");
			System.out.println(e.getMessage());
			System.out.println(Arrays.toString(e.getStackTrace()));
		}
		con.commit();
		System.out.println(id + "번 그룹 삭제 성공");
		DBConnect.closeConnection();
	}

	// 입력한 순서가 (현재 자기 순서를 제외하고) 이미 데이터베이스에 있는지 알려주는 메소드
	public static boolean isAvailableOrder(int myId, int myOrder) throws SQLException {
		con = DBConnect.getConnection();
		System.out.println("isAvailableOrder:DB 연결 성공");
		Set<Integer> set = new HashSet<>();
		Statement stmt;
		int count = 0;
		try {
			String sql = "SELECT ID, ORDERS FROM BOOKMARK_GROUP";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("조회 시작");
			while (rs.next()) {
				int id = rs.getInt("ID");
				int order = rs.getInt("ORDERS");
				if (id != myId)
					set.add(order);
			} // while 끝
			System.out.println("조회 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBConnect.closeConnection();
		if (set.contains(myOrder))
			return false;
		return true;
	}

	// 추가하려는 북마크가 이미 같은 그룹에 있으면 안되므로 알려주는 메소드
	public static boolean isAvailableBookmark(int groupId, String manageId) throws SQLException {
		con = DBConnect.getConnection();
		System.out.println("isAvailableBookmark:DB 연결 성공");
		Set<String> set = new HashSet<>();
		int count = 0;
		try {
			String sql = "SELECT MANAGE_ID FROM BOOKMARK WHERE GROUP_ID=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, groupId);
			ResultSet rs = pstmt.executeQuery();
			System.out.println("조회 시작");
			while (rs.next()) {
				set.add(rs.getString("MANAGE_ID"));
			} // while 끝
			System.out.println("조회 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBConnect.closeConnection();
		System.out.println("set:" + set);
		if (set.contains(manageId))
			return false;
		return true;
	}

	// 추가할땐 전체를 조회
	public static boolean isAvailableOrder(int myOrder) throws SQLException {
		con = DBConnect.getConnection();
		System.out.println("isAvailableOrder:DB 연결 성공");
		Set<Integer> set = new HashSet<>();
		Statement stmt;
		int count = 0;
		try {
			String sql = "SELECT ORDERS FROM BOOKMARK_GROUP";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("조회 시작");
			while (rs.next()) {
				int order = rs.getInt("ORDERS");
				set.add(order);
			} // while 끝
			System.out.println("조회 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBConnect.closeConnection();
		if (set.contains(myOrder))
			return false;
		return true;
	}

	public static boolean isAvailableName(String oldName, String newName) throws SQLException {
		con = DBConnect.getConnection();
		System.out.println("isAvailableName:DB 연결 성공");
		Set<String> set = new HashSet<>();
		Statement stmt;
		int count = 0;
		try {
			String sql = "SELECT NAME FROM BOOKMARK_GROUP";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("조회 시작");
			while (rs.next()) {
				String name = rs.getString("NAME");
				if (!name.equals(oldName)) // 원래 이름은 안바뀌어도 됨
					set.add(name);
			} // while 끝
			System.out.println("조회 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBConnect.closeConnection();
		System.out.println("set:" + set);
		// 바꾸기 전 이름과 후 이름이 다르고, 후 이름이 데이터베이스에 있으면 불가능, 그이외는 가능
		if (!oldName.equals(newName) & set.contains(newName))
			return false;
		return true;
	}

	public static boolean isAvailableName(String myName) throws SQLException {
		con = DBConnect.getConnection();
		System.out.println("isAvailableName:DB 연결 성공");
		Set<String> set = new HashSet<>();
		Statement stmt;
		int count = 0;
		try {
			String sql = "SELECT NAME FROM BOOKMARK_GROUP";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("조회 시작");
			while (rs.next()) {
				String name = rs.getString("NAME");
				set.add(name);
			} // while 끝
			System.out.println("조회 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBConnect.closeConnection();
		System.out.println("set:" + set);
		if (set.contains(myName))
			return false;
		return true;
	}

	public static void addGroup(String name, int order) throws SQLException {
		con = DBConnect.getConnection();
		System.out.println("addGroup:DB 연결 성공");
		try {
			String sql = "INSERT INTO BOOKMARK_GROUP(NAME,ORDERS)" + "VALUES(?, ?);";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, order);
			pstmt.execute();
		} catch (Exception e) {
			System.out.println("오류 발생");
			System.out.println(e.getMessage());
			System.out.println(Arrays.toString(e.getStackTrace()));
		}
		con.commit();
		System.out.println("그룹 추가 성공");
		DBConnect.closeConnection();
	}

	public static void addBookmark(int groupId, String manageId) throws SQLException {
		con = DBConnect.getConnection();
		System.out.println("addGroup:DB 연결 성공");
		try {
			String sql = "INSERT INTO BOOKMARK(GROUP_ID,MANAGE_ID)" + "VALUES(?, ?);";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, groupId);
			pstmt.setString(2, manageId);
			pstmt.execute();
		} catch (Exception e) {
			System.out.println("오류 발생");
			System.out.println(e.getMessage());
			System.out.println(Arrays.toString(e.getStackTrace()));
		}
		con.commit();
		System.out.println("그룹 추가 성공");
		DBConnect.closeConnection();
	}
}
