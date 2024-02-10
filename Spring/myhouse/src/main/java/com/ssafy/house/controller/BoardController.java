package com.ssafy.house.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.house.dto.BoardDto;
import com.ssafy.house.dto.MemberDto;
import com.ssafy.house.service.BoardService;

@RestController
@RequestMapping("/article")
@CrossOrigin("*")
public class BoardController {

	private BoardService boardService;

	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}

	@GetMapping("/list")
	public List<BoardDto> list(@RequestParam Map<String, String> map) throws Exception {
		List<BoardDto> list = boardService.listArticle(map);
		return list;
	}

	@GetMapping("/getArticle")
	public BoardDto getArticle(@RequestParam int articleNo) throws Exception {
		BoardDto boardDto = boardService.getArticle(articleNo);
		updateHit(articleNo);
		return boardDto;
	}

	@GetMapping("/updateHit")
	public void updateHit(@RequestParam int articleNo) throws Exception {
		boardService.updateHit(articleNo);
	}

	@PostMapping("/write")
	public void writeArticle(@RequestBody BoardDto boardDto) throws Exception {
		System.out.println("호로로로로로로로롤");
		System.out.println("boardDto : " + boardDto);
		boardService.writeArticle(boardDto);
	}

//	@GetMapping("/modify/{articleno}")
//	public BoardDto getModifyArticle(int articleno) throws Exception {
//
//		return boardService.getArticle(articleno);
//	}
//
//	@PostMapping("/modify")
//	public void modifyArticle(@RequestBody BoardDto boardDto) throws Exception {
//		boardService.modifyArticle(boardDto);
//	}
	
	@GetMapping("/modify/{articleno}")
	public ResponseEntity<BoardDto> getModifyArticle(
			@PathVariable("articleno")  int articleno)
			throws Exception {
		return new ResponseEntity<BoardDto>(boardService.getArticle(articleno), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<String> modifyArticle(
			@RequestBody BoardDto boardDto) throws Exception {

		boardService.modifyArticle(boardDto);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/delete")
	public void deleteArticle(@RequestParam int articleNo) throws Exception {
		boardService.deleteArticle(articleNo);
	}
}
