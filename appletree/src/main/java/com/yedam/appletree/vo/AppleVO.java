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
	private int nutrients;	//영양제
	private int pruning;	//가지치기
	
	@Override
	public String toString() {
		return	"=============================== \n"
				+ "나무번호 : " + num + "\n"
				+ "    물 : (3/" + water + ")\n"
				+ " 영양제 : (1/" + nutrients + ")\n"
				+ "가지치기 : (2/" + pruning + ")\n"
				+ "===============================";
		}
	}
