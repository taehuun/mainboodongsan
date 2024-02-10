package com.ssafy.house.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.house.dto.CommunityDto;
import com.ssafy.house.dto.RippleDto;
import com.ssafy.house.service.CommunityService;

@RestController
@RequestMapping("/community")
@CrossOrigin("*")
public class CommunityController {

	private CommunityService communityService;

	public CommunityController(CommunityService communityService) {
		super();
		this.communityService = communityService;
	}
	
	@GetMapping("/list")
	public List<CommunityDto> list(@RequestParam Map<String, String> map) throws Exception{
		List<CommunityDto> list = communityService.listArticle(map);
//		System.out.println(list.toString());
		return list;
	}
	
	@GetMapping("/getArticle")
	public CommunityDto getArticle(@RequestParam int articleNo) throws Exception{
		CommunityDto communityDto = communityService.getArticle(articleNo);
		List<RippleDto> list = communityService.getlist(articleNo);
		communityDto.setList(list);
		updateHit(articleNo);
		return communityDto;
	}
	
	@GetMapping("/updateHit")
	public void updateHit(@RequestParam int articleNo) throws Exception {
		communityService.updateHit(articleNo);
	}
	//리플 관련 메소드
	
	@PostMapping("/ripplewrite")
	public void rippleWrite(@RequestBody RippleDto ripple) throws Exception{
		communityService.rippleWrite(ripple);
		updateRipple(ripple.getArticleNo());
	}
	
	@GetMapping("/updateRipple")
	public void updateRipple(@RequestParam int articleNo) throws Exception {
		communityService.updateRipple(articleNo);
	}
	
	@GetMapping("/deleteRipple")
	public void deleteRipple(@RequestParam int rippleNo, @RequestParam int articleNo) throws Exception{
		communityService.deleteRipple(rippleNo);
		updateRipple(articleNo);
	}
	
	//////////////////////////////
	
	@PostMapping("/write")
	public void writeAricle(@RequestBody CommunityDto communityDto) throws Exception{
		communityService.writeArticle(communityDto);
	}
	
	@GetMapping("/modify/{articleno}")
	public ResponseEntity<CommunityDto> getModifyArticle(@PathVariable("articleno") int articleno) throws Exception{
		return new ResponseEntity<CommunityDto>(communityService.getArticle(articleno), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<String> modifyArticle(@RequestBody CommunityDto communityDto) throws Exception{
		communityService.modifyArticle(communityDto);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/delete")
	public void deleteArticle(@RequestParam int articleno) throws Exception {
		communityService.deleteArticle(articleno);
	}
	
	
}
