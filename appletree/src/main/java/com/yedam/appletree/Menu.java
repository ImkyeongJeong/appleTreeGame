package com.yedam.appletree;

import java.util.Scanner;

import com.yedam.appletree.service.MemberService;
import com.yedam.appletree.serviceImpl.MemberServiceImpl;
import com.yedam.appletree.vo.MemberVO;

public class Menu {
	private Scanner sc = new Scanner(System.in);
	private MemberService dao = new MemberServiceImpl();
	
	private void mainTitle() {
		System.out.println("================================");
		System.out.println("=                              =");
		System.out.println("=          Apple Tree          =");
		System.out.println("=                              =");
		System.out.println("================================");
		System.out.println("==== 1.로그인 2.회원가입 3.종료  ====");
		System.out.println("================================");
	}
	
	private void mainMenu() {
		mainTitle();
		int menu = sc.nextInt();
		switch(menu) {
		case 1:
			login();
			break;
		case 2:
			memberJoin();
			break;
		case 3:
			System.out.println("bye bye");
			break;
		}
	}
	
	private void login() {
		System.out.println("================================");
		System.out.println("============ 로 그 인 ===========");
		System.out.println("================================");
		System.out.println("아이디를 입력하세요.");
		String id = sc.next();
		System.out.println("비밀번호를 입력하세요.");
		String pwd = sc.next();
		
	}
	
	private void memberJoin() {
		System.out.println("================================");
		System.out.println("=========== 회 원 가 입 ==========");
		System.out.println("================================");
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
		dao.insertMember(member);
	}
	
	public void run() {
		mainMenu();
	}
	
}
