package com.yedam.appletree.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppleVO {
	private int index;		//나무 심은 자리번호
	private int water;		//물주기
	private int nutrients;	//가지치기
	private int pruning;	//영양제
}
