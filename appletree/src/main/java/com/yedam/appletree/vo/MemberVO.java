package com.yedam.appletree.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor	//기본 생성자
@AllArgsConstructor //모든 필드값을 파라미터로 받는 생성자
public class MemberVO {
	private String id;
	private String pwd;
	private String mName;
	private String phone;
	private String email;
	
	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", mName=" + mName + ", phone=" + phone + ", email=" + email + "]";
	}
}
