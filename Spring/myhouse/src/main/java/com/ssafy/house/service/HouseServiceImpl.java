package com.ssafy.house.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.house.dto.DongCode;
import com.ssafy.house.dto.HouseDeal;
import com.ssafy.house.dto.HouseDealResult;
import com.ssafy.house.dto.HouseInfo;
import com.ssafy.house.mapper.HouseMapper;

@Service
public class HouseServiceImpl implements HouseService {
	
	private HouseMapper houseMapper;
	private HouseServiceImpl(HouseMapper houseMapper) {
		super();
		this.houseMapper = houseMapper;
	};
	
	
	
	@Override
	public List<DongCode> getSidoList() {
		return houseMapper.getSidoList();
	}


	@Override
	public List<DongCode> getGugunList(String sidoName) {
		// TODO Auto-generated method stub
		return houseMapper.getGugunList(sidoName);
	}


	@Override
	public List<DongCode> getDongList(String sidoName, String gugunName) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("sidoName", sidoName);
		map.put("gugunName", gugunName);
		return houseMapper.getDongList(map);
	}
	
	
	
	@Override
	public List<HouseDealResult> getDealList(String dongCode, String dealYear, int order, String email) {
		// TODO Auto-generated method stub
		
		// DB에서 동코드, 거래년도, 거래월 에 해당하는 거래내역 불러오기
		Map<String, String> map = new HashMap<String, String>();
		map.put("dongCode", dongCode);
		map.put("dealYear", dealYear);
		map.put("email", email);
		List<HouseDealResult> dealList = houseMapper.getDealList(map);
		
		if (dealList == null) {
			return dealList;
		}
		
		// 선택한 order 기준으로 정렬하기
		// - order = 0 : 정렬 기준 없음
		// - order = 1 : 아파트 이름 순으로 정렬
		// - order = 2 : 거래 금액 순으로 정렬
		if (order == 0) {
			return dealList;
		}
		
		else {
//			mergeSort(dealList, 0, dealList.size()-1, order);
			return dealList;
		}
//		return dealList;
	}
	@Override
	public List<HouseDealResult> getDealListFavor(int order, String email) {
		// TODO Auto-generated method stub
		
		// DB에서 동코드, 거래년도, 거래월 에 해당하는 거래내역 불러오기
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		List<HouseDealResult> dealList = houseMapper.getDealListFavor(map);
		
		if (dealList == null) {
			return dealList;
		}
		
		// 선택한 order 기준으로 정렬하기
		// - order = 0 : 정렬 기준 없음
		// - order = 1 : 아파트 이름 순으로 정렬
		// - order = 2 : 거래 금액 순으로 정렬
		if (order == 0) {
			return dealList;
		}
		
		else {
//			mergeSort(dealList, 0, dealList.size()-1, order);
			return dealList;
		}
//		return dealList;
	}


	@Override
	public void mergeSort(List<HouseInfo> dealList, int start, int end, int order) {
		// TODO Auto-generated method stub
        if(start == end) return;
        int mid = start + (end - start) / 2;
        
        mergeSort(dealList, start, mid, order);
        mergeSort(dealList, mid + 1, end, order);
        
        merge(dealList, start, end, order);
	}


	@Override
	public void merge(List<HouseInfo> dealList, int start, int end, int order) {
		// TODO Auto-generated method stub
		HouseDeal[] result = new HouseDeal[end - start + 1];
		int mid = (start + end) / 2;
		int i = start;
	    int j = mid + 1;
	    int k = 0;

//	    while (i <= mid && j <= end) {
//	        if (order == 1) {
//	            if (dealList.get(i).getApartmentName().compareTo(dealList.get(j).getApartmentName()) < 0) {
//	                result[k++] = dealList.get(i++);
//	            } else {
//	                result[k++] = dealList.get(j++);
//	            }
//	        } else if (order == 2) {
//	            if (dealList.get(i).getDealAmount().compareTo(dealList.get(j).getDealAmount()) < 0) {
//	                result[k++] = dealList.get(i++);
//	            } else {
//	                result[k++] = dealList.get(j++);
//	            }
//	        }
//	    }
//
//	    while (i <= mid) {
//	        result[k++] = dealList.get(i++);
//	    }
//
//	    while (j <= end) {
//	        result[k++] = dealList.get(j++);
//	    }
//
//	    // 병합된 결과를 원래 리스트에 복사
//	    for (int l = 0; l < k; l++) {
//	        dealList.set(start + l, result[l]);
//	    }
	}



	@Override
	public void favorRegister(String email, long aptNo, long aptCode) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("aptNo", aptNo);
		map.put("aptCode", aptCode);
		houseMapper.favorRegister(map);
		
	}



	@Override
	public void favorDelete(String email, long aptNo, long aptCode) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("aptNo", aptNo);
		map.put("aptCode", aptCode);
		houseMapper.favorDelete(map);
		
	}



	@Override
	public List<Integer> getDealYear(long aptCode) {
		// TODO Auto-generated method stub
		List<Integer> list = houseMapper.getDealYear(aptCode);
		return list;
	}



	@Override
	public List<HouseDealResult> getDealByYear(long aptCode, int dealYear) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aptCode", aptCode);
		map.put("dealYear", dealYear);
		List<HouseDealResult> list = houseMapper.getDealByYear(map);
		return list;
	}


}
