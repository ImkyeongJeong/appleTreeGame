package com.yedam.appletree.service;

import com.yedam.appletree.vo.CharacterVO;
import com.yedam.appletree.vo.ItemVO;

public interface GameService {
	//캐릭터 생성 ㅇ
	int insertChar(String name);
	//닉네임 중복체크 ㅇ
	String nameCheck(String name);
	//현재상태 보기 ㅇ
	CharacterVO selectChar(String name);
	//아이템조회
	ItemVO selectItem(String name);

	//현재상태 총 사과 수확 업데이트 ㅇ
	int updateApple(int random);
	//현재상태 체력 -10 업데이트 ㅇ
	int updateHpDown();
	//현재상태 체력 +10 업데이트 ㅇ
	int updateHpUp(int hp);
}
