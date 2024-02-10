package com.ssafy.house.service;

import java.util.List;
import java.util.Map;

import com.ssafy.house.dto.CommunityDto;
import com.ssafy.house.dto.RippleDto;

public interface CommunityService {

	void writeArticle(CommunityDto communityDto) throws Exception;
	List<CommunityDto> listArticle(Map<String, String> map) throws Exception;
	CommunityDto getArticle(int articleNo) throws Exception;
	
	List<RippleDto> getlist(int articleNo) throws Exception;
	void rippleWrite(RippleDto ripple) throws Exception;
	void updateRipple(int articleNo) throws Exception;
	void deleteRipple(int rippleNo) throws Exception;
	
	void updateHit(int articleNo) throws Exception;
	void modifyArticle(CommunityDto communityDto) throws Exception;
	void deleteArticle(int articleNo) throws Exception;
}
