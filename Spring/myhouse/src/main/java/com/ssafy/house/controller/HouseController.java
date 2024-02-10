package com.ssafy.house.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.house.dto.DongCode;
import com.ssafy.house.dto.HouseDeal;
import com.ssafy.house.dto.HouseDealResult;
import com.ssafy.house.dto.HouseInfo;
import com.ssafy.house.service.HouseService;

@RestController
@RequestMapping("/addrs")
@CrossOrigin("*")
public class HouseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	private HouseService houseService;
	
	@Autowired
	public HouseController(HouseService houseService) {
		this.houseService = houseService;
	}
	
	
	@GetMapping(value = "/")
	public List<DongCode> sido() {
		List<DongCode> list = houseService.getSidoList();
		return list;
	}
	
	
	@GetMapping(value="/{sido}")
	public List<DongCode> gugun(@PathVariable("sido") String sido) {
		List<DongCode> list = houseService.getGugunList(sido);
		return list;
	}
	
	
	@GetMapping(value="/{sido}/{gugun}")
	public List<DongCode> dong(@PathVariable("sido") String sido, @PathVariable("gugun") String gugun){
		List<DongCode> list = houseService.getDongList(sido, gugun);
		
		return list;
	}
	
	@GetMapping(value="/deals/{dongCode}")
	public List<HouseDealResult> getDeals(@PathVariable("dongCode") String dongCode, String year, String email) {
		List<HouseDealResult> list = houseService.getDealList(dongCode, year, 0, email);
		return list;
	}
	
	@GetMapping(value="/dealsFavor")
	public List<HouseDealResult> getDealFavor(String email) {
		List<HouseDealResult> list = houseService.getDealListFavor(0, email);
		return list;
	}
	
//	@GetMapping(value="/getDealYear")
//	public List<HouseDealResult> getDealYear(String email) {
//		List<HouseDealResult> list = houseService.getDealListFavor(0, email);
//		return list;
//	}
	
	@GetMapping(value="/getDealYear")
	public ResponseEntity<List<Integer>> getDealYear(long aptCode) {
		logger.info("HouseController >> getDealYear :: Connected.");
		logger.info("aptCode : "+aptCode);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		List<Integer> list = null;
		try {
			list = houseService.getDealYear(aptCode);	
			logger.info("HouseController >> getDealYear try :: list is ==" + list);
			resultMap.put("yearList", list);
			status = HttpStatus.OK;
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
//		
		return new ResponseEntity<List<Integer>>(list, status);
		
	}
	
	@GetMapping(value="/getDealByYear")
	public ResponseEntity<List<HouseDealResult>> getDealByYear(long aptCode, int dealYear) {
		logger.info("HouseController >> getDealByYear :: Connected.");
		logger.info("aptCode : "+aptCode);
		logger.info("dealYear : "+dealYear);
		
		HttpStatus status = HttpStatus.ACCEPTED;
		List<HouseDealResult> list = null;
		try {
			list = houseService.getDealByYear(aptCode, dealYear);
			status = HttpStatus.OK;
		} catch (Exception e) {
			// TODO: handle exception
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<HouseDealResult>>(list, status);
		
	}
	
	@PostMapping(value="/register")
	public ResponseEntity<Map<String, Object>> favorRegister(
			@RequestBody HouseDealResult houseDealResult, @RequestParam String email) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		try {
			houseService.favorRegister(email, houseDealResult.getNo(), houseDealResult.getAptCode());
			status = HttpStatus.OK;
		} catch (Exception e) {
			logger.error("HouseController >> favorRegister :: error");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@PostMapping(value="/delete")
	public ResponseEntity<Map<String, Object>> favorDelete(
			@RequestBody HouseDealResult houseDealResult, @RequestParam String email) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		try {
			houseService.favorDelete(email, houseDealResult.getNo(), houseDealResult.getAptCode());
			status = HttpStatus.OK;
		} catch (Exception e) {
			logger.error("HouseController >> favorDelete :: error");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
}
