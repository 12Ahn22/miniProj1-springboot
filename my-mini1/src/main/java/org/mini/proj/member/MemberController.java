package org.mini.proj.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mini.proj.code.CodeService;
import org.mini.proj.entity.HobbyVO;
import org.mini.proj.entity.MemberVO;
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

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
	private final MemberService memberService;
	private final CodeService codeService;

	@GetMapping("list")
	public String list(@Valid PageRequestVO pageRequestVO, BindingResult bindingResult, Model model) throws Exception {
		// 서비스를 호출한다.
		PageResponseVO<MemberVO> pageResponseVO = memberService.list(pageRequestVO);

		// JSP에서 사용할 수 있도록 setAttribute 해준다.
		model.addAttribute("pageResponseVO", pageResponseVO);
		model.addAttribute("sizes", codeService.getList());
		return "member/memberList";
	}

	@GetMapping("view")
	public String view(Model model, MemberVO member) {
		MemberVO viewMember = memberService.view(member);
		model.addAttribute("member", viewMember);
		return "member/memberView";
	}

	@GetMapping("delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestBody MemberVO member) {
		Map<String, Object> map = new HashMap<String, Object>();

		int updated = memberService.delete(member);
		if (updated == 1) { // 성공
			map.put("status", 204);
		} else {
			map.put("status", 404);
			map.put("statusMessage", "회원 정보 삭제 실패하였습니다");
		}

		return map;
	}

	@PostMapping("update")
	@ResponseBody
	public Map<String, Object> update(@RequestBody MemberVO member) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		int updated = memberService.update(member);
		if (updated == 1) { // 성공
			map.put("status", 204);
		} else {
			map.put("status", 404);
			map.put("statusMessage", "회원 정보 수정 실패하였습니다");
		}
		return map;
	}

	@GetMapping("updateForm")
	public String fetchUpdateFormData(Model model, MemberVO member) {
		log.info("멤버 업데이트 폼");
		Map<String, Object> map = memberService.fetchUpdateFormData(member);
		model.addAttribute("member", map.get("memberVO"));
		model.addAttribute("hobbyList", map.get("hobbyList"));
		return "member/memberUpdate";
	}

	@GetMapping("insertForm")
	public String fetchInsertFormData(Model model) {
		List<HobbyVO> hobbyList = memberService.fetchInsertFormData();
		 model.addAttribute("hobbyList", hobbyList);
		return "member/memberInsert";
	}

	@PostMapping("insert")
	@ResponseBody
	public Map<String, Object> insert(@RequestBody MemberVO member) {
		Map<String, Object> map = new HashMap<String, Object>();
		int updated = memberService.insert(member);
		if (updated == 1) { // 성공
			map.put("status", 204);
		} else {
			map.put("status", 404);
			map.put("statusMessage", "회원 가입에 실패하였습니다");
		}
		return map;
	}
	
	@GetMapping("loginForm")
	public Object loginForm() {
		log.info("로그인 화면");
		return "member/login";
	}

//	@PostMapping("login")
//	@ResponseBody
//	public Map<String, Object> login(@RequestBody MemberVO member, HttpSession session) {
//		log.info("LOGIN");
//		Map<String, Object> map = new HashMap<String, Object>();
//
//		// 로그인 처리
//		MemberVO loginVO = memberService.login(member);
//		
//		if(loginVO != null) {
//			// 로그인 성공
//			session.setAttribute("loginMember", loginVO);
//			session.setMaxInactiveInterval(30 * 60 * 1000); // 30분
//			map.put("status", 204);
//		}else {
//			// 로그인 실패
//			map.put("status", 404);
//			map.put("statusMessage", "아이디 혹은 비밀번호가 잘못되었습니다.");
//		}
//		return map;
//	}

//	@GetMapping("logout")
//	public String logout(HttpSession session) {
//		// 세션에서 로그인 정보 얻기
//		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
////		loginMember.setMemberUUID("");
////		memberService.updateUUID(loginMember);
//		// 세션 삭제
//		session.removeAttribute("loginMember");
//		session.invalidate();
//
//		// main 화면으로 리다이렉트 하도록 응답
//		return "redirect:/";
//	}

	@RequestMapping("profile")
	public String profile(HttpSession session, Model model) {
		log.info("PROFILE");
		MemberVO sessionMember = (MemberVO) session.getAttribute("loginMember");
		
		MemberVO viewMember = memberService.view(sessionMember);
		model.addAttribute("member", viewMember);
		return "member/memberProfile";
	}

	@PostMapping("duplicate")
	@ResponseBody
	public Map<String, Object> checkDuplicateId(@RequestBody MemberVO member) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		MemberVO searchMember = memberService.checkDuplicateId(member);
		if(searchMember == null) {
			map.put("status", 204);
		}else {
			map.put("status", 404);
			map.put("statusMessage", "해당 아이디는 이미 사용중입니다.");
		}
		return map;
	}
}
