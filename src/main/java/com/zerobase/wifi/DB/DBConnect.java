package com.zerobase.wifi.DB;

import org.apache.ibatis.javassist.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.zerobase.wifi.controller.HomeController;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	static Connection con = null;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	public static void closeConnection() {
		if (con != null)
			try {
				con.close();
				logger.info("connection closed");
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static Connection getConnection() {
		if (con != null)
			closeConnection();

		try {
			logger.info("connection opened");
			Class.forName("org.sqlite.JDBC");
			ClassPathResource resource = new ClassPathResource("WIFI.db");
//			System.out.println("이름:" + resource.getFilename());
			String path = resource.getURI().toString().substring(6).replaceAll("/", "\\\\");
			System.out.println("uri:" + path);
			con = DriverManager.getConnection("jdbc:sqlite:" + path);
			// C:\\Users\\wodud\\Documents\\workspace-sts-3.9.17.RELEASE\\wifi\\src\\main\\resources\\WIFI.db
			con.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
