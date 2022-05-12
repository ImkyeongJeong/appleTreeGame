package com.yedam.appletree.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemVO {
	private String itemName;
	private int count;
	private int money;
	
	@Override
	public String toString() {
		return  itemName + " " + count + "개 \n"
				+ "돈 : " + money ;
		}
}
