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
			loginMember = ms.selectMember(id);
			System.out.println("로그인 성공!");
			userMenu();
		} else if(checkId == null) {
			System.out.println("존재하지 않는 아이디입니다.");
		} else if(checkId != null && !checkPwd.equals(pwd)) {
			System.out.println("비밀번호가 틀렸습니다.");
		}
	}
	
	//접속 user정보
	private void loginInfo() {
		System.out.println("ID : " + loginMember.getId());
		System.out.println("이   름: " + loginMember.getMName());
		System.out.println("연 락 처: " + loginMember.getPhone());
		System.out.println("이 메 일: " + loginMember.getEmail());
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
				updateInfo();
				break;
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
		//접속자 정보 출력
		loginInfo();
		updateInfoTitle();
		int menu = sc.nextInt();
		switch(menu) {
		case 1:
			//비밀번호 변경
			cgPwd();
			break;
		case 2:
			//연락처
			cgPhone();
			break;
		case 3:
			//이메일
			cgEmail();
			break;
		case 4:
			userMenu();
			break;
		}
	}
	
	//비밀번호 변경
	private void cgPwd() {
		System.out.println("현재 비밀번호를 입력하세요.");
		String pwd = sc.next();
		System.out.println("변경할 비밀번호를 입력하세요.");
		String cgPwd = sc.next();
		
		String[] checkIdPwd = ms.check(loginMember.getId());
		String checkPwd = checkIdPwd[1];
		
		if(checkPwd != null && checkPwd.equals(pwd)) {
			loginMember.setPwd(cgPwd);
			ms.updateMember(loginMember);
			System.out.println("비밀번호 변경완료!");
		} else if(!checkPwd.equals(pwd)) { 
			System.out.println("현재 비밀번호가 맞지 않습니다.");
		}
	}
	
	//연락처 변경
	private void cgPhone() {
		System.out.println("변경할 연락처를 입력하세요.");
		String cgPhone = sc.next();
		loginMember.setPhone(cgPhone);
		ms.updateMember(loginMember);
		System.out.println("수정완료!");
	}
	//이메일 변경
	private void cgEmail() {
		System.out.println("변경할 메일을 입력하세요.");
		String cgEmail = sc.next();
		loginMember.setPhone(cgEmail);
		ms.updateMember(loginMember);
		System.out.println("수정완료!");
	}
	
	
	public void run() {
		login();
	}
}
