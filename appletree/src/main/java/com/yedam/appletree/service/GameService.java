package com.yedam.appletree.service;

import com.yedam.appletree.vo.CharacterVO;

public interface GameService {
	//캐릭터 생성 ㅇ
	int insertChar(String name);
	//닉네임 중복체크 ㅇ
	String nameCheck(String name);
	//현재상태 보기 ㅇ
	CharacterVO selectChar(String name);
	//보유아이템, 돈, 체력 수정
	int updateChar(CharacterVO vo);
	//현재상태 총 사과 수확 업데이트
	int updateApple(String name);
	//형재상태 체력 업데이트
	int updateHp();
}
