package com.yedam.appletree.vo;

import com.yedam.appletree.Login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemVO {
	private String itemName;
	private int count;

	@Override
	public String toString() {
		return  itemName + " " + count + "개 \n";
		}
}
