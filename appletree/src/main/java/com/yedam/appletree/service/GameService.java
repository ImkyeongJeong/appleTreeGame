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
	//money다운 업데이트
	int updateDownMoney(int money);
	//money업 업데이트
	int updateUpMoney(int money);
	//현재상태 총 사과 수확 업데이트 ㅇ
	int updateTotalApple(int random);
	//현재상태 체력 -10 업데이트 ㅇ
	int updateHpDown();
	//온천 현재상태 체력 +10 업데이트 ㅇ
	int updateHpUp(int hp);
	//아이템 사용하여 체력 +10 업데이트 
	int useItemUpdateHpUp(String itemName, int hp);
}
