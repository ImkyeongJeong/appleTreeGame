package com.yedam.appletree;

import java.util.Scanner;

import com.yedam.appletree.service.GameService;
import com.yedam.appletree.serviceImpl.GameServiceImpl;
import com.yedam.appletree.vo.AppleVO;

public class GameMenu {
	private Scanner sc = new Scanner(System.in);
	private GameService gs = new GameServiceImpl();
	private AppleVO apple = new AppleVO();
	
	private void gameTitle() {
		System.out.println("==============================================");
		System.out.println("=                                            =");
		System.out.println("=                 Apple Tree                 =");
		System.out.println("=                                            =");
		System.out.println("==============================================");
	}
	
	private void gameMenuTitle() {
		System.out.println("==============================================");
		System.out.println("=        1.현재상태   2.장소이동   3.게임종료       =");
		System.out.println("==============================================");
	}
	
	private void gameMenu() {
		checkChar();
		boolean b = true;
		do {
			gameMenuTitle();
			int menu = sc.nextInt();
			switch(menu) {
			case 1:
				getStatus();
				break;
			case 2:
				move();
				break;
			case 3:
				b = false;
				break;
			}
		}
		while(b);
	}
	
	//접속user 캐릭터 유무 확인
	private void checkChar() {
		gameTitle();
		String name = gs.nameCheck(Login.loginMember.getId());
		if(name != null) {
			System.out.println("            " +name + "님 환영합니다.");
		} else if(name == null) {
			System.out.println("            캐릭터가 존재하지 않습니다.");
			System.out.println("==============================================");
			insertChar();
		}
	}
	
	//캐릭터 생성
	private void insertChar() {
		System.out.println("캐릭터 생성 > 닉네임을 입력하세요.");
		String name = sc.next();
		if(gs.nameCheck(name) == null) {
			gs.insertChar(name);
			System.out.println("생성 완료!");
			checkChar();
		} else if(gs.nameCheck(name).equals(name)) {
			System.out.println("이미 존재하는 닉네임입니다.");
		}
	}
	
	//현재상태 불러오기(수확한 사과 개수, 체력, 보유 아이템, 돈) / 날씨
	private void getStatus() {
		System.out.println("현재상태 얍");
	}
	
	//장소이동(농장, 온천, 상점)
	private void move() {
		boolean b = true;
		do {
			System.out.println("1.농장 2.온천 3.상점 4.취소");
			int menu = sc.nextInt();
			switch(menu) {
			case 1:
				appleCtrl();
				break;
			case 2:
				
				break;
			case 3:
				b = false;
				break;
			}
		}
		while(b);
	}
	
	//사과나무 컨트롤 메서드
	private void appleCtrl() {
		String name = gs.nameCheck(Login.loginMember.getId());
		System.out.println(name + "님의 농장에 입장!");
		String[] apple = new String[5];
		apple[0]="|"; //심은후
		apple[0]="o"; //심기전
		apple[0]=" "; //애플vo생성 후 index(나무번호),물주기,가지치기,영양제
	}
	
	
	public void run() {
		gameMenu();
	}
}
