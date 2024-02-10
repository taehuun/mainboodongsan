package com.ssafy.house.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.house.dto.MemberDto;
import com.ssafy.house.service.MemberService;
import com.ssafy.house.util.JWTUtil;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class MemberController {
	
	private MemberService memberService;
	private JWTUtil jwtUtil;
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	public MemberController(MemberService memberService, JWTUtil jwtUtil) {
		super();
		this.memberService = memberService;
		this.jwtUtil = jwtUtil;
	}
	
	@PostMapping(value ="/login")
	public ResponseEntity<?> loginToken(@RequestBody MemberDto memberDto) {
		System.out.println(memberDto.toString());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		HttpStatus status = HttpStatus.ACCEPTED;
		MemberDto loginResult = memberService.login(memberDto.getEmail(), memberDto.getPass());
		
		try {
			if(loginResult != null) {
				String accessToken = jwtUtil.createAccessToken(loginResult.getEmail());
				String refreshToken = jwtUtil.createRefreshToken(loginResult.getEmail());
				
				memberService.saveRefreshToken(loginResult.getEmail(), refreshToken);
				
				resultMap.put("accessToken", accessToken);
				resultMap.put("refreshToken", refreshToken);
				
				status = HttpStatus.CREATED;
			}
			else {
				resultMap.put("error", "아이디 또는 패스워드를 확인해주세요.");
				status = HttpStatus.UNAUTHORIZED;
			}
			
		}catch(Exception e) {
			resultMap.put("error", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		
		
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	
	@GetMapping(value ="/logout/{email}")
	public ResponseEntity<Map<String, Object>> logout(
			@PathVariable("email") String email) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		logger.info("logout executed");
		try {
			logger.info("logout email : "+email);
			memberService.deleRefreshToken(email);
			status = HttpStatus.OK;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("deleteRefreshToken failed. :: ");
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PostMapping(value = "/join")
	public ResponseEntity<?> join(@RequestBody MemberDto memberDto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			MemberDto checkMember = memberService.getMember(memberDto.getEmail());
			if(checkMember == null) {
				memberService.join(memberDto);
				status = HttpStatus.OK;
			}
			else {
				logger.error("memberController >> join :: member already exists");
				status = HttpStatus.UNAUTHORIZED;
			}
			
		} catch (Exception e) {
			logger.error("memberController >> join :: join error.");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PostMapping(value ="/userEdit")
	public ResponseEntity<Map<String, Object>> userEdit(
			@RequestBody MemberDto updateMemberDto, @RequestParam String originalEmail){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		logger.info("userEdit >> memberDto : "+ updateMemberDto);
		logger.info("userEdit >> email : "+ originalEmail);
		
		
		try {
			memberService.modify(updateMemberDto, originalEmail);
			status = HttpStatus.OK;
			
		} catch (Exception e) {
			logger.error("userEdit failed :: ");
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
		
		
	}
	
	@GetMapping(value = "/userDelete/{email}")
	public ResponseEntity<Map<String, Object>> deleteMember(
			@PathVariable("email") String email) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		try {
			memberService.deleteMember(email);
			status = HttpStatus.OK;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("userDelete failed :: ");
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	
	
	
	@GetMapping(value ="/userAuth/{email}")
	public ResponseEntity<?> tokenAuth(@PathVariable("email") String email, HttpServletRequest request) {
		// 받아온 토큰이 유효하고, 현재 요청한 사람이 db에 존재하는가?????
		Map<String, Object> resultMap = new HashMap<String, Object>();
		jwtUtil.checkToken(request.getHeader("Authorization"));
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			// if true
			logger.info("jwtCheck 통과. 즉, 사용가능한 토큰");
			try {
				MemberDto memberDto = memberService.getMember(email);
				resultMap.put("userInfo", memberDto);
				status = HttpStatus.OK;
			} catch(Exception e) {
				logger.error("아이디 조회 실패.", e);
				resultMap.put("error", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}else {
			logger.error("사용불가능 토큰.");
			// resultmap을 비워서 보내면 catch로 넘어가나????
			resultMap.put("error", "사용불가능한 토큰이거나 다른 에러");
			status = HttpStatus.UNAUTHORIZED;
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping(value ="/getUserInfo")
	public ResponseEntity<Map<String, Object>> getUserInfo(
		HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if(jwtUtil.checkToken(request.getHeader("GetUserInfo"))) {
			logger.info("jwtUtil == true로 들어옴");
			logger.info(request.getHeader("GetUserInfo"));
			String token = request.getHeader("GetUserInfo");
			logger.info("token :" + token );
			MemberDto memberDto = memberService.getMemberByToken(token);
			logger.info("memberDto : " + memberDto);
			resultMap.put("userInfo", memberDto);	
			status = HttpStatus.OK;
		}else {
			logger.error("사용할 수 없는 토큰이거나 다른 에러");
			resultMap.put("error", "사용할 수 없는 토큰이거나 다른 에러");
			status = HttpStatus.UNAUTHORIZED;
		}
		
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
//	public ResponseEntity<?> deleteToken() {
//		
//	}
//	
	
	
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody MemberDto memberDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		logger.debug("token : {}, memberDto : {}", token, memberDto);
		if (jwtUtil.checkToken(token)) {
			if (token.equals(memberService.getRefreshToken(memberDto.getEmail()))) {
				String accessToken = jwtUtil.createAccessToken(memberDto.getEmail());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("accessToken", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불가!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	
	
}
