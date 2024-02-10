package com.ssafy.house.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.house.dto.DongCode;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.house.dto.BoardDto;
import com.ssafy.house.mapper.BoardMapper;


@Service
public class BoardServiceImpl implements BoardService {

	private BoardMapper boardMapper;

	@Autowired
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}

	@Override
	public void writeArticle(BoardDto boardDto) throws Exception {
		boardMapper.writeArticle(boardDto);
	}

	@Override
	public List<BoardDto> listArticle(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return boardMapper.listArticle(map);
	}

	@Override
	public BoardDto getArticle(int articleNo) throws Exception {
		// TODO Auto-generated method stub
		return boardMapper.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		// TODO Auto-generated method stub
		boardMapper.updateHit(articleNo);
		
	}

	@Override
	public void modifyArticle(BoardDto boardDto) throws Exception {
		// TODO Auto-generated method stub
		boardMapper.modifyArticle(boardDto);
	}

	@Override
	public void deleteArticle(int articleNo) throws Exception {
		// TODO Auto-generated method stub
		boardMapper.deleteArticle(articleNo);
	}

}
