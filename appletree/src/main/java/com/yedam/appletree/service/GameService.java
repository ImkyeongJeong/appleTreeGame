package com.yedam.appletree.service;

import com.yedam.appletree.vo.CharacterVO;

public interface GameService {
	//캐릭터 생성
	int insertChar(String name);
	//닉네임 중복체크
	String nameCheck(String name);
	//현재상태 보기 / 캐릭터 있는지 확인하기
	CharacterVO selectChar(String id);
	//보유아이템, 돈, 체력 수정
	int updateChar(CharacterVO character);
	//캐릭터 삭제
	int deleteChar(CharacterVO character);
}
