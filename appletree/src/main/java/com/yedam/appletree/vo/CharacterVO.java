package com.yedam.appletree.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterVO {
	private String name;
	private int hp;
	private int totalApple;
	@Override
	public String toString() {
		return  "[" + name + "]님의 현재상태\n"
				+ "체력 : " + hp + "\n"
				+ "총 수확한 사과 : " + totalApple;
		}
}