package org.mini.proj.board;

import java.util.HashMap;
import java.util.Map;

import org.mini.proj.code.CodeService;
import org.mini.proj.entity.BoardVO;
import org.mini.proj.paging.PageRequestVO;
import org.mini.proj.paging.PageResponseVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	// 스프링이 의존성을 주입해준다.
	private final BoardService boardService;
	private final CodeService codeService;

	@GetMapping("list")
	public String list(@Valid PageRequestVO pageRequestVO, BindingResult bindingResult, Model model) throws Exception {
		log.info("게시판 목록");
		
        if(bindingResult.hasErrors()){
        	pageRequestVO = PageRequestVO.builder().build();
        }
        
		PageResponseVO<BoardVO> pageResponseVO = boardService.list(pageRequestVO);
		// HttpSession session = request.getSession();
		// boolean isLogin = (session.getAttribute("loginMember") != null)? true : false;
		
		model.addAttribute("pageResponseVO", pageResponseVO);
		model.addAttribute("sizes", codeService.getList());
		// model.setAttribute("isLogin", isLogin);
		return "board/boardList";
	}

	@GetMapping("view")
	public String view(Model model, BoardVO boardVO) {
		log.info("게시판 상세 목록");
		// 게시글 정보
		BoardVO board = boardService.view(boardVO);
		model.addAttribute("board", board);

		return "board/boardView";
	}
	
	@GetMapping("jsonView")
	@ResponseBody
	public Map<String,Object> jsonView(Model model, BoardVO boardVO) {
		log.info("게시판 상세 목록 - jsonView");
		Map<String, Object> map = new HashMap<String, Object>();
		// 게시글 정보
		BoardVO board = boardService.view(boardVO);

		if(board != null) { // 성공
			map.put("status", 204);
			map.put("board", board);
		} else {
			map.put("status", 404);
			map.put("statusMessage", "게시글 삭제에 실패하였습니다");
		}
		
		return map;
	}

	@PostMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(Model model, @RequestBody BoardVO boardVO) {
		log.info("게시물 삭제");
		Map<String, Object> map = new HashMap<String, Object>();
		int updated = boardService.delete(boardVO);
		
		if(updated == 1) { // 성공
			map.put("status", 204);
		} else {
			map.put("status", 404);
			map.put("statusMessage", "게시글 삭제에 실패하였습니다");
		}
		
		return map;
	}

	@GetMapping("updateForm")
	public String updateForm(Model model, BoardVO boardVO) {
		// 게시글 정보
		BoardVO board = boardService.fetchUpdateFormData(boardVO);
		model.addAttribute("board", board);
		return "board/boardUpdate";
	}

	@PostMapping("update")
	@ResponseBody
	public Map<String, Object> update(Model model, @RequestBody BoardVO boardVO) {
		Map<String, Object> map = new HashMap<String, Object>();
		int updated = boardService.update(boardVO);
		
		if(updated == 1) { // 성공
			map.put("status", 204);
		} else {
			map.put("status", 404);
			map.put("statusMessage", "게시글 수정에 실패하였습니다");
		}
		
		return map;
	}

	@GetMapping("insertForm")
	public String insertForm() {
		return "board/boardInsert";
	}

	@PostMapping("insert")
	@ResponseBody
	public Map<String, Object> insert(Model model, @RequestBody BoardVO boardVO) {
		Map<String, Object> map = new HashMap<String, Object>();
		int updated = boardService.insert(boardVO);
		if(updated == 1) { // 성공
			map.put("status", 204);
		} else {
			map.put("status", 404);
			map.put("statusMessage", "게시글 생성에 실패하였습니다");
		}
		
		return map;
	}

}
