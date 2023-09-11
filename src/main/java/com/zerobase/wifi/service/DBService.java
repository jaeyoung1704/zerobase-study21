package com.zerobase.wifi.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock.Catch;
import org.slf4j.Logger;

import com.zerobase.wifi.DB.DBConnect;
import com.zerobase.wifi.vo.WifiVo_DB;
import com.zerobase.wifi.vo.posVo;
import com.zerobase.wifi.vo.WifiVo_API;

public class DBService {
	static Connection con;

	public static int addAll(List<WifiVo_DB> list) throws SQLException {
		String sql = "INSERT INTO RAW_DATA(MANAGE_ID, DISTRICT, NAME,"
				+ "ADDRESS1, ADDRESS2, INSTALL_TYPE, INSTALL_BY, SERVICE_TYPE, "
				+ "NETWORK_TYPE, YEAR, IN_OUT, LNT, LAT, REFRESH_DATE)"
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int count = 0;
		PreparedStatement pstmt = null;
		try {
			con = DBConnect.getConnection();
			System.out.println("addAll:DB 연결 성공");
			// 기존 테이블 삭제
			pstmt = con.prepareStatement("DELETE FROM RAW_DATA");
			pstmt.executeUpdate();
			con.commit();
			System.out.println("기존테이블 삭제 성공");
			pstmt = con.prepareStatement(sql);
			System.out.println("서버에 " + list.size() + "개 기록 시도");
			for (WifiVo_DB vo : list) {
				++count;
				pstmt.setString(1, vo.getManageID());
				pstmt.setString(2, vo.getDistrict());
				pstmt.setString(3, vo.getName());
				pstmt.setString(4, vo.getAddress1());
				pstmt.setString(5, vo.getAddress2());
				pstmt.setString(6, vo.getInstallType());
				pstmt.setString(7, vo.getInstallBy());
				pstmt.setString(8, vo.getServiceType());
				pstmt.setString(9, vo.getNetworkType());
				pstmt.setString(10, vo.getYear());
				pstmt.setString(11, vo.getInOut());
				pstmt.setDouble(12, vo.getLnt());
				pstmt.setDouble(13, vo.getLat());
				pstmt.setString(14, vo.getRefreshDate());

				pstmt.addBatch();
				pstmt.clearParameters();

				// 100개마다 커밋
				if ((count % 100) == 0) {
					pstmt.executeBatch();
					pstmt.clearBatch();
					con.commit();
				}

			}
			// 마지막 커밋
			pstmt.executeBatch();
			con.commit();
			pstmt.close();
			DBConnect.closeConnection();
			// 오류 발생시 롤백
		} catch (Exception e) {
			con.rollback();
			System.out.println("오류 발생");
			System.out.println(e.getMessage());
			System.out.println(Arrays.toString(e.getStackTrace()));
		} finally {
			if (pstmt != null)
				pstmt.close();
		}
		System.out.println(count + "개 기록 성공");
		if (pstmt != null)
			pstmt.close();
		DBConnect.closeConnection();
		return count;
	}

