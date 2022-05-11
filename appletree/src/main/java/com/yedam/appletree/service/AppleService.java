package com.yedam.appletree.service;

import java.util.List;

import com.yedam.appletree.vo.AppleVO;

public interface AppleService {
	//사과나무 심기
	int insertTree(int index);
	//사과나무 정보보기(남은 행동 수)
	AppleVO selectApple(int num);
	//사과나무 전체보기
	List<AppleVO> selectAppleList(); 
	//사과나무 업데이트(물주기, 가지치기, 영양제 등)
	int updateApple();
	//사과나무 수확 후 제거
	int deleteApple();
}
