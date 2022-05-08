package com.yedam.appletree;

import java.util.Scanner;

import com.yedam.appletree.service.MemberService;
import com.yedam.appletree.serviceImpl.MemberServiceImpl;
import com.yedam.appletree.vo.MemberVO;

public class Menu {
	private Scanner sc = new Scanner(System.in);
	private MemberService dao = new MemberServiceImpl();
	private Login login = new Login();
	
	private void mainTitle() {
		System.out.println("==============================================");
		System.out.println("=                                            =");
		System.out.println("=                 Apple Tree                 =");
		System.out.println("=                                            =");
		System.out.println("==============================================");
		System.out.println("=        1.로그인    2.회원가입    3.종료         =");
		System.out.println("==============================================");
	}
	
	private void memberJoinTitle() {
		System.out.println("==============================================");
		System.out.println("=                 회  원  가  입               =");
		System.out.println("==============================================");
	}
	
	private void mainMenu() {
		boolean b = true;
		do {
			mainTitle();
			System.out.println("메뉴를 선택하세요.");
			int menu = sc.nextInt();
			switch(menu) {
			case 1:
				login.run();
				break;
			case 2:
				memberJoinTitle();
				memberJoin();
				break;
			case 3:
				System.out.println("bye bye");
				break;
			}
		}
		while(b);
	}
	
	//회원가입
	private void memberJoin() {
		System.out.println("아이디를 입력하세요.");
		String id = sc.next();
		System.out.println("비밀번호를 입력하세요.");
		String pwd = sc.next();
		System.out.println("이름을 입력하세요.");
		String mName = sc.next();
		System.out.println("연락처를 입력하세요.");
		String phone = sc.next();
		System.out.println("e-mail을 입력하세요.");
		String email = sc.next();
		
		MemberVO member = new MemberVO(id, pwd, mName, phone, email);
		String[] checkIdPwd = dao.check(id);
		String checkId = checkIdPwd[0];
		if(checkId != null && checkId.equals(id)) {
			System.out.println("이미 존재하는 아이디입니다.");
		} else {
			dao.insertMember(member);
			System.out.println(member.getMName() + "님 회원가입 완료");
		}
	}
	
	public void run() {
		mainMenu();
	}
	
}
