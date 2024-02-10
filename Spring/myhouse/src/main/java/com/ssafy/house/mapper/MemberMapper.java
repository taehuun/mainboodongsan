package com.ssafy.house.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.dto.FileInfoDto;
import com.ssafy.house.dto.MemberDto;

@Mapper
public interface MemberMapper {
	public MemberDto login(Map<String, String> map);
	public MemberDto getMember(String email);
	public MemberDto getMemberByToken(String token);
	public void joinMember(MemberDto memberDto);
	public void deleteMember(String email);
	public void modifyMember(Map<String, String> map);
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String email) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;

	FileInfoDto fileInfo(String email) throws Exception;

}
