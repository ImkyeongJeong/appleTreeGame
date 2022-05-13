package com.yedam.appletree;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.yedam.appletree.service.AppleService;
import com.yedam.appletree.service.GameService;
import com.yedam.appletree.service.ItemService;
import com.yedam.appletree.serviceImpl.AppleServiceImpl;
import com.yedam.appletree.serviceImpl.GameServiceImpl;
import com.yedam.appletree.serviceImpl.ItemServiceImpl;
import com.yedam.appletree.vo.AppleVO;
import com.yedam.appletree.vo.CharacterVO;
import com.yedam.appletree.vo.ItemVO;

public class GameMenu {
	private Scanner sc = new Scanner(System.in);
	private GameService gs = new GameServiceImpl();
	private AppleVO vo = new AppleVO();
	private AppleService as = new AppleServiceImpl();
	private ItemService is = new ItemServiceImpl();
	private int index = 0;
	private List<AppleVO> apples = new ArrayList<AppleVO>();
	
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
		Login.loginCharacter.setName(name);
		
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
		String nameCheck = gs.nameCheck(name);
		if(nameCheck == null) {
			gs.insertChar(name);
			Login.loginCharacter.setName(name);
			is.insertItem();
			System.out.println("생성 완료!");
		} else if(nameCheck != null) {
			System.out.println("이미 존재하는 닉네임입니다.");
			insertChar();
		}
	}
	
	//1.현재상태 불러오기(날씨, 총 수확 사과 개수, 체력)
	private void getStatus() {
		CharacterVO status = gs.selectChar(Login.loginCharacter.getName());
		System.out.println(status.toString());
	}
	
	//2.해당 캐릭터 아이템창 보기
	private void itemList() {
		List<ItemVO> list = new ArrayList<ItemVO>();
		ItemVO item = new ItemVO();
		list = is.itemList();
		System.out.println("[" + Login.loginCharacter.getName() + "]님의 아이템\n");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(item.toString());
		}
			
	}
	
	//3. 이동하기
	private void move() {
		CharacterVO status = gs.selectChar(Login.loginCharacter.getName());
		
		boolean b = true;
		
		while(b) {
			System.out.println("1.농장 2.온천 3.상점 4.되돌아가기");
			int menu = sc.nextInt();
			
			if(menu == 1) {
				if(status.getHp() <= 0) {
					System.out.println("hp부족 농장 출입 불가!");
				} else appleFarm();
				
			} else if(menu == 2) {
				spa();
			} else if(menu == 3) {
				shop();
			} else if(menu == 4) {
				b = false;
			}
		}
	}
	
	//농장: 사과나무 컨트롤
	private void appleFarm() {
		
		boolean b = true;
		
		while(b) {
			CharacterVO status = gs.selectChar(Login.loginCharacter.getName());
			int hp = status.getHp();
			
			if(hp <= 0) {
				System.out.println("hp가 부족합니다.!!!!");
				break;
			}
			farmInfo();
			System.out.println("\n");
			System.out.println("1.나무심기 2.나무정보 3.물주기 4.가지치기");
			System.out.println("5.영양제주기 6.수확하기 7.나가기");
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
		System.out.println();
		String name = Login.loginCharacter.getName();
		System.out.println("\t   [  " + Login.loginCharacter.getName() + "님의 농장  ]" + "\n");
		
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

		if(flag){ //입력한 자리에 나무가 존재하지 않다면 심기
			int result = as.insertTree(index);
			if(result == 1) {
				System.out.println(index + "번째 나무 심기 완료!");
				gs.updateHpDown();
			}
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
		
		vo = as.selectApple(index);
		if(vo.getNum() == index) {
			as.updateWater(index);
			int n = gs.updateHpDown();
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
				System.out.println("가지치기 완료!");
				gs.updateHpDown();
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
				System.out.println("영양제 주기 완료!");
				gs.updateHpDown();
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

		if(vo.getNum() == index && vo.getWater() == 5 && vo.getNutrients() == 2 && vo.getPruning() == 2) {
			int result = as.deleteApple(index);
			if(result  == 1) {
				int random = (int)(Math.random()*20) + 1;
				System.out.println("!!!!! 사과(" + random + ")개를 얻었습니다. !!!!!");
				gs.updateHpDown();
				gs.updateApple(random);
			} 
		} else if(vo.getNum() != index) {
			System.out.println("해당 자리는 나무가 존재하지 않습니다.");
		} else {
			System.out.println("아직 수확할 수 없습니다.");
			System.out.println(vo.toString());
		}
	}
	
	//온천: hp 충전(시간당 10씩)
	private void spa() {
		System.out.println("==================");
		System.out.println("온천에 입장하셨습니다.");
		System.out.println("==================");
		CharacterVO status = gs.selectChar(Login.loginCharacter.getName());
		System.out.println("현재 hp: " + status.getHp());
		
		boolean b = true;
		
		while(b) {
			System.out.println("1.hp회복하기 2.나가기");
			int menu = sc.nextInt();
			if(menu == 1) {
				status = gs.selectChar(Login.loginCharacter.getName());
				if(status.getHp() >= 100) {
					System.out.println("hp 모두 회복완료!!!!");
				} else if(status.getHp() < 100) {
					gs.updateHpUp(status.getHp());
					System.out.println("hp +10 !!!");
				}
			} else if(menu == 2) {
				b = false;
			} else if(menu != 1) {
				System.out.println("잘못된 선택입니다.");
			}
		}
	}
	
	//상점: 아이템 사고 팔고
	private void shop() {
		boolean b = true;
		System.out.println("==================");
		System.out.println("상점에 입장하셨습니다.");
		System.out.println("==================");

		while(b) {
			System.out.println("1.구입하기 2.사과팔기 3.나가기");
			int menu = sc.nextInt();
			
			if(menu == 1) {
				boolean flag = true;
				
				while(flag) {
					System.out.println("1.식혜(500원) 2.네잎클로버(10,000원) 3.되돌아가기");
					int buyMenu = sc.nextInt();
					if(buyMenu == 1) {
						System.out.println("식혜 구입완료!");
					} else if(buyMenu == 2) {
						System.out.println("네잎클로버 구입완료!");
					} else if(buyMenu == 3) {
						flag = false;
					}
				}
			} else if(menu == 2) {
				System.out.println("몇개를 파시겠습니까? (1개당:1,000원)");
//				int sell = sc.nextInt();
				//판매 했을 때 아이템에 money증가
			} else if(menu == 3) {
				b = false;
			}
		}


	}
	
	public void run() {
		gameMenu();
	}
}
