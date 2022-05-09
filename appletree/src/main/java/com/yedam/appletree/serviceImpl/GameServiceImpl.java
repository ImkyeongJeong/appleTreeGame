package com.yedam.appletree.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yedam.appletree.Login;
import com.yedam.appletree.dao.DataSource;
import com.yedam.appletree.service.GameService;
import com.yedam.appletree.vo.CharacterVO;

public class GameServiceImpl implements GameService{
	DataSource dao = DataSource.getInstance();
	private Connection conn;
	private PreparedStatement psmt;
	private ResultSet rs;
	CharacterVO vo;
	
	@Override
	public int insertChar(String name) {
		int n = 0;
		String sql = "INSERT INTO character VALUES(?, ?, DEFAULT, DEFAULT)";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginMember.getId());
			psmt.setString(2, name);
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}

	@Override
	public String nameCheck(String name) {
		String checkName = null;
		String sql = "SELECT name FROM character WHERE ID = ?";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, name);
			rs = psmt.executeQuery();
			if(rs.next()) {
				checkName = rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return checkName;
	}

	@Override
	public CharacterVO selectChar(String id) {
		
		return null;
	}

	@Override
	public int updateChar(CharacterVO character) {
		return 0;
	}

	@Override
	public int deleteChar(CharacterVO character) {
		return 0;
	}

	private void close() {
		// 연결순서: conn > psmt > rs
		// 닫는순서: rs > psmt > conn
		try {
			if(rs != null) rs.close();
			if(psmt != null) psmt.close();
			if(conn != null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
