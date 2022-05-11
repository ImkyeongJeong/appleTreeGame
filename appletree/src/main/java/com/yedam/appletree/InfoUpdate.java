package com.yedam.appletree;

import java.util.Scanner;

import com.yedam.appletree.service.MemberService;
import com.yedam.appletree.serviceImpl.MemberServiceImpl;
import com.yedam.appletree.vo.MemberVO;

public class InfoUpdate {
	private Scanner sc = new Scanner(System.in);
	private MemberService ms = new MemberServiceImpl();
	
	private void updateInfoTitle() {
		System.out.println("==============================================");
		System.out.println("=   1.비밀번호   2.연락처   3.EMAIL   4.되돌아가기  =");
		System.out.println("==============================================");
	}
	
	//접속 user정보
	private void loginInfo() {
		System.out.println("ID : " + Login.loginMember.getId());
		System.out.println("이   름: " + Login.loginMember.getMName());
		System.out.println("연 락 처: " + Login.loginMember.getPhone());
		System.out.println("이 메 일: " + Login.loginMember.getEmail());
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
				//되돌아가기
				break;
			}
		}
		
		//비밀번호 변경
		private void cgPwd() {
			System.out.println("현재 비밀번호를 입력하세요.");
			String pwd = sc.next();
			System.out.println("변경할 비밀번호를 입력하세요.");
			String cgPwd = sc.next();
			
			String[] checkIdPwd = ms.check(Login.loginMember.getId());
			String checkPwd = checkIdPwd[1];
			
			if(checkPwd != null && checkPwd.equals(pwd)) {
				Login.loginMember.setPwd(cgPwd);
				ms.updateMember(Login.loginMember);
				System.out.println("비밀번호 변경완료!");
			} else if(!checkPwd.equals(pwd)) { 
				System.out.println("현재 비밀번호가 맞지 않습니다.");
			}
		}
		
		//연락처 변경
		private void cgPhone() {
			System.out.println("변경할 연락처를 입력하세요.");
			String cgPhone = sc.next();
			Login.loginMember.setPhone(cgPhone);
			ms.updateMember(Login.loginMember);
			System.out.println("수정완료!");
		}
		
		//이메일 변경
		private void cgEmail() {
			System.out.println("변경할 메일을 입력하세요.");
			String cgEmail = sc.next();
			Login.loginMember.setPhone(cgEmail);
			ms.updateMember(Login.loginMember);
			System.out.println("수정완료!");
		}

		public void run() {
			updateInfo();
		}
}
