package com.ssafy.house.service;


import com.ssafy.house.dto.FileInfoDto;

import com.ssafy.house.dto.MemberDto;

public interface MemberService {
	
	public MemberDto login(String email, String pass);
	public MemberDto getMember(String email);
	public MemberDto getMemberByToken(String token);
	public void deleteMember(String email);
	public void join(MemberDto memberDto);
	public void modify(MemberDto memberDto, String email);
	void saveRefreshToken(String email, String refreshToken) throws Exception;
	Object getRefreshToken(String email) throws Exception;
	void deleRefreshToken(String email) throws Exception;

	FileInfoDto fileInfo(String email) throws Exception;

}
