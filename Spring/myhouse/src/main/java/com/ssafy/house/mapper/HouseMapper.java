package com.ssafy.house.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.dto.DongCode;
import com.ssafy.house.dto.HouseDeal;
import com.ssafy.house.dto.HouseDealResult;
import com.ssafy.house.dto.HouseInfo;

@Mapper
public interface HouseMapper {
	public List<DongCode> getSidoList();
	public List<DongCode> getGugunList(String sidoName);
	public List<DongCode> getDongList(Map<String, String> map);
	
	public List<HouseDealResult> getDealList(Map<String, String> map);
	public List<HouseDealResult> getDealListFavor(Map<String, String> map);
	public List<Integer> getDealYear(long aptCode);
	public List<HouseDealResult> getDealByYear(Map<String, Object> map);
	
	public void favorRegister(Map<String, Object> map);
	public void favorDelete(Map<String, Object> map);
	
	
}