	public static ArrayList<WifiVo_DB> getList(double myLat, double myLnt) throws SQLException {
		con = DBConnect.getConnection();
		System.out.println("getlist:DB 연결 성공");
		ArrayList<WifiVo_DB> list = new ArrayList<>();
		Statement stmt;
		int count = 0;
		try {
			stmt = con.createStatement();
			String sql = "CREATE TEMPORARY TABLE TEMP AS " + "SELECT " + //
					Math.cos(myLat) * 6371 * Math.PI / 180 + "* ABS(" + myLnt + " - lnt) AS X" + ", 112*ABS(" + myLat
					+ "-lat) AS Y, MANAGE_ID FROM RAW_DATA";
			stmt.executeUpdate(sql);
			con.commit();
			System.out.println("임시 테이블 생성 성공");

			sql = "SELECT X*X+Y*Y AS DISTANCE, *\r\n" //
					+ "FROM TEMP\r\n" //
					+ "JOIN RAW_DATA\r\n"//
					+ "ON TEMP.MANAGE_ID=RAW_DATA.MANAGE_ID\r\n"//
					+ "ORDER BY DISTANCE ASC LIMIT 20;\r\n";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("조회 시작");
			while (rs.next()) {
				WifiVo_DB vo = new WifiVo_DB();
				vo.setDistance((float) Math.sqrt(rs.getFloat("DISTANCE")));
				vo.setManageID(rs.getString("MANAGE_ID"));
				vo.setDistrict(rs.getString("DISTRICT"));
				vo.setName(rs.getString("NAME"));
				vo.setAddress1(rs.getString("ADDRESS1"));
				vo.setAddress2(rs.getString("ADDRESS2"));
				vo.setInstallType(rs.getString("INSTALL_TYPE"));
				vo.setInstallBy(rs.getString("INSTALL_BY"));
				vo.setServiceType(rs.getString("SERVICE_TYPE"));
				vo.setNetworkType(rs.getString("NETWORK_TYPE"));
				vo.setYear(rs.getString("YEAR"));
				vo.setInOut(rs.getString("IN_OUT"));
				vo.setLnt(rs.getFloat("LNT"));
				vo.setLat(rs.getFloat("LAT"));
				vo.setRefreshDate(rs.getString("REFRESH_DATE"));
				list.add(vo);
			} // while 끝
			System.out.println("조회 성공");
			stmt = con.createStatement();
			sql = "DROP TABLE TEMP;";
			stmt.executeUpdate(sql);
			con.commit();
			System.out.println("임시테이블 삭제 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 조회 성공시 현재 위치 기록
		addHistory(myLat, myLnt);
		DBConnect.closeConnection();
		return list;
	}

	static float distance(double myLat, double myLnt, double lat, double lnt) {
		final int R = 6371; // 지구 반지름
		// 경도 1도:cos위도1*반지름*파이/180)
		double x = Math.cos(myLat) * R * Math.PI / 180 * Math.abs(myLnt - lnt);
		// 위도 1도: 약 111.7
		double y = 112 * Math.abs(myLat - lat);
//		System.out.println("myLat:" + myLat + ", myLnt:" + myLnt);
//		System.out.println("Lat:" + lat + ", Lnt:" + lnt);
//		System.out.println("경도 1도 거리:" + Math.cos(myLat) * 6400 * 2 * Math.PI / 360);
//		System.out.println("위도 1도 거리:" + R * 2 * Math.PI / 360);
		float distance = (float) Math.sqrt(x * x + y * y);
		return Math.round(distance * 1000) / 1000f;
	}

	// api에서 받은 wifi정보를 db에 넣기좋게 변형
	public static List<WifiVo_DB> APItoDB(List<WifiVo_API> apiList) throws ParseException {
		List<WifiVo_DB> dbList = new ArrayList<>();
		for (WifiVo_API api : apiList) {
			WifiVo_DB db = new WifiVo_DB();
			db.setManageID(api.getX_SWIFI_MGR_NO());
			db.setName(api.getX_SWIFI_MAIN_NM());
			db.setDistrict(api.getX_SWIFI_WRDOFC());
			db.setAddress1(api.getX_SWIFI_ADRES1());
			db.setAddress2(api.getX_SWIFI_ADRES2());
			db.setInstallType(api.getX_SWIFI_INSTL_TY());
			db.setInstallBy(api.getX_SWIFI_INSTL_MBY());
			db.setServiceType(api.getX_SWIFI_SVC_SE());
			db.setNetworkType(api.getX_SWIFI_CMCWR());
			db.setYear(api.getX_SWIFI_CNSTC_YEAR());
			db.setInOut(api.getX_SWIFI_INOUT_DOOR());
			// api에서 lat lnt 거꾸로 돼어있으므로 반대로 저장
			db.setLnt(api.getLAT());
			db.setLat(api.getLNT());
			db.setRefreshDate(api.getWORK_DTTM());
			dbList.add(db);
		}
		return dbList;
	}

	public static ArrayList<posVo> getHistory() {
		con = DBConnect.getConnection();
		System.out.println("getHistory:DB 연결 성공");
		ArrayList<posVo> list = new ArrayList<>();
		Statement stmt;
		try {
			String sql = "SELECT * FROM HISTORY ORDER BY ID DESC";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("조회 시작");
			while (rs.next()) {
				posVo vo = new posVo();
				vo.setID(rs.getInt("ID"));
				vo.setLnt(rs.getFloat("lnt"));
				vo.setLat(rs.getFloat("lat"));
				vo.setDate(rs.getString("Date"));
				list.add(vo);
			} // while 끝
			stmt = con.createStatement();
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(list.size() + "개 데이터 조회 성공");
		DBConnect.closeConnection();
		return list;
	}

	public static void addHistory(double lat, double lnt) throws SQLException {
		con = DBConnect.getConnection();
		System.out.println("addHistory:DB 연결 성공");
		try {
			String sql = "INSERT INTO HISTORY(LAT, LNT)" + "VALUES(?, ?);";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setDouble(1, lat);
			pstmt.setDouble(2, lnt);
			pstmt.execute();
		} catch (Exception e) {
			System.out.println("오류 발생");
			System.out.println(e.getMessage());
			System.out.println(Arrays.toString(e.getStackTrace()));
		}
		con.commit();
		System.out.println("위치 기록 성공:" + lat + "," + lnt);
		DBConnect.closeConnection();
	}

	public static void deleteHistory(int id) throws SQLException {
		con = DBConnect.getConnection();
		System.out.println("deleteHistory:DB 연결 성공");
		try {
			String sql = "DELETE FROM HISTORY WHERE ID=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setDouble(1, id);
			pstmt.execute();
		} catch (Exception e) {
			System.out.println("오류 발생");
			System.out.println(e.getMessage());
			System.out.println(Arrays.toString(e.getStackTrace()));
		}
		con.commit();
		System.out.println(id + "번 기록 삭제 성공");
		DBConnect.closeConnection();
	}

	public static WifiVo_DB selectOne(String manageID, double myLat, double myLnt) throws SQLException {
		con = DBConnect.getConnection();
		System.out.println("selectOne:DB 연결 성공");
		WifiVo_DB vo = new WifiVo_DB();
		int count = 0;
		try {
			String sql = "SELECT * FROM RAW_DATA WHERE MANAGE_ID=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, manageID);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				// 결과가 없으면 오류 발생
				System.out.println("조회된 결과 없음");
				return null;
			} else {
				vo.setManageID(rs.getString("MANAGE_ID"));
				vo.setDistrict(rs.getString("DISTRICT"));
				vo.setName(rs.getString("NAME"));
				vo.setAddress1(rs.getString("ADDRESS1"));
				vo.setAddress2(rs.getString("ADDRESS2"));
				vo.setInstallType(rs.getString("INSTALL_TYPE"));
				vo.setInstallBy(rs.getString("INSTALL_BY"));
				vo.setServiceType(rs.getString("SERVICE_TYPE"));
				vo.setNetworkType(rs.getString("NETWORK_TYPE"));
				vo.setYear(rs.getString("YEAR"));
				vo.setInOut(rs.getString("IN_OUT"));
				vo.setLnt(rs.getFloat("LNT"));
				vo.setLat(rs.getFloat("LAT"));
				vo.setRefreshDate(rs.getString("REFRESH_DATE"));
				vo.setDistance(distance(myLat, myLnt, vo.getLat(), vo.getLnt()));
			}
		} catch (Exception e) {
			System.out.println("오류 발생");
			System.out.println(e.getMessage());
			System.out.println(Arrays.toString(e.getStackTrace()));
		}
		System.out.println("조회 성공:" + vo);
		DBConnect.closeConnection();
		return vo;
	}
}
