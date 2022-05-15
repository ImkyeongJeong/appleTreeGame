package com.yedam.appletree.service;

import java.util.List;

import com.yedam.appletree.vo.AppleVO;

public interface AppleService {
	//사과나무 심기
	int insertTree(int num);
	//사과나무 정보보기(남은 행동 수)
	AppleVO selectApple(int num);
	//접속 user 사과나무 전체보기
	List<AppleVO> selectAppleList(); 
	//사과나무 업데이트(물주기)
	int updateWater(int num);
	//사과나무 업데이트(가지치기)
	int updateNutrients(int num);
	//사과나무 업데이트(영양제)
	int updatePruning(int num);
	//사과나무 수확 후 제거(물5, 가지치기2, 영양제1)
	int deleteApple(int num);
	//행동량 보다 많을 경우 나무 죽음
	int deathApple(int num);
}
