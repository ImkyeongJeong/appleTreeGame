package com.yedam.appletree.service;

import java.util.List;

import com.yedam.appletree.vo.ItemVO;

public interface ItemService {
	//아이템 : 사과, 영양제, 식혜, 네잎클로버
	//아이템 테이블 (캐릭터이름, 아이템이름, 개수, 돈) 입력
	int insertItem(String item);
	//사과 첫 수확했을 때 item테이블에 추가
	int insertApple(int apple);
	//사과 수확 후 업데이트
	int updateUpApple(int apple);
	//사과 판매 후 업데이트
	int updateDownApple(int apple);
	//아이템 모든 정보 조회
	List<ItemVO> itemList();
	//사과수확, 아이템 구매했을 때 count 증가
	int updateUpItem(String itemName);
	//사과판매, 아이템 사용했을 때 count 감소
	int updateDownItem(String itemName);
	//아이템이 0일 때 삭제
	int deleteItem(String itemName);
}