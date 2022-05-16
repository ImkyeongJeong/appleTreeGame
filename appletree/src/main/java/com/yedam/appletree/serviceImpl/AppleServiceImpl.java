package com.yedam.appletree.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.appletree.Login;
import com.yedam.appletree.dao.DataSource;
import com.yedam.appletree.service.AppleService;
import com.yedam.appletree.vo.AppleVO;

public class AppleServiceImpl implements AppleService{
	DataSource dao = DataSource.getInstance();
	private Connection conn;
	private PreparedStatement psmt;
	private ResultSet rs;
	AppleVO vo = new AppleVO();
	
	@Override
	public int insertTree(int num) {
		int n = 0;
		String sql = "INSERT INTO APPLETREE VALUES(?, ?, DEFAULT, DEFAULT, DEFAULT)";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginCharacter.getName());
			psmt.setInt(2, num);
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}

	@Override
	public AppleVO selectApple(int num) {
		AppleVO vo = new AppleVO();
		String sql = "SELECT * FROM APPLETREE WHERE c_name = ? AND NUM = ?";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginCharacter.getName());
			psmt.setInt(2, num);
			rs = psmt.executeQuery();
			if(rs.next()) {
				vo.setNum(rs.getInt("num"));
				vo.setWater(rs.getInt("water"));
				vo.setNutrients(rs.getInt("nutrients"));
				vo.setPruning(rs.getInt("pruning"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return vo;
	}
	
	@Override
	public List<AppleVO> selectAppleList() {
		List<AppleVO> apples  = new ArrayList<AppleVO>();
		String sql = "SELECT * FROM APPLETREE WHERE c_name = ?";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginCharacter.getName());
			rs = psmt.executeQuery();
			while(rs.next()) {
				vo = new AppleVO();
				vo.setNum(rs.getInt("num"));
				vo.setWater(rs.getInt("water"));
				vo.setNutrients(rs.getInt("nutrients"));
				vo.setPruning(rs.getInt("pruning"));
				apples.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return apples;
	}
	
	@Override
	public int updateWater(int num) {
		int n = 0;
		String sql = "UPDATE APPLETREE SET WATER = WATER + 1 WHERE c_name = ? AND NUM = ?";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginCharacter.getName());
			psmt.setInt(2, num);
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}

	@Override
	public int updateNutrients(int num) {
		int n = 0;
		String sql = "UPDATE APPLETREE SET nutrients = nutrients + 1 WHERE c_name = ? AND NUM = ?";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginCharacter.getName());
			psmt.setInt(2, num);
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}

	@Override
	public int updatePruning(int num) {
		int n = 0;
		String sql = "UPDATE APPLETREE SET pruning = pruning + 1 WHERE c_name = ? AND NUM = ?";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginCharacter.getName());
			psmt.setInt(2, num);
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}

	@Override
	public int deleteApple(int num) {
		int n = 0;
		String sql = "DELETE APPLETREE WHERE c_name = ? AND NUM = ? AND WATER = 3 AND nutrients = 1 AND pruning = 2";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginCharacter.getName());
			psmt.setInt(2, num);
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}
	
	@Override
	public int deathApple(int num) {
		int n = 0;
		String sql = "DELETE APPLETREE WHERE c_name = ? AND NUM = ?";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginCharacter.getName());
			psmt.setInt(2, num);
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
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

