package com.yedam.appletree.service;

import java.util.List;

import com.yedam.appletree.vo.ItemVO;

public interface ItemService {
	//아이템 : 사과, 영양제, 식혜, 네잎클로버
	//아이템 테이블 (캐릭터이름, 아이템이름, 개수, 돈) 입력
	int insertItem();
	//아이템 모든 정보 조회
	List<ItemVO> itemList();
	//사과수확, 아이템 사용, 구입시 item이름을 받아 count 수정
	int updateItem(int itemName);
	//모두 소진시 삭제 
	int deleteItem(int itemName);
}
