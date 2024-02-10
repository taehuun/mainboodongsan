package com.ssafy.house.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.house.dto.CommunityDto;
import com.ssafy.house.dto.RippleDto;
import com.ssafy.house.mapper.CommunityMapper;

@Service
public class communityServiceImpl implements CommunityService {

	private CommunityMapper communityMapper;

	@Autowired
	public communityServiceImpl(CommunityMapper communityMapper) {
		super();
		this.communityMapper = communityMapper;
	}

	@Override
	public void writeArticle(CommunityDto communityDto) throws Exception {
		communityMapper.writeArticle(communityDto);
		
	}

	@Override
	public List<CommunityDto> listArticle(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return communityMapper.listArticle(map);
	}

	@Override
	public CommunityDto getArticle(int articleNo) throws Exception {
		// TODO Auto-generated method stub
		return communityMapper.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {

		communityMapper.updateHit(articleNo);
	}

	@Override
	public void modifyArticle(CommunityDto communityDto) throws Exception {
		
		communityMapper.modifyArticle(communityDto);
	}

	@Override
	public void deleteArticle(int articleNo) throws Exception {
	
		communityMapper.deleteArticle(articleNo);
	}

	@Override
	public List<RippleDto> getlist(int articleNo) throws Exception {
		
		return communityMapper.getlist(articleNo);
	}

	@Override
	public void rippleWrite(RippleDto ripple) throws Exception {
		
		communityMapper.rippleWrite(ripple);
	}

	@Override
	public void updateRipple(int articleNo) throws Exception {
		
		communityMapper.updateRipple(articleNo);
	}

	@Override
	public void deleteRipple(int rippleNo) throws Exception {
		communityMapper.deleteRipple(rippleNo);
		
	}
	
	
}
