package com.yedam.appletree;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.yedam.appletree.service.AppleService;
import com.yedam.appletree.service.GameService;
import com.yedam.appletree.serviceImpl.AppleServiceImpl;
import com.yedam.appletree.serviceImpl.GameServiceImpl;
import com.yedam.appletree.vo.AppleVO;

public class GameMenu {
	private Scanner sc = new Scanner(System.in);
	private GameService gs = new GameServiceImpl();
	private AppleVO apple = new AppleVO();
	private AppleService as = new AppleServiceImpl();
	private int index = 1;
	private AppleVO vo;
	
	private void gameTitle() {
		System.out.println("==============================================");
		System.out.println("=                                            =");
		System.out.println("=                 Apple Tree                 =");
		System.out.println("=                                            =");
		System.out.println("==============================================");
	}
	
	//현재상태(날씨, 총 수확 사과 개수, 체력) / 아이템(보유아이템(사과포함), 돈
	private void gameMenuTitle() {
		System.out.println("==============================================");
		System.out.println("=   1.현재상태  2.아이템창  3.이동하기  4.게임종료    =");
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
				itemList();
				break;
			case 3:
				move();
				break;
			case 4:
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
			insertChar();
		}
	}
	
	//1.현재상태 불러오기(날씨, 총 수확 사과 개수, 체력)
	private void getStatus() {
		String name = gs.nameCheck(Login.loginMember.getId());
		System.out.println("[" + name + "]님의 현재상태");
	}
	
	//2.해당 캐릭터 아이템창 보기
	private void itemList() {
		String name = gs.nameCheck(Login.loginMember.getId());
		System.out.println("[" + name + "]님의 아이템");
	}
	
	//3.장소이동(농장, 온천, 상점)
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

				break;
			case 4:
				b = false;
				break;
			}
		}
		while(b);
	}
	
	//농장: 사과나무 컨트롤
	private void appleCtrl() {
		String name = gs.nameCheck(Login.loginMember.getId());
		System.out.println("[   " + name + "님의 농장에 입장!  ]" + "\n");
		String[] apple = new String[5];
		for (int i = 0; i < apple.length; i++) {
			apple[i] = "o";
		}
		
		List<AppleVO> apples = new ArrayList<AppleVO>();
		apples = as.selectAppleList();
//		apple[0]="|"; //심은후
//		apple[0]="o"; //심기전
//		apple[0]=" "; //애플vo생성 후 index(나무번호),물주기,가지치기,영양제
		
		for (int i = 0; i < apples.size(); i++) {
			if (apples.get(i) != null) {
				apple[apples.get(i).getIndex() - 1] = "♣";
			}
		}
		
		for (String ap : apple) {
			System.out.print(ap + " ");
		}
		
//		for (int i = 0; i < apple.length; i++) {
//			apple.add("o");
//			apple.add()
//			if(apples.get(i).getIndex() != 0) {
//				apple[i] = "♣";
//			System.out.print(" [" + apple[i].toString() + "]");
//		}
//		System.out.println("\n \n");
//		}
		boolean b = true;
		
		while(b) {
			System.out.println("1.나무심기 2.나무정보 3.물주기 4.가지치기 5.영양제주기 6.취소");
			int menu = sc.nextInt();
			if(menu == 1) {
				insertTree();
			} else if(menu == 2) {
				
			} else if(menu == 3) {
				
			} else if(menu == 4) {
				
			} else if(menu == 5) {
				
			} else if(menu == 6) {
				gameMenu();
				b = false;
			}
		}
	}
	
	//나무심기
	private void insertTree() {
		System.out.println("몇 번째 자리에 심으시겠습니까?");
		index = sc.nextInt();
		System.out.println(vo.getIndex());
		if(index == vo.getIndex()) {
			System.out.println("해당 자리는 나무가 존재합니다.");
		} else if(index > 0 && index <= 5){
			as.insertTree(index);
		}
	}
	
	//온천: hp 충전(시간당 10씩)
	private void spa() {
		
	}
	
	//상점: 
	public void run() {
		gameMenu();
	}
}
