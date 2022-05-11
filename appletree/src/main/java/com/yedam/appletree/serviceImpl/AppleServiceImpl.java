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
	public int insertTree(int index) {
		int n = 0;
		String sql = "INSERT INTO APPLETREE VALUES(?, ?, DEFAULT, DEFAULT, DEFAULT)";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginMember.getId());
			psmt.setInt(2, index);
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
		String sql = "SELECT * FROM APPLETREE WHERE NUM = ?";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, num);
			rs = psmt.executeQuery();
			if(rs.next()) {
				vo.setIndex(rs.getInt("num"));
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
		String sql = "SELECT * FROM APPLETREE";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				vo = new AppleVO(); 
				vo.setIndex(rs.getInt("num"));
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
	public int updateApple() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteApple() {
		// TODO Auto-generated method stub
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
