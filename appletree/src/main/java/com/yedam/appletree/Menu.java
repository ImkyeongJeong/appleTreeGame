package com.yedam.appletree;

import java.util.Scanner;

import com.yedam.appletree.service.MemberService;
import com.yedam.appletree.serviceImpl.MemberServiceImpl;
import com.yedam.appletree.vo.MemberVO;

public class Menu {
	private Scanner sc = new Scanner(System.in);
	private MemberService dao = new MemberServiceImpl();
	private Login login = new Login();
	private Member member = new Member();
	private UserMenu userMenu = new UserMenu();
	
	private void mainTitle() {
		System.out.println("==============================================");
		System.out.println("=                                            =");
		System.out.println("=                 Apple Tree                 =");
		System.out.println("=                                            =");
		System.out.println("==============================================");
		System.out.println("=        1.로그인    2.회원가입    3.종료         =");
		System.out.println("==============================================");
	}
	
	private void mainMenu() {
		boolean b = true;
		while(true) {
			mainTitle();
			System.out.println("메뉴를 선택하세요.");
			int menu = sc.nextInt();
			switch(menu) {
			case 1:
				login.run();
				break;
			case 2:
				member.run();
				break;
			case 3:
				System.out.println("bye bye");
				b = false;
				break;
			}
			
			//로그인이 됐다면 게임화면 실행(접속유저id가 "0"이 아니라면)
			if(!Login.loginMember.getId().equals("0")) {
				userMenu.run();
			} else {
				mainMenu();
			}
		}
	}
	
	public void run() {
		mainMenu();
	}
}
