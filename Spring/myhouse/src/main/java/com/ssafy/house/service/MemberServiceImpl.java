package com.ssafy.house.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import com.ssafy.house.dto.FileInfoDto;

import com.ssafy.house.dto.MemberDto;
import com.ssafy.house.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	private MemberMapper memberMapper;
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	public MemberServiceImpl(MemberMapper memberMapper) {
		super();
		this.memberMapper = memberMapper;
	}

	
	@Override
	public MemberDto login(String email, String pass) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("pass", pass);
		return memberMapper.login(map);
	}
	
	@Override
	public MemberDto getMember(String email) {
		return memberMapper.getMember(email);
	}
	
	@Override
	public MemberDto getMemberByToken(String token) {
		// TODO Auto-generated method stub		
		return memberMapper.getMemberByToken(token);
	}

	@Override
	public void join(MemberDto memberDto) {
		// TODO Auto-generated method stub
		memberMapper.joinMember(memberDto);
	}

	@Override
	public void modify(MemberDto memberDto, String email) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
//		map.put("memberDto", memberDto);
		map.put("name", memberDto.getName());
		map.put("pass", memberDto.getPass());
		map.put("email", email);
		map.put("user_img", memberDto.getUser_img());
		logger.info("memberServiceImpl >> modify >> map : " + map);
		memberMapper.modifyMember(map);
	}
	
	@Override
	public void deleteMember(String email) {
		// TODO Auto-generated method stub
		memberMapper.deleteMember(email);
	}

	@Override
	public void saveRefreshToken(String email, String refreshToken) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("token", refreshToken);
		memberMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String email) throws Exception {
		// TODO Auto-generated method stub
		return memberMapper.getRefreshToken(email);
	}

	@Override
	public void deleRefreshToken(String email) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("token", null);
		memberMapper.deleteRefreshToken(map);
		
	}


	@Override
	public FileInfoDto fileInfo(String email) throws Exception {
		// TODO Auto-generated method stub
		return memberMapper.fileInfo(email);
	}

	

}
