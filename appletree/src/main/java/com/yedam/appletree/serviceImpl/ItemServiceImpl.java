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
	public int insertItem() {
		int n = 0;
		String sql = "INSERT INTO c_item VALUES(?,null,null,DEFAULT)";
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
	public int updateItem(int itemName) {
		return 0;
	}

	@Override
	public int deleteItem(int itemName) {
		return 0;
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
				vo.setMoney(rs.getInt("money"));
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

}
