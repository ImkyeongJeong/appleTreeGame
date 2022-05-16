package com.yedam.appletree.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.appletree.GameMenu;
import com.yedam.appletree.Login;
import com.yedam.appletree.dao.DataSource;
import com.yedam.appletree.service.GameService;
import com.yedam.appletree.vo.AppleVO;
import com.yedam.appletree.vo.CharacterVO;
import com.yedam.appletree.vo.ItemVO;

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

	@Override //캐릭터 조회
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
				vo.setMoney(rs.getInt("money"));
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
	public int updateTotalApple(int random) {
		String sql = "UPDATE character SET totalapple = totalapple + ? WHERE NAME = ?";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, random);
			psmt.setString(2, Login.loginCharacter.getName());
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return 0;
	}
	
	//사과 수확 후 현재상태 체력 -10 업데이트
	@Override
	public int updateHpDown() {
		String sql = "UPDATE character SET hp = hp - 10 WHERE NAME = ?";
		int n = 0;
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginCharacter.getName());
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}
	
	@Override
	public int updateHpUp(int hp) {
		String sql= null;
		if(hp+10 <= 100) {
			sql = "UPDATE character SET hp = hp + 10 WHERE NAME = ?";
			
		} else {
			sql = "UPDATE character SET hp = 100 WHERE NAME = ?";
		}
		int n = 0;
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginCharacter.getName());
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}

	@Override
	public int useItemUpdateHpUp(String itemName, int hp) {
		String sql= null;
		int hpUP = 0;
		
		if(itemName.equals("달걀")) {
			hpUP = 30;
		} else if(itemName.equals("식혜")) {
			hpUP = 50;
		} else if(itemName.equals("오렌지주스")) {
			hpUP = 100;
		} else if(itemName.equals("사과")) {
			hpUP = 50;
		}
		
		if(hp+hpUP <= 100) {
			if(itemName.equals("달걀")) {
				sql = "UPDATE character SET hp = hp + 30 WHERE NAME = ?";
			} else if(itemName.equals("식혜")){
				sql = "UPDATE character SET hp = hp + 50 WHERE NAME = ?";
			} else if(itemName.equals("오렌지주스")) {
				sql = "UPDATE character SET hp = hp + 100 WHERE NAME = ?";
			} else if(itemName.equals("사과")) {
				sql = "UPDATE character SET hp = hp + 50 WHERE NAME = ?";
			}
		} else {
			sql = "UPDATE character SET hp = 100 WHERE NAME = ?";
		}
		int n = 0;
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginCharacter.getName());
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}
	
	@Override
	public int updateDownMoney(int money) {
		int n = 0;
		String sql = "UPDATE character SET money = money - ? WHERE NAME = ?";
		
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, money);
			psmt.setString(2, Login.loginCharacter.getName());
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}
	
	@Override
	public int updateUpMoney(int money) {
		int n = 0;
		String sql = "UPDATE character SET money = money + ? WHERE NAME = ?";
		
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, money);
			psmt.setString(2, Login.loginCharacter.getName());
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}
	
	@Override //랭킹
	public List<CharacterVO> selectRank() {
		List<CharacterVO> rank  = new ArrayList<CharacterVO>();
		String sql = "SELECT name, totalapple, dense_rank() over (order by totalapple desc) FROM character ORDER BY totalapple desc";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				vo = new CharacterVO();
				vo.setName(rs.getString("name"));
				vo.setTotalApple(rs.getInt("totalapple"));
				rank.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return rank;
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
