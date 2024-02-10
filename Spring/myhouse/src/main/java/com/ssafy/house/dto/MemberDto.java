package com.ssafy.house.dto;

public class MemberDto {

	private String email;
	private String pass;
	private String name;
	private String phoneNumber;
	private String joinDate;
	private String token;
	private String user_img;

	public String getUser_img() {
		return user_img;
	}

	public void setUser_img(String user_img) {
		this.user_img = user_img;
	}

	//파일 업로드 정보 담을 객체 추가
	private FileInfoDto fileInfo;
		
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public FileInfoDto getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfoDto fileInfo) {
		this.fileInfo = fileInfo;
	}

	@Override
	public String toString() {
		return "MemberDto [email=" + email + ", pass=" + pass + ", name=" + name + ", phoneNumber=" + phoneNumber
				+ ", joinDate=" + joinDate + ", token=" + token + "]";
	}
	
	
	
}
