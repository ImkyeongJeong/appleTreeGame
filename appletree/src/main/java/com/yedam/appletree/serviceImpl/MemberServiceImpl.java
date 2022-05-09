package com.yedam.appletree.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.appletree.dao.DataSource;
import com.yedam.appletree.service.MemberService;
import com.yedam.appletree.vo.MemberVO;

public class MemberServiceImpl implements MemberService{
	//DataSource객체 연결
	DataSource dao = DataSource.getInstance();
	//커넥션 연결
	private Connection conn;
	//sql문 실행 시키기 위함
	private PreparedStatement psmt;
	//sql문이 select일 때 결과 받기 위함
	private ResultSet rs;
	
	@Override
	public int insertMember(MemberVO member) {
		String sql = "INSERT INTO MEMBER VALUES(?,?,?,?,?)";
		int n = 0;
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, member.getId());
			psmt.setString(2, member.getPwd());
			psmt.setString(3, member.getMName());
			psmt.setString(4, member.getPhone());
			psmt.setString(5, member.getEmail());
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}

	@Override
	public List<MemberVO> selectListMember() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO vo;
		String sql = "SELECT * FROM MEMBER";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				vo = new MemberVO();
				vo.setId(rs.getString("id"));
				vo.setPwd(rs.getString("pwd"));
				vo.setMName(rs.getString("namd"));
				vo.setPhone(rs.getString("phone"));
				vo.setEmail(rs.getString("email"));
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
	public MemberVO selectMember(String id) {
		//로그인된 ID정보 넘겨주기
		MemberVO vo = new MemberVO();
		String sql = "SELECT * FROM MEMBER WHERE ID = ?";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if(rs.next()) {
				vo.setId(rs.getString("id"));
				vo.setPwd(rs.getString("pwd"));
				vo.setMName(rs.getString("name"));
				vo.setPhone(rs.getString("phone"));
				vo.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return vo;
	}

	@Override
	public int updateMember(MemberVO member) {
		String sql = "UPDATE MEMBER SET PWD = ?, NAME = ?, PHONE = ?,"
					+ " EMAIL = ? WHERE ID = ?";
		int n = 0;
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, member.getPwd());
			psmt.setString(2, member.getMName());
			psmt.setString(3, member.getPhone());
			psmt.setString(4, member.getEmail());
			psmt.setString(5, member.getId());
			n = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return n;
	}

	@Override
	public int deleteMember(MemberVO member) {
		
		return 0;
	}
	
	//중복체크 / ID&PW확인
	@Override 
	public String[] check(String id) {
		String[] checkIdPwd = new String[2];
		String sql = "SELECT ID, PWD FROM MEMBER WHERE ID = ?";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if(rs.next()) {
				checkIdPwd[0] = rs.getString("id");
				checkIdPwd[1] = rs.getString("pwd");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			close();
		}
		return checkIdPwd;
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
