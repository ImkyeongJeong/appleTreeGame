package com.yedam.appletree;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.yedam.appletree.service.AppleService;
import com.yedam.appletree.service.GameService;
import com.yedam.appletree.serviceImpl.AppleServiceImpl;
import com.yedam.appletree.serviceImpl.GameServiceImpl;
import com.yedam.appletree.vo.AppleVO;
import com.yedam.appletree.vo.CharacterVO;

public class GameMenu {
	private Scanner sc = new Scanner(System.in);
	private GameService gs = new GameServiceImpl();
	private AppleVO vo = new AppleVO();
	private AppleService as = new AppleServiceImpl();
	private int index = 0;
	private List<AppleVO> apples = new ArrayList<AppleVO>();
	
	private void gameTitle(String name) {
		System.out.println("==============================================");
		System.out.println("                                              ");
		System.out.println("                  " + name + "의 사과농장        ");
		System.out.println("                                              ");
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
		String name = gs.nameCheck(Login.loginMember.getId());
//		if(name != null) {
//			System.out.println("            " +name + "님 환영합니다.");
//		} else 
		if(name == null) {
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
			loginCharacter = gs.selectChar(name);
			
		} else if(gs.nameCheck(name).equals(name)) {
			System.out.println("이미 존재하는 닉네임입니다.");
			insertChar();
		}
	}
	
	//1.현재상태 불러오기(날씨, 총 수확 사과 개수, 체력)
	private void getStatus() {
		CharacterVO status = gs.selectChar(loginCharacter.getName());
		System.out.println(status.toString());
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
		
		boolean b = true;
		
		while(b) {
			farmInfo();
			System.out.println("\n");
			System.out.println("1.나무심기 2.나무정보 3.물주기 4.가지치기 5.영양제주기 6.수확하기 7.취소");
			int menu = sc.nextInt();
			
			if(menu == 1) {
				insertTree();
			} else if(menu == 2) {
				treeInfo();
			} else if(menu == 3) {
				water();
			} else if(menu == 4) {
				nutrients();
			} else if(menu == 5) {
				pruning();
			} else if(menu == 6) {
				getApple();
			} else if(menu == 7) {
				gameMenu();
				b = false;
			}
		}
	}
	
	//농장에 사과나무 정보 출력
	private void farmInfo() {
		String name = loginCharacter.getName();
		System.out.println("\t   [  " + loginCharacter.getName() + "님의 농장  ]" + "\n");
		
		String[] apple = new String[5];
		
		//apple배열 나무 심기 전으로 초기화 
		for (int i = 0; i < apple.length; i++) {
			apple[i] = "o";
		}
		
		//나무 전체 조회하여 apples에 대입
		apples = as.selectAppleList();
		
		//전체 조회 후 자리값에 나무 표시
		for (int i = 0; i < apples.size(); i++) {
			if (apples.get(i) != null) {
				apple[apples.get(i).getNum() - 1] = "♣";
			}
		}
		
		for (String ap : apple) {
			System.out.print("   [" + ap + "] ");
		}
	}
	
	//나무심기
	private void insertTree() {
		System.out.println("몇 번째 자리에 심으시겠습니까?");
		index = sc.nextInt();
		boolean flag = true;
		
		//입력한 자리에 나무가 존재한다면
		for (int i = 0; i < apples.size(); i++) {
			if(apples.get(i).getNum() == index) {
				System.out.println("해당 자리는 나무가 존재합니다.");
				flag = false;
				break;
			}
		}

		if(flag){ //입력한 자리에 나무가 존재하지 않다면
			as.insertTree(index);
		}
	}

	//나무정보
	private void treeInfo() {
		System.out.println("몇 번째 나무 정보를 보시겠습니까?");
		index = sc.nextInt();
		boolean flag = true;
		
		vo = as.selectApple(index);
		if(vo.getNum() == index) {
			System.out.println(vo.toString());	
		} else {
			System.out.println("해당 자리는 나무가 존재하지 않습니다.");
		}
	}
	
	//물주기
	private void water() {
		System.out.println("몇 번째 나무에 물을 주시겠습니까?");
		index = sc.nextInt();
		boolean flag = true;
		
		vo = as.selectApple(index);
		if(vo.getNum() == index) {
			int result = as.updateWater(index);
			if(result  == 1) {
				System.out.println("=== 물~.~ ===");
				gs.updateApple(loginCharacter.getName());
			}
		} else {
			System.out.println("해당 자리는 나무가 존재하지 않습니다.");
		}
	}
	
	//가지치기
	private void nutrients() {
		System.out.println("몇 번째 나무에 가치를 치시겠습니까?");
		index = sc.nextInt();
		
		vo = as.selectApple(index);
		if(vo.getNum() == index) {
			int result = as.updateNutrients(index);
			if(result  == 1) {
				System.out.println("=== 가지치깅~.~ ===");
				gs.updateHp();
			}
		} else {
			System.out.println("해당 자리는 나무가 존재하지 않습니다.");
		}
	}
	
	//영양제주기
	private void pruning() {
		System.out.println("몇 번째 나무에 영양제를 주시겠습니까?");
		index = sc.nextInt();
		
		vo = as.selectApple(index);
		if(vo.getNum() == index) {
			int result = as.updatePruning(index);
			if(result  == 1) {
				System.out.println("=== 영양제~.~ ===");
				gs.updateHp();
			} else {
			System.out.println("해당 자리는 나무가 존재하지 않습니다.");
			}
		}
	}
	
	//수확하기
	private void getApple() {
		System.out.println("몇 번째 나무를 수확하시겠습니까?");
		index = sc.nextInt();
		
		vo = as.selectApple(index);
		if(vo.getNum() == index) {
			int result = as.deleteApple(index);
			if(result  == 1) {
				System.out.println("=== 사과를 얻었습니다.~.~ ===");
				gs.updateHp();
			} else {
				System.out.println("아직 수확할 수 없습니다.");
			}
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
