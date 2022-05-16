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
		System.out.println("=                                            =");
		System.out.println("=                 Apple Tree                 =");
		System.out.println("=                                            =");
		System.out.println("==============================================");
		System.out.println("=   1.현재상태  2.아이템창  3.이동하기  4.게임종료    =");
		System.out.println("==============================================");
	}
	
	private void gameMenu() {
		checkChar();
		
		boolean b = true;
		do {
			MainMenu.clearScreen();
			gameMenuTitle();
			int menu = sc.nextInt();
			sc.nextLine();
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
			MainMenu.clearScreen();
			System.out.println("==============================================");
			System.out.println("=                                            =");
			System.out.println("=                 Apple Tree                 =");
			System.out.println("=                                            =");
			System.out.println("==============================================");
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
			System.out.println("생성 완료!");
			MainMenu.sleepTime(1000);
			MainMenu.clearScreen();
		} else if(nameCheck != null) {
			System.out.println("이미 존재하는 닉네임입니다.");
			MainMenu.sleepTime(1000);
			insertChar();
		}
	}
	
	//1.현재상태 불러오기(날씨, 총 수확 사과 개수, 체력)
	private void getStatus() {
		MainMenu.clearScreen();
		CharacterVO status = gs.selectChar(Login.loginCharacter.getName());
		System.out.println("[" + Login.loginCharacter.getName() + "]님의 현재상태");
		System.out.println("==============================================");
		System.out.println(status.toString());
		System.out.println("==============================================");
		MainMenu.sleepTime(1800);
	}
	
	//2.해당 캐릭터 아이템창 보기
	private void itemList() {
		MainMenu.clearScreen();
		boolean flag = true; 
		
		while(flag) {
			CharacterVO selectHp = gs.selectChar(Login.loginCharacter.getName());
			int hp = selectHp.getHp();
			List<ItemVO> list = new ArrayList<ItemVO>();
			list = is.itemList();
			if(list.size() == 0) {
				System.out.println("[" + Login.loginCharacter.getName() + "]님의 아이템");
				System.out.println("==============================================");
				System.out.println("\t보유하신 아이템이 없습니다.");
				System.out.println("==============================================");
				MainMenu.sleepTime(1000);
				flag = false;
			} else {
				System.out.println("[" + Login.loginCharacter.getName() + "]님의 아이템");
				System.out.println("==============================================");
				for (int i = 0; i < list.size(); i++) { //아이템 목록 출력
					System.out.print(list.get(i).getItemName() + list.get(i).getCount()+ "개  ");
				}
				System.out.println("\n==============================================");
				System.out.println("사용하시려면 아이템을 직접 입력해주세요. 사용하지 않으시려면 숫자(0)입력");
				String useItem = sc.next();
				
				int hpUP = 0;
				if(useItem.equals("달걀")) {
					hpUP = 30;
				} else if(useItem.equals("식혜")) {
					hpUP = 50;
				} else if(useItem.equals("오렌지주스")) {
					hpUP = 100;
				} else if(useItem.equals("사과")) {
					hpUP = 50;
				}
				
				
				if(useItem.equals("0")) {
					MainMenu.sleepTime(100);
					flag = false;
				} else {
					if(hp >= 100) {
						System.out.println("현재 체력이 이미 모두 회복되어 있습니다.");
						MainMenu.sleepTime(1000);
						flag = false;
					} else {
						for (int i = 0; i < list.size(); i++) {
							CharacterVO hpSelect = gs.selectChar(Login.loginCharacter.getName());
							if(list.get(i).getItemName().equals(useItem)) {
								is.updateDownItem(useItem);
								gs.useItemUpdateHpUp(list.get(i).getItemName(), hpSelect.getHp());
								System.out.println(useItem + "을(를) 사용하여 hp +" + hpUP + "!");
								MainMenu.sleepTime(1000);
								MainMenu.clearScreen();
							}
						}
					}
					//사용 후 다시 조회했을 때 아이템 0개일 때 아이템창에서 삭제
					list = is.itemList();
					for (int i = 0; i < list.size(); i++) {
						if(list.get(i).getCount() == 0) {
							is.deleteItem(list.get(i).getItemName());
						}
					}
					//삭제 후 다시 조회했을 때 아이템이 없다면
					list = is.itemList();
					if(list.size() == 0) {
						System.out.println("보유 아이템을 모두 사용하셨습니다.");
						MainMenu.sleepTime(1000);
						flag = false;
					}
				}
			}
		}
	}
	
	//3. 이동하기
	private void move() {

		boolean b = true;
		
		while(b) {
			CharacterVO status = gs.selectChar(Login.loginCharacter.getName());
			MainMenu.clearScreen();
			System.out.println("==============================================");
			System.out.println("=                                            =");
			System.out.println("=                 Apple Tree                 =");
			System.out.println("=                                            =");
			System.out.println("==============================================");
			System.out.println("=   1.농장가기  2.온천가기  3.상점가기  4.되돌아가기  =");
			System.out.println("==============================================");
			int menu = sc.nextInt();
			
			if(menu == 1) {
				if(status.getHp() <= 0) {
					System.out.println("hp부족 농장 출입 불가!");
					MainMenu.sleepTime(1000);
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
			boolean flag = true;
			
			CharacterVO status = gs.selectChar(Login.loginCharacter.getName());
			int hp = status.getHp();
			
			List<ItemVO> itemList = new ArrayList<ItemVO>();
			itemList = is.itemList();
			
			if(hp <= 0) {
				MainMenu.clearScreen();
				System.out.println("hp가 부족합니다.......!");
				MainMenu.sleepTime(1000);
				if(!(itemList.size() == 0)) {
					System.out.println("아이템을 사용하여 hp를 회복하세요.");
					MainMenu.sleepTime(1400);
				}

				while(flag) {
					CharacterVO selectHp = gs.selectChar(Login.loginCharacter.getName());
					
					List<ItemVO> list = new ArrayList<ItemVO>();
					list = is.itemList();
					if(list.size() == 0) {
						flag = false;
					} else {
						MainMenu.clearScreen();
						System.out.println("[" + Login.loginCharacter.getName()+"]님의 보유 아이템");
						System.out.println("==============================================");
						for (int i = 0; i < list.size(); i++) {
							System.out.print(list.get(i).getItemName() + list.get(i).getCount()+ "개  ");
						}
						System.out.println();
						System.out.println("==============================================");
						System.out.println("사용하실 아이템을 직접 입력하세요. 사용하지 않으시려면 숫자(0)을 입력하세요.");
						String useItem = sc.next();
						
						int hpUP = 0;
						if(useItem.equals("달걀")) {
							hpUP = 30;
						} else if(useItem.equals("식혜")) {
							hpUP = 50;
						} else if(useItem.equals("오렌지주스")) {
							hpUP = 100;
						} else if(useItem.equals("사과")) {
							hpUP = 50;
						}
						
						if(useItem.equals("네잎클로버")) {
							System.out.println("네잎클로버는 먹을 수 없습니다.");
							MainMenu.sleepTime(1000);
							break;
						}
						
						if(useItem.equals("0")) {
							flag = false;
						} else {
							if(selectHp.getHp() >= 100) {
								System.out.println("현재 체력이 모두 회복됐습니다.");
								MainMenu.sleepTime(1000);
								flag = false;
							} else {
								for (int i = 0; i < list.size(); i++) {
									CharacterVO hpSelect = gs.selectChar(Login.loginCharacter.getName());
									if(list.get(i).getItemName().equals(useItem)) {
										is.updateDownItem(useItem);
										gs.useItemUpdateHpUp(list.get(i).getItemName(), hpSelect.getHp());
										System.out.println(useItem + "을(를) 사용하여 hp+ " + hpUP + "!");
										MainMenu.sleepTime(1000);
										MainMenu.clearScreen();
										System.out.println();
									}
								}
							}
							//사용 후 다시 조회했을 때 아이템 0개일 때 아이템창에서 삭제
							list = is.itemList();
							
							for (int i = 0; i < list.size(); i++) {
								if(list.get(i).getCount() == 0) {
									is.deleteItem(list.get(i).getItemName());
								}
							}
							
							//삭제 후 다시 조회했을 때 아이템이 없다면
							list = is.itemList();
							if(list.size() == 0) {
								System.out.println("보유 아이템을 모두 사용하셨습니다.");
								flag = false;
							}
						}
					}
				}
			}
			
			CharacterVO selectHp = gs.selectChar(Login.loginCharacter.getName()); //다시 조회
			if(selectHp.getHp() > 0) {
				MainMenu.clearScreen();
				farmInfo();
				System.out.println();
				System.out.println("1.나무심기 2.물주기 3.영양제주기 4.가지치기");
				System.out.println("5.나무정보 6.수확하기 7.나가기");
				int menu = sc.nextInt();
					if(menu == 1) {
						insertTree();
					} else if(menu == 2) {
						water();
					} else if(menu == 3) {
						nutrients();
					} else if(menu == 4) {
						pruning();
					} else if(menu == 5) {
						treeInfo();
					} else if(menu == 6) {
						getApple();
					} else if(menu == 7) {
						b = false;
					}
				} else {
					MainMenu.clearScreen();
					System.out.println("현재 hp로는 아무작업도 하실 수 없습니다. 온천 또는 아이템을 사용하여 hp를 회복하세요.");
					MainMenu.sleepTime(1500);
					b = false;
					break;
				}
			}
		}
		
	
	//농장에 사과나무 정보 출력
	private void farmInfo() {
		CharacterVO selectHp = gs.selectChar(Login.loginCharacter.getName()); //다시 조회
		System.out.println("현재 내 채력: " + selectHp.getHp());
//		String name = Login.loginCharacter.getName();
		System.out.println("==========================================\n");
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
				apple[apples.get(i).getNum() - 1] = "Ψ";
			}
		}
		
		//나무 전체 조회하여 apples에 대입
		apples = as.selectAppleList();
		for (int i = 0; i < apples.size(); i++) {
			if (apples.get(i) != null && apples.get(i).getWater() == 3 && apples.get(i).getNutrients() ==1 && apples.get(i).getPruning() ==2) {
				apple[apples.get(i).getNum() - 1] = "♥";
			}
		}
		
		for (String ap : apple) {
			System.out.print("   [" + ap + "] ");
		}
		System.out.println("\n");
		System.out.println("==========================================");
	}
	
	private void progress() {
	    System.out.printf("■");
	    MainMenu.sleepTime(200);
	    System.out.printf("■");
	    MainMenu.sleepTime(200);
	    System.out.printf("■");
	    MainMenu.sleepTime(200);
	    System.out.printf("■");
	    MainMenu.sleepTime(200);
	    System.out.printf("■");
	    MainMenu.sleepTime(200);
	    System.out.printf("■");
	    MainMenu.sleepTime(200);
	    System.out.printf("■");
	    MainMenu.sleepTime(200);
	    System.out.printf("■");
	    MainMenu.sleepTime(200);
	    System.out.printf("■");
	    MainMenu.sleepTime(200);
	    System.out.println("■");
	    MainMenu.sleepTime(200);
	}
	
	//나무심기
	private void insertTree() {
		System.out.println("몇 번째 자리에 심으시겠습니까?");
		index = sc.nextInt();
		if(index > 5 || index == 0) {
			System.out.println("해당 자리는 존재하지 않습니다.");
			MainMenu.sleepTime(1000);
		} else {
			boolean flag = true;
			
			//입력한 자리에 나무가 존재한다면
			for (int i = 0; i < apples.size(); i++) {
				if(apples.get(i).getNum() == index) {
					System.out.println("해당 자리는 나무가 이미 존재합니다.");
					MainMenu.sleepTime(1000);
					flag = false;
				}
			}

			if(flag){ //입력한 자리에 나무가 존재하지 않다면 심기
				int result = as.insertTree(index);
				if(result == 1) {
					System.out.println("나무 심는 중");
					progress();
					System.out.println("나무 심기 완료!");
					MainMenu.sleepTime(1000);
					gs.updateHpDown();
				}
			}
		}
	}

	//나무정보
	private void treeInfo() {
		System.out.println("몇 번째 나무 정보를 보시겠습니까?");
		index = sc.nextInt();
		
		vo = as.selectApple(index);
		if(vo.getNum() == index) {
			System.out.println(vo.toString());
			MainMenu.sleepTime(2000);
		} else {
			System.out.println("해당 자리는 나무가 존재하지 않습니다.");
			MainMenu.sleepTime(1000);
		}
	}
	
	//물주기
	private void water() {
		System.out.println("몇 번째 나무에 물을 주시겠습니까?");
		index = sc.nextInt();
		
		vo = as.selectApple(index);
		if(vo.getNum() == index) {
			int result = as.updateWater(index);
			if(result == 1) {
				System.out.println("물을 주는 중");
				progress();
				System.out.println("물주기 완료!");
				MainMenu.sleepTime(1000);
				gs.updateHpDown();
				//나무조회 후 5번 이상 물 주면 죽음
				vo = as.selectApple(index);
				if(vo.getWater() > 3) {
					as.deathApple(index);
					MainMenu.clearScreen();
					System.out.println("....필요량 이상의 (물)로 나무가 죽었습니다.");
					MainMenu.sleepTime(1000);
				}
			}
		} else {
			System.out.println("해당 자리는 나무가 존재하지 않습니다.");
			MainMenu.sleepTime(1000);
		}
	}
	
	//영양제
	private void nutrients() {
		System.out.println("몇 번째 나무에 영양제를 주시겠습니까?");
		index = sc.nextInt();
		
		vo = as.selectApple(index);
		if(vo.getNum() == index) {
			int result = as.updateNutrients(index);
			if(result  == 1) {
				System.out.println("영양제 주는 중");
				progress();
				System.out.println("영양제 주기 완료!");
				MainMenu.sleepTime(1000);
				gs.updateHpDown();
				//나무조회 후 1번 이상 영양제 주면 죽음
				vo = as.selectApple(index);
				if(vo.getNutrients() > 1) {
					as.deathApple(index);
					MainMenu.clearScreen();
					System.out.println("....필요량 이상의 (영양제)투입으로 나무가 죽었습니다.");
					MainMenu.sleepTime(1000);
				}
			}
		} else {
			System.out.println("해당 자리는 나무가 존재하지 않습니다.");
			MainMenu.sleepTime(1000);
		}
	}
	
	//가지치기
	private void pruning() {
		System.out.println("몇 번째 나무에 가치를 치시겠습니까?");
		index = sc.nextInt();
		vo = as.selectApple(index);
		if(vo.getNum() == index) {
			int result = as.updatePruning(index);

			if(result  == 1) {
				System.out.println("가지치기 중");
				progress();
				System.out.println("가지치기 완료!");
				MainMenu.sleepTime(1000);
				gs.updateHpDown();
				//나무조회 후 2번 이상 가지치면 죽음
				vo = as.selectApple(index);
				if(vo.getPruning() > 2) {
					as.deathApple(index);
					MainMenu.clearScreen();
					System.out.println("....필요량 이상의 (가지치기)로 나무가 죽었습니다.");
					MainMenu.sleepTime(1000);
				}
			}
		} else {
			System.out.println("해당 자리는 나무가 존재하지 않습니다.");
			MainMenu.sleepTime(1000);
		}
	}
	
	//수확하기
	private void getApple() {
		List<ItemVO> list = new ArrayList<ItemVO>();
		list = is.itemList();
		System.out.println("몇 번째 나무를 수확하시겠습니까?");
		index = sc.nextInt();
		
		vo = as.selectApple(index);
		if(vo.getNum() == index && vo.getWater() == 3 && vo.getNutrients() == 1 && vo.getPruning() == 2) {
			int result = as.deleteApple(index);
			if(result  == 1) {
				System.out.println("수확하는 중");
				progress();
				int random = (int)(Math.random()*20) + 1; //최대20개 수확
				System.out.println("!!!!! 사과(" + random + ")개를 얻었습니다. !!!!!");
				MainMenu.sleepTime(1000);
				MainMenu.clearScreen();
				gs.updateHpDown();
				gs.updateTotalApple(random); //총수확사과 업데이트
				
				boolean b = true;
				
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i).getItemName().equals("사과")) {
						b = false;
						//item창에 수확한 사과가 있을 때 update
						is.updateUpApple(random);
					}
				}
				if(b) {
				is.insertApple(random); //item창에 수확한 사과 없을 때
				}
			} 
		} else if(vo.getNum() != index) {
			System.out.println("해당 자리는 나무가 존재하지 않습니다.");
			MainMenu.sleepTime(1000);
		} else {
			System.out.println("아직 수확할 수 없습니다.");
			MainMenu.sleepTime(1000);
			System.out.println(vo.toString());
		}
	}
	
	//온천: hp 충전(시간당 10씩)
	private void spa() {
		MainMenu.clearScreen();
		System.out.println("입장료 1,000원입니다.");
		System.out.println("입장하시겠습니까? 1.예 2.아니오");
		int choice = sc.nextInt();
		if(choice == 1) {
			CharacterVO charVO = gs.selectChar(Login.loginCharacter.getName());
			if( charVO.getMoney() < 1000) {
				System.out.println("현금이 부족합니다.");
				MainMenu.sleepTime(1000);
			} else {
				MainMenu.clearScreen();
				System.out.println("==================");
				System.out.println("온천에 입장하셨습니다.");
				System.out.println("==================");
				CharacterVO status = gs.selectChar(Login.loginCharacter.getName());
				gs.updateDownMoney(1000);
				System.out.println("현재 내 hp: " + status.getHp());
				
				boolean b = true;
				
				while(b) {
					System.out.println("1.hp회복하기 2.나가기");
					int menu = sc.nextInt();
					if(menu == 1) {
						status = gs.selectChar(Login.loginCharacter.getName());
						if(status.getHp() >= 100) {
							System.out.println("hp 모두 회복완료!!!!");
							MainMenu.sleepTime(1000);
							System.out.println("안녕히 가세요.");
							MainMenu.sleepTime(500);
							b = false;
							
						} else if(status.getHp() < 100) {
							gs.updateHpUp(status.getHp());
							System.out.println("hp +10 !!!");
							MainMenu.sleepTime(200);
							MainMenu.clearScreen();
						}
					} else if(menu == 2) {
						b = false;
					} else if(menu != 1) {
						System.out.println("잘못된 선택입니다.");
						MainMenu.sleepTime(1000);
					}
				}
			}
		} else {
			System.out.println("안녕히 가세요.");
			MainMenu.sleepTime(1000);
		}
	}
	
	//상점: 아이템 사고 팔고
	private void shop() {
		ItemVO item;
		
		boolean b = true;
		MainMenu.clearScreen();
		System.out.println("==================");
		System.out.println("상점에 입장하셨습니다.");
		System.out.println("==================");
		MainMenu.sleepTime(700);
		while(b) {
			MainMenu.clearScreen();
			System.out.println("============================");
			System.out.println("1.구입하기 2.사과팔기 3.상점나가기");
			System.out.println("============================");
			int menu = sc.nextInt();
			MainMenu.clearScreen();
			if(menu == 1) {
				boolean flag = true;
				
				while(flag) {
					List<ItemVO> list = new ArrayList<ItemVO>();
					list = is.itemList();
					CharacterVO charVO = gs.selectChar(Login.loginCharacter.getName());
					
					System.out.println("현재 내 잔액: " + charVO.getMoney() + "원");
					System.out.println("==================================================================");
					System.out.println("1.달걀(hp+30 / 300원) 2.식혜(hp+50 / 500원) 3.오렌지주스(hp+100 / 1000원) \n"
							+ "4.네잎클로버(사과 전체 판매금액 20% 추가수입 / 3,000원) 5.되돌아가기");
					System.out.println("==================================================================");
					int buyMenu = sc.nextInt();
	
					if(buyMenu == 1) {
						if(charVO.getMoney() < 300) {
							System.out.println("금액이 부족합니다.");
							MainMenu.sleepTime(1000);
							break;
						} else {
							boolean c = true;
							for (int i = 0; i < list.size(); i++) {
								if(list.get(i).getItemName().equals("달걀")) {
									c = false;
									is.updateUpItem("달걀");
									break;
								}
							}
							if(c) {
								is.insertItem("달걀");
							}
							gs.updateDownMoney(300);
							System.out.println("달걀 구입완료!");
							MainMenu.sleepTime(1000);
							MainMenu.clearScreen();
						}
					} else if(buyMenu == 2) {
						if(charVO.getMoney() < 500) {
							System.out.println("금액이 부족합니다.");
							MainMenu.sleepTime(1000);
							break;
						} else {
							boolean c = true;
							for (int i = 0; i < list.size(); i++) {
								if(list.get(i).getItemName().equals("식혜")) {
									c = false;
									is.updateUpItem("식혜");
									break;
								}
							}
							if(c) {
								is.insertItem("식혜");
							}
							gs.updateDownMoney(500);
							System.out.println("식혜 구입완료!");
							MainMenu.sleepTime(1000);
							MainMenu.clearScreen();
						}
					} else if(buyMenu == 3) {
						if(charVO.getMoney() < 1000) {
							System.out.println("금액이 부족합니다.");
							MainMenu.sleepTime(1000);
							break;
						} else {
							boolean c = true;
							for (int i = 0; i < list.size(); i++) {
								if(list.get(i).getItemName().equals("오렌지주스")) {
									c = false;
									is.updateUpItem("오렌지주스");
									break;
								}
							}
							if(c) {
								is.insertItem("오렌지주스");
							}
							gs.updateDownMoney(1000);
							System.out.println("오렌지주스 구입완료!");
							MainMenu.sleepTime(1000);
							MainMenu.clearScreen();
						}
					} else if(buyMenu == 4) {
						if(charVO.getMoney() < 3000) {
							System.out.println("금액이 부족합니다.");
							MainMenu.sleepTime(1000);
							break;
						} else {
							boolean c = true;
							for (int i = 0; i < list.size(); i++) {
								if(list.get(i).getItemName().equals("네잎클로버")) {
									c = false;
									is.updateUpItem("네잎클로버");
									break;
								}
							}
							if(c) {
								is.insertItem("네잎클로버");
							}
							gs.updateDownMoney(3000);
							System.out.println("네잎클로버 구입완료!");	
							MainMenu.sleepTime(1000);
							MainMenu.clearScreen();
						}
					} else if(buyMenu == 5) {
						flag = false;
					}
				}
			} else if(menu == 2) {
				List<ItemVO> list = new ArrayList<ItemVO>();
				list = is.itemList();
				int apple = 0;
				int clover = 0;
				//사과 & 클로버 존재하는지 확인하는 for문
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i).getItemName().equals("사과")) {
						apple = list.get(i).getCount();
					}
					if(list.get(i).getItemName().equals("네잎클로버")) {
						clover = list.get(i).getCount();
					}
				}
				
				if(apple <= 0) {
					System.out.println("현재 보유하신 사과가 없습니다.");
					MainMenu.sleepTime(1000);
				} else {
					System.out.println("몇개를 파시겠습니까? (1개당:1,000원)");
					System.out.println("현재 내 보유 사과: " + apple);
					int sell = sc.nextInt();
					if(sell > apple) {
						System.out.println("보유하신 사과보다 많이 입력하셨습니다.");
						MainMenu.sleepTime(500);
					}else {
						//클로버가 있다면
						if(clover >= 1) {
							gs.updateUpMoney((sell*1000)+(int)((sell*1000)*0.2));
							is.updateDownApple(sell);
							is.updateDownItem("네잎클로버");
							System.out.println(sell * 1000 + "원 획득!");
							System.out.println("네잎클로버 효과로 " + (int)((sell * 1000) * 0.2) + "원 추가 획득!");
							MainMenu.sleepTime(1300);
						} else {
							gs.updateUpMoney((sell*1000));
							is.updateDownApple(sell);
							System.out.println(sell * 1000 + "원 획득!");
							MainMenu.sleepTime(1300);
						}
						
						//판매 후 다시 조회했을 때 사과||네잎클로버 0개일 때 아이템창에서 삭제
						list = is.itemList();
						
						for (int i = 0; i < list.size(); i++) {
							if(list.get(i).getCount() == 0) {
								is.deleteItem(list.get(i).getItemName());
							}
						}
					}
				}	
				
			} else if(menu == 3) {
				b = false;
			}
		}


	}
	
	public void run() {
		MainMenu.clearScreen();
		System.out.print("접속중");
	    System.out.printf(".");
	    MainMenu.sleepTime(300);
	    System.out.printf(".");
	    MainMenu.sleepTime(300);
	    System.out.printf(".");
	    MainMenu.sleepTime(300);
	    System.out.printf(".");
	    MainMenu.sleepTime(300);
	    System.out.printf(".");
	    MainMenu.sleepTime(300);
		gameMenu();
	}
}
