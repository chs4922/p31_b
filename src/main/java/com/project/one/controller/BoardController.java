package com.project.one.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.one.service.BoardService;
import com.project.one.vo.BoardPageVO;
import com.project.one.vo.BoardVO;

@Controller
//@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService service;
	
	// interceptor (임시)
	@RequestMapping("/nologin.do")
	public String nologin() {
		return "nologin";
	}
	
	// 카테고리 리스트
	@RequestMapping("/categoryList.do")
	public String categoryList() {
		return "category_list";
	}
	
	// 게시판 리스트
	@RequestMapping("/board.do")
	public ModelAndView board(@RequestParam(defaultValue="1")int page) {
		ModelAndView mv = new ModelAndView();
		BoardPageVO boardPage = service.makeBoardPage(page);
		mv.addObject("boardPage", boardPage);
		mv.addObject("page", page);
		mv.setViewName("board_list");
		return mv;
	}
	
	// 게시판 검색
	@RequestMapping("/boardSearch.do")
	public ModelAndView search(@RequestParam(defaultValue="1")int page, String searchOption, String searchText, String category, HttpServletResponse resp) {
		resp.setContentType("text/html;charset=UTF-8");
		ModelAndView mv = new ModelAndView();
		
		if (searchText==null || searchText.isEmpty()) {
			searchText = "";
		}
		if (category==null || category.isEmpty()) {
			category = "";
		}
		BoardPageVO boardPage = service.searchBoardPage(page, searchOption, searchText, category);
		mv.addObject("boardPage", boardPage);
		mv.addObject("page", page);
		mv.addObject("searchOption", searchOption);
		mv.addObject("searchText", searchText);
		mv.addObject("category", category);
		mv.setViewName("board_list");
		return mv;
	}
	
	// 마이페이지에서 내 글 보기
	@RequestMapping("/writingList.do")
	public ModelAndView myWritingList(HttpSession session) {
		String loginId = (String) session.getAttribute("loginId");
		ModelAndView mv = new ModelAndView();
		
		BoardPageVO boardPage = service.searchBoardPage(1, "writer", loginId, null);
		mv.addObject("boardPage", boardPage);
		mv.addObject("page", 1);
		mv.addObject("searchOption", "writer");
		mv.addObject("searchText", loginId);
		mv.setViewName("board_list");
		return mv;
	}
	
	@RequestMapping("/writeForm.do")
	public ModelAndView writeForm() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("task", "write");
		mv.setViewName("write_form");
		return mv;
	}
	
	@RequestMapping("/updateForm.do")
	public ModelAndView updateForm(int board_num) {
		BoardVO board = service.readNoCount(board_num);
		ModelAndView mv = new ModelAndView();
		mv.addObject("task", "update");
		mv.addObject("original", board);
		mv.setViewName("write_form");
		return mv;
	}
	
	@RequestMapping("/replyForm.do")
	public ModelAndView replyForm(int board_num) {
		ModelAndView mv = new ModelAndView();
		BoardVO board = service.readNoCount(board_num);
		mv.addObject("task", "reply");
		mv.addObject("original", board);
		mv.setViewName("write_form");
		return mv;
	}
	
	@RequestMapping(value="/write.do", method=RequestMethod.POST)
	public ModelAndView write(BoardVO board, int ref_num, String task,HttpSession session) {
		String loginId = (String) session.getAttribute("loginId");
		ModelAndView mv = new ModelAndView();
		String category = board.getCategory();
		category = category.substring(0, category.length()-1);
		board.setCategory(category);
		board.setBoard_num(ref_num);
		
		if (loginId!=null && loginId.length()>0) {
			if (task!=null && task.equals("write")) { //글쓰기
				service.write(board, loginId);
				mv.setViewName("write_result");
			} else if (task.equals("update")) { //수정
				boolean result = service.update(board,loginId);
				
				if (result) {
					mv.addObject("board_num", board.getBoard_num());
					mv.setViewName("update_success");
				} else {
					mv.setViewName("update_fail");
				}
			} else { //답변
				service.reply(board, loginId, ref_num);
				mv.setViewName("write_result");
			}
		} else {
			mv.setViewName("no_login");
		}
		return mv;
	}
	
	@RequestMapping("/read.do")
	public ModelAndView read(int board_num, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		String loginId = (String) session.getAttribute("loginId");

		BoardVO board = service.read(board_num, loginId);
		
		board.setContent(board.getContent().replaceAll("\n", "<br>"));
		
		mv.addObject("board", board);
		mv.setViewName("read");
		return mv;
	}
	
	@RequestMapping("/update.do")
	public ModelAndView update(BoardVO board, HttpSession session) {
		String loginId = (String) session.getAttribute("loginId");
		
		boolean result = service.update(board,loginId);
		ModelAndView mv = new ModelAndView();
		
		if (result) {
			mv.addObject("board_num", board.getBoard_num());
			mv.setViewName("update_success");
		} else {
			mv.setViewName("update_fail");
		}
		return mv;
	}
	
	@RequestMapping("/delete.do")
	public ModelAndView delete(int board_num, HttpSession session) {
		String loginId = (String) session.getAttribute("loginId");
		
		boolean result = service.delete(board_num, loginId);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("result", result);
		mv.setViewName("delete_result");
		return mv;
	}
	
	@RequestMapping("/editorTest.do")
	public String editor() {
		return "editor_test";
	}
}
