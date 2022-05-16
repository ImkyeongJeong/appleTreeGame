package com.yedam.appletree.vo;

import java.util.ArrayList;
import java.util.List;

import com.yedam.appletree.service.ItemService;
import com.yedam.appletree.serviceImpl.ItemServiceImpl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterVO {
	private ItemService is = new ItemServiceImpl();
	private String name;
	private int hp;
	private int money;
	private int totalApple;
	
	
	@Override
	public String toString() {
		List<ItemVO> list = new ArrayList<ItemVO>();
		list = is.itemList();
		int apple = 0;
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getItemName().equals("사과")) {
				apple = list.get(i).getCount();
			}
		}
		
		return   "체력 : " + hp + "\n"
				+ "현금 : " + money + "\n"
				+ "현재 내 보유 사과: " + apple + "\n"
				+ "총 수확한 사과 : " + totalApple;
		}
}