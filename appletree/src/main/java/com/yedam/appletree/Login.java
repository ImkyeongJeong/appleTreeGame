package com.yedam.appletree;

import java.util.Scanner;

import com.yedam.appletree.service.GameService;
import com.yedam.appletree.service.MemberService;
import com.yedam.appletree.serviceImpl.GameServiceImpl;
import com.yedam.appletree.serviceImpl.MemberServiceImpl;
import com.yedam.appletree.vo.CharacterVO;
import com.yedam.appletree.vo.MemberVO;

public class Login {
	private Scanner sc = new Scanner(System.in);
	private MemberService ms = new MemberServiceImpl();
	private GameService gs = new GameServiceImpl();
	//접속 user정보 저장
	public static MemberVO loginMember = new MemberVO();
	//접속 캐릭터정보 저장
	public static CharacterVO loginCharacter = new CharacterVO();
	
	private void loginTitle() {
		System.out.println("==============================================");
		System.out.println("=                  로  그  인                 =");
		System.out.println("==============================================");
	}

	//로그인
	private void login() {
		MainMenu.clearScreen();
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
			loginCharacter = gs.selectChar(loginMember.getId());
			System.out.println("로그인 성공!");
			MainMenu.sleepTime(800);
			MainMenu.clearScreen();
		} else if(checkId == null) {
			System.out.println("존재하지 않는 아이디입니다.");
			MainMenu.sleepTime(800);
			MainMenu.clearScreen();
			loginMember.setId("0");
		} else if(checkId != null && !checkPwd.equals(pwd)) {
			System.out.println("비밀번호가 틀렸습니다.");
			loginMember.setId("0");
			MainMenu.sleepTime(800);
			MainMenu.clearScreen();
		}
	}
	
	public void run() {
		login();
	}
}
