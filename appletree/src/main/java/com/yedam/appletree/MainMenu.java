package com.yedam.appletree;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.yedam.appletree.service.GameService;
import com.yedam.appletree.service.MemberService;
import com.yedam.appletree.serviceImpl.GameServiceImpl;
import com.yedam.appletree.serviceImpl.MemberServiceImpl;
import com.yedam.appletree.vo.CharacterVO;
import com.yedam.appletree.vo.MemberVO;

public class MainMenu {
	private Scanner sc = new Scanner(System.in);
	private MemberService dao = new MemberServiceImpl();
	private Login login = new Login();
	private Member member = new Member();
	private UserMenu userMenu = new UserMenu();
	private GameService gs = new GameServiceImpl();
	public static void clearScreen() {
		for (int i = 0; i < 40; i++) {
			System.out.println();
		}
	}
	
	public static void sleepTime(int sec) {
		long c = System.currentTimeMillis();
			while (true) {
			if ((System.currentTimeMillis() - c) >= sec) {
				break;
			}
		}
	}

	private void mainTitle() {

		System.out.println("==============================================");
		System.out.println("=                                            =");
		System.out.println("=                 Apple Tree                 =");
		System.out.println("=                                            =");
		System.out.println("==============================================");
		System.out.println("=       1.로그인    2.회원가입    3.랭킹보기       =");
		System.out.println("==============================================");
	}
	
	private void mainMenu() {
		boolean b = true;
		while(true) {
			mainTitle();
			System.out.println("메뉴를 선택하세요.");
			int menu = sc.nextInt();
			switch(menu) {
			case 1:
				login.run();
				break;
			case 2:
				member.run();
				break;
			case 3:
				rank();
				break;
			}
			
			//로그인이 됐다면 게임화면 실행(접속유저id가 "0"이 아니라면)
			if(!Login.loginMember.getId().equals("0")) {
				userMenu.run();
			} else {
				mainMenu();
			}
		}
	}
	
	private void rank() {
		List<CharacterVO> list = new ArrayList<CharacterVO>();
		list = gs.selectRank();
		clearScreen();
		System.out.println("==============================================");
		System.out.println("=              Apple Tree 랭킹                =");
		System.out.println("==============================================");
		System.out.println();
		for (int i = 0; i < list.size(); i++) {
			System.out.println("\t\t" + (i+1) + "등 [" + list.get(i).getName() +"]님");
			System.out.println("\t\t총 수확한 사과: " + list.get(i).getTotalApple());
			System.out.println();
		}
		System.out.println("==============================================");
		Login.loginMember.setId("0");
	}
	
	public void run() {
		mainMenu();
	}
}
