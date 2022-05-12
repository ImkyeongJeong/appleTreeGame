package com.yedam.appletree.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yedam.appletree.GameMenu;
import com.yedam.appletree.Login;
import com.yedam.appletree.dao.DataSource;
import com.yedam.appletree.service.GameService;
import com.yedam.appletree.vo.AppleVO;
import com.yedam.appletree.vo.CharacterVO;

public class GameServiceImpl implements GameService{
	DataSource dao = DataSource.getInstance();
	private Connection conn;
	private PreparedStatement psmt;
	private ResultSet rs;
	CharacterVO vo;
	
	@Override //캐릭터 생성
	public int insertChar(String name) {
		int n = 0;
		String sql = "INSERT INTO character VALUES(?, ?, DEFAULT, DEFAULT, DEFAULT)";
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

	@Override //닉네임 중복체크 / 캐릭터조회
	public String nameCheck(String name) {
		String checkName = null;
		String sql = "SELECT name FROM character WHERE NAME = ?";
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

	@Override //현재상태보기
	public CharacterVO selectChar(String name) {
		CharacterVO vo = new CharacterVO();
		String sql = "SELECT * FROM character WHERE NAME = ?";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, name);
			rs = psmt.executeQuery();
			if(rs.next()) {
				vo.setName(rs.getString("name"));
				vo.setHp(rs.getInt("hp"));
				vo.setTotalApple(rs.getInt("totalapple"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return vo;
	}

	//사과 수확 후 현재상태 totalapple +1 업데이트
	@Override
	public int updateApple(String name) {
		String sql = "UPDATE character SET totalapple = totalapple + 1 WHERE NAME = ?";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, name);
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return 0;
	}
	
	//사과 수확 후 현재상태 체력 업데이트
	@Override
	public int updateHp() {
		String sql = "UPDATE character SET hp = hp - 10 WHERE NAME = ?";
		int n = 0;
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, GameMenu.loginCharacter.getName());
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}
	
	@Override //보유아이템, 돈, 체력 수정
	public int updateChar(CharacterVO character) {
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
