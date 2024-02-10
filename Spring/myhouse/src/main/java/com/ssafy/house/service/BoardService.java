package com.ssafy.house.service;

import java.util.List;
import java.util.Map;

import com.ssafy.house.dto.BoardDto;
import com.ssafy.house.dto.DongCode;

public interface BoardService {
	
	void writeArticle(BoardDto boardDto) throws Exception;
	List<BoardDto> listArticle(Map<String, String> map) throws Exception;
	BoardDto getArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	void modifyArticle(BoardDto boardDto) throws Exception;
	void deleteArticle(int articleNo) throws Exception;
	

}
