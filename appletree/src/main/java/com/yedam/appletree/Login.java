package com.yedam.appletree;

import java.util.Scanner;

import com.yedam.appletree.service.MemberService;
import com.yedam.appletree.serviceImpl.MemberServiceImpl;
import com.yedam.appletree.vo.CharacterVO;
import com.yedam.appletree.vo.MemberVO;

public class Login {
	private Scanner sc = new Scanner(System.in);
	private MemberService ms = new MemberServiceImpl();
	private GameMenu game = new GameMenu();
	private InfoUpdate infoUpdate = new InfoUpdate();
	//접속 user정보 저장
	public static MemberVO loginMember = new MemberVO();
	//접속 user캐릭터정보 저장
	public static CharacterVO loginCharacter = new CharacterVO();
	
	private void loginTitle() {
		System.out.println("==============================================");
		System.out.println("=                  로  그  인                 =");
		System.out.println("==============================================");
	}
	
	private void userTitle() {
		System.out.println("==============================================");
		System.out.println("=      1.게임접속    2.정보수정    3.로그아웃      =");
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
			userMenu();
		} else if(checkId == null) {
			System.out.println("존재하지 않는 아이디입니다.");
		} else if(checkId != null && !checkPwd.equals(pwd)) {
			System.out.println("비밀번호가 틀렸습니다.");
		}
	}
	
	//사용자 메뉴
	private void userMenu() {
		boolean b = true;
		do {
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
				b = false;
				break;
			}
		}
		while(b);
	}
	
	public void run() {
		login();
	}
}
