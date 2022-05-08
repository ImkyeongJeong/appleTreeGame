package com.yedam.appletree;

import java.util.Scanner;

import com.yedam.appletree.service.MemberService;
import com.yedam.appletree.serviceImpl.MemberServiceImpl;
import com.yedam.appletree.vo.CharacterVO;
import com.yedam.appletree.vo.MemberVO;

public class Login {
	private Scanner sc = new Scanner(System.in);
	//접속 user정보 저장
	public static MemberVO loginMember = new MemberVO();
	//접속 user캐릭터정보 저장
	public static CharacterVO loginCharacter = new CharacterVO();
	private MemberService ms = new MemberServiceImpl();
	
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
	
	private void updateInfoTitle() {
		System.out.println("==============================================");
		System.out.println("=    1.비밀번호  2.연락처  3.EMAIL 4.되돌아가기    =");
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
			System.out.println("로그인 성공!");
			loginMember = ms.selectMember(checkId);
			userMenu();
		} else if(checkId == null) {
			System.out.println("존재하지 않는 아이디입니다.");
		} else if(checkId != null && !checkPwd.equals(pwd)) {
			System.out.println("비밀번호가 틀렸습니다.");
		}
		
	}
	
	//접속 user정보
	private void loginInfo() {
		System.out.println(loginMember.getId());
		System.out.println(loginMember.getPwd());
		System.out.println(loginMember.getMName());
		System.out.println(loginMember.getPhone());
		System.out.println(loginMember.getEmail());
	}
	
	
	//사용자 접속메뉴
	private void userMenu() {
		boolean b = true;
		do {
			userTitle();
			System.out.println("메뉴를 선택하세요.");
			int menu = sc.nextInt();
			switch(menu) {
			case 1:
				//게임접속
				loginInfo();
			case 2:
				//정보수정
				updateInfo();
			case 3:
				//로그아웃
				b = false;
				break;
			}
		}
		while(b);
	}
	
	//정보수정
	private void updateInfo() {
		updateInfoTitle();
		int menu = sc.nextInt();
		switch(menu) {
		case 1:
			//비밀번호 변경
			
			break;
		case 2:
			//연락처
			break;
		case 3:
			//이메일
			break;
		case 4:
			userMenu();
			break;
		}
	}
	
	//비밀번호 변경
	private void cPwd() {
		
	}
	//연락처 변경
	private void cPhone() {
		
	}
	//이메일 변경
	private void cEmail() {
		
	}
	
	
	public void run() {
		login();
	}
	
	
}
