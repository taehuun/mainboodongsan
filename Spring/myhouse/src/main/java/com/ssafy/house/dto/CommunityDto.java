package com.ssafy.house.dto;

import java.util.ArrayList;
import java.util.List;

public class CommunityDto {
	private int articleNo;
	private String email;
	private String subject;
	private String content;
	private int hit;
	private String registerTime;
	private int ripple;
	private String favorDong;
	private List<RippleDto> list;
	
	public int getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public int getRipple() {
		return ripple;
	}
	public void setRipple(int ripple) {
		this.ripple = ripple;
	}
	public String getFavorDong() {
		return favorDong;
	}
	public void setFavorDong(String favorDong) {
		this.favorDong = favorDong;
	}
	public List<RippleDto> getList() {
		return list;
	}
	public void setList(List<RippleDto> list2) {
		this.list = list2;
	}
	
	
	
	
}