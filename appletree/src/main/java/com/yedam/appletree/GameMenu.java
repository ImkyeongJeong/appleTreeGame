package com.yedam.appletree;

import java.util.Scanner;

import com.yedam.appletree.service.GameService;
import com.yedam.appletree.serviceImpl.GameServiceImpl;

public class GameMenu {
	private Scanner sc = new Scanner(System.in);
	private GameService gs = new GameServiceImpl();
	//접속유저 캐릭터 정보 출력
	//Game메뉴 1.현재상태 | 2.장소이동(농장,온천,상점) | 3.
	
	private void gameMenu() {
		boolean b = true;
		do {
			checkCharacter();
			System.out.println("==============================================");
			System.out.println("=        1.현재상태   2.장소이동   3.게임종료        ");
			System.out.println("==============================================");
			int menu = sc.nextInt();
			switch(menu) {
			case 1:
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
	
	//접속user캐릭터 조회( if(null) {캐릭터 생성하기})
	private void checkCharacter() {
		String name = gs.nameCheck(Login.loginMember.getId());
		if(name != null) {
			System.out.println(name + "님 환영합니다.");
		}
		if(name == null) {
			System.out.println("캐릭터가 존재하지 않습니다.");
			System.out.println("생성하시겠습니까? (1.예, 2.아니오)");
			int menu = sc.nextInt();
			if(menu == 1) {
				insertChar();
			} else if(menu == 2) {
				checkCharacter();
			}
		}
		
	}
	
	//캐릭터 생성
	private void insertChar() {
		System.out.println("닉네임을 입력하세요.");
		String name = sc.next();
		if(gs.nameCheck(name) == null) {
			gs.insertChar(name);
			System.out.println("생성 완료!");
		} else if(gs.nameCheck(name).equals(name)) {
			System.out.println("이미 존재하는 닉네임입니다.");
		}
	}
	//현재상태 불러오기(수확한 사과 개수, 체력, 보유 아이템, 돈) / 날씨
	
	//장소이동(농장, 온천, 상점)
	
	
	
	public void run() {
		gameMenu();
	}
}
