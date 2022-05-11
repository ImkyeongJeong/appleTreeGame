package com.yedam.appletree;

import java.util.Scanner;

import com.yedam.appletree.service.MemberService;
import com.yedam.appletree.serviceImpl.MemberServiceImpl;
import com.yedam.appletree.vo.MemberVO;

public class Login {
	private Scanner sc = new Scanner(System.in);
	private MemberService ms = new MemberServiceImpl();
	//접속 user정보 저장
	public static MemberVO loginMember = new MemberVO();
	
	private void loginTitle() {
		System.out.println("==============================================");
		System.out.println("=                  로  그  인                 =");
		System.out.println("==============================================");
	}

	//로그인
	private void login() {
		loginTitle();
		System.out.println("아이디를 입력하세요.");
		String id = sc.next();
		System.out.println("비밀번호를 입력하세요.");
		String pwd = sc.next();
		
		String[] checkIdPwd = ms.check(id);
		String checkId = checkIdPwd[0];
		String checkPwd = checkIdPwd[1];
		
		if(checkId != null && checkId.equals(id) && checkPwd.equals(pwd)) {
			loginMember = ms.selectMember(id);
			System.out.println("로그인 성공!");
		} else if(checkId == null) {
			System.out.println("존재하지 않는 아이디입니다.");
			loginMember.setId("0");
		} else if(checkId != null && !checkPwd.equals(pwd)) {
			System.out.println("비밀번호가 틀렸습니다.");
		}
	}
	
	public void run() {
		login();
	}
}
