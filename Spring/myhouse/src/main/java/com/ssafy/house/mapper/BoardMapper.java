package com.ssafy.house.mapper;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.dto.BoardDto;

@Mapper
public interface BoardMapper {
	
	void writeArticle(BoardDto boardDto) throws Exception;
	List<BoardDto> listArticle(Map<String, String> map) throws Exception;
	BoardDto getArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	void modifyArticle(BoardDto boardDto) throws Exception;
	void deleteArticle(int articleNo) throws Exception;
	
//	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;

}
