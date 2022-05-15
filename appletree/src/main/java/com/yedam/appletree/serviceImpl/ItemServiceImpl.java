package com.yedam.appletree.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.appletree.Login;
import com.yedam.appletree.dao.DataSource;
import com.yedam.appletree.service.ItemService;
import com.yedam.appletree.vo.ItemVO;

public class ItemServiceImpl implements ItemService{
	DataSource dao = DataSource.getInstance();
	private Connection conn;
	private PreparedStatement psmt;
	private ResultSet rs;
	

	@Override
	public int insertItem(String item) {
		int n = 0;
		String sql = "INSERT INTO c_item VALUES(?,?,1)";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginCharacter.getName());
			psmt.setString(2, item);
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}
	
	@Override
	public int insertApple(int apple) {
		int n = 0;
		String sql = "INSERT INTO c_item VALUES(?,'사과',?)";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginCharacter.getName());
			psmt.setInt(2, apple);
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}

	@Override
	public int updateUpApple(int apple) {
		int n = 0;
		String sql = "UPDATE c_item SET COUNT = COUNT + ? WHERE c_name = ? AND i_name = '사과'";
		
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, apple);
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
	public int updateDownApple(int apple) {
		int n = 0;
		String sql = "UPDATE c_item SET COUNT = COUNT - ? WHERE c_name = ? AND i_name = '사과'";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, apple);
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
	public List<ItemVO> itemList() {
		ItemVO vo;
		List<ItemVO> list = new ArrayList<ItemVO>();
		String sql = "SELECT * FROM c_item WHERE c_name = ?";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginCharacter.getName());
			rs = psmt.executeQuery();
			while(rs.next()) {
				vo = new ItemVO();
				vo.setItemName(rs.getString("i_name"));
				vo.setCount(rs.getInt("count"));
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	@Override
	public int updateUpItem(String itemName) {
		int n = 0;
		String sql = "UPDATE c_item SET COUNT = COUNT + 1 WHERE c_name = ? AND i_name = ?";
		
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginCharacter.getName());
			psmt.setString(2, itemName);
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}
	
	@Override
	public int updateDownItem(String itemName) {
		int n = 0;
		String sql = "UPDATE c_item SET COUNT = COUNT - 1 WHERE c_name = ? AND i_name = ?";
		
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginCharacter.getName());
			psmt.setString(2, itemName);
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}

	@Override
	public int deleteItem(String itemName) {
		int n = 0;
		String sql = "DELETE FROM c_item WHERE c_name = ? AND i_name = ? AND count = 0";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Login.loginCharacter.getName());
			psmt.setString(2, itemName);
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}
	
	private void close() {
		try {
			if(rs != null) rs.close();
			if(psmt != null) psmt.close();
			if(conn != null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
