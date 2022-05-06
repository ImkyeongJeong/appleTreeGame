package com.yedam.appletree.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
	private static DataSource datasource = new DataSource();
	private DataSource() {};
	public static DataSource getInstance() {
		return datasource;
	}
	
	private Connection conn;
	private String driver;
	private String url;
	private String user;
	private String password;
	
	public Connection getConnection() {
		dbConfig();
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	private void dbConfig() {
		Properties properties = new Properties();
		String resources = getClass().getResource("/db.properties").getPath();
		try {
			properties.load(new FileInputStream(resources));
			driver = properties.getProperty("driver");
			url = properties.getProperty("url");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
