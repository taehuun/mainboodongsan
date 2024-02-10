package com.ssafy.house.service;

import java.util.List;

import com.ssafy.house.dto.DongCode;
import com.ssafy.house.dto.HouseDeal;
import com.ssafy.house.dto.HouseDealResult;
import com.ssafy.house.dto.HouseInfo;

public interface HouseService {
	
	public List<DongCode> getSidoList();
	public List<DongCode> getGugunList(String sidoName);
	public List<DongCode> getDongList(String sidoName, String gugunName);
	
	public List<HouseDealResult> getDealList(String dongCode, String dealYear, int order, String email); 
	public List<HouseDealResult> getDealListFavor(int order, String email); 
	public List<Integer> getDealYear(long aptCode);
	public List<HouseDealResult> getDealByYear(long aptCode, int dealYear);
	
	public void favorRegister(String email, long aptNo, long aptCode);
	public void favorDelete(String email, long aptNno, long aptCode);
	
	
	// 병합 정렬을 위한 메서드
	public void mergeSort(List<HouseInfo> dealList, int start, int end, int order);
	public void merge(List<HouseInfo> dealList, int start, int end, int order);


}
