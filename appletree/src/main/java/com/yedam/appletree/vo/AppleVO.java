package com.yedam.appletree.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppleVO {
	private int num;		//나무 심은 자리번호
	private int water;		//물주기
	private int nutrients;	//가지치기
	private int pruning;	//영양제
	
	@Override
	public String toString() {
		return  "번호 : " + num + "\n"
				+ "물 : " + water + "\n"
				+ "영양제 : " + pruning + "\n"
				+ "가지치기 : " + nutrients ;
		}
	}
