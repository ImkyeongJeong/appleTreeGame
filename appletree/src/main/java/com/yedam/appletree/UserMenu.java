package com.yedam.appletree;

import java.util.Scanner;

import com.yedam.appletree.service.MemberService;
import com.yedam.appletree.serviceImpl.MemberServiceImpl;
import com.yedam.appletree.vo.CharacterVO;
import com.yedam.appletree.vo.MemberVO;

public class UserMenu {
	private Scanner sc = new Scanner(System.in);
	private GameMenu game = new GameMenu();
	private InfoUpdate infoUpdate = new InfoUpdate();
	private MemberService ms = new MemberServiceImpl();
	
	private void userTitle() {
		System.out.println("==============================================");
		System.out.println("=                                            =");
		System.out.println("=                 Apple Tree                 =");
		System.out.println("=                                            =");
		System.out.println("==============================================");
		System.out.println("=  1.게임접속   2.정보수정   3.로그아웃   4.회원탈퇴  =");
		System.out.println("==============================================");
	}

	//사용자 메뉴
	private void userMenu() {
		boolean b = true;
		do {
			MainMenu.clearScreen();
			userTitle();
			System.out.println("메뉴를 선택하세요.");
			int menu = sc.nextInt();
			switch(menu) {
			case 1:
				//게임접속
				game.run();
				break;
			case 2:
				//정보수정
				infoUpdate.run();
				break;
			case 3:
				//로그아웃
				MainMenu.clearScreen();
				b = false;
				break;
			case 4:
				//회원탈퇴
				delMember();
				b = false;
				break;
			}
		}
		while(b);
	}
	
	//회원탈퇴
	private void delMember() {
		System.out.println("탈퇴하시겠습니까? (1.예, 2.아니오)");
		int check = sc.nextInt();
		if(check == 1) {
			System.out.println("비밀번호를 입력하세요.");
			String pwd = sc.next();
			if(pwd.equals(Login.loginMember.getPwd())) {
				int n = ms.deleteMember(Login.loginMember.getId());
				if(n == 1) {
					System.out.println("탈퇴완료");
					MainMenu.sleepTime(800);
					MainMenu.clearScreen();
				}
				//자바에 저장된 로그인 초기화
				Login.loginMember = new MemberVO();
			} else {
				System.out.println("비밀번호가 맞지 않습니다.");
				MainMenu.sleepTime(800);
				MainMenu.clearScreen();
			}
		}
	}
	
	public void run() {
		userMenu();
	}
}
