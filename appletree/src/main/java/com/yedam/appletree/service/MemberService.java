package com.yedam.appletree.service;

import java.util.List;

import com.yedam.appletree.vo.MemberVO;

public interface MemberService {
	//회원가입
	int insertMember(MemberVO member);
	//아이디 중복체크
	String[] check(String id);
	//회원조회(전체)
	List<MemberVO> selectListMember();
	//회원조회(한건)
	MemberVO selectMember(String id);
	//회원수정
	int updateMember(MemberVO member);
	//회원탈퇴
	int deleteMember(MemberVO member);
}