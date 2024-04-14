package org.mini.proj.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mini.proj.entity.HobbyVO;
import org.mini.proj.entity.MemberHobbyVO;
import org.mini.proj.entity.MemberVO;
import org.mini.proj.hobby.mapper.HobbyMapper;
import org.mini.proj.member.mapper.MemberMapper;
import org.mini.proj.memberhobby.mapper.MemberHobbyMapper;
import org.mini.proj.paging.PageRequestVO;
import org.mini.proj.paging.PageResponseVO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{
	private final MemberMapper memberMapper;
	private final HobbyMapper hobbyMapper;
	private final MemberHobbyMapper memberHobbyMapper;

	public PageResponseVO<MemberVO> list(PageRequestVO pageRequestVO) {
		List<MemberVO> list = null;
		PageResponseVO<MemberVO> pageResponseVO = null;
		int total = 0;
		try {
			list = memberMapper.getList(pageRequestVO);
			total = memberMapper.getTotalCount(pageRequestVO);
			
			log.info("list {}", list);
			log.info("total {}", total);
			pageResponseVO = 
	        		new PageResponseVO<MemberVO>(list, total,pageRequestVO.getPageNo(), pageRequestVO.getSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageResponseVO;
	}
//	MemberDAO memberDAO = new MemberDAO(); // 유저 정보
//	HobbyDAO hobbyDAO = new HobbyDAO(); // 취미
//	MemberHobbyDAO memberHobbyDAO = new MemberHobbyDAO(); // 멤버-취미 관계
//
//	public List<MemberVO> list() throws SQLException {
//		List<MemberVO> list = null;
//		list = memberDAO.list();
//		return list;
//	}

	public MemberVO view(MemberVO member) {
		MemberVO memberVO = null;
		List<HobbyVO> hobbies  = null;
		try {
			memberVO = memberMapper.view(member);
			hobbies = memberMapper.getMemberHobbies(member);
			if (hobbies.size() != 0) memberVO.setHobbies(hobbies);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberVO;
	}

	public int delete(MemberVO member) {
		return memberMapper.delete(member);
//		try {
//			memberDAO.delete(member);
//			BaseDAO.conn.commit();
//		} catch (Exception e) {
//			try {
//				BaseDAO.conn.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//			return 0;
//		}
//		return 1;
	}

	public int update(MemberVO member) {
		try {
			// 멤버-취미 테이블에서 해당 멤버를 전부 삭제
			memberHobbyMapper.deleteAll(member);

			// 받은 취미를 전부 insert
			Map<Integer, String> hobbies = member.getMapHobbies();
			if (hobbies != null) {
					for(Integer key: hobbies.keySet()) {
						MemberHobbyVO memberHobbyVO = new MemberHobbyVO(member.getId(), key);
						memberHobbyMapper.insert(memberHobbyVO);
					}
				
//				for(int i = 0; i < hobbies.size(); i++) {
//					Set<Entry<Integer, String>> hv = hobbies.
////					MemberHobbyVO memberHobbyVO = new MemberHobbyVO(member.getId(), hv);
//					memberHobbyMapper.insert(memberHobbyVO);
//				}
			}
			// 멤버 수정 사항 업데이트
			memberMapper.update(member);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	public Map<String, Object> fetchUpdateFormData(MemberVO member) {
		Map<String, Object> map = new HashMap<String, Object>();
		MemberVO memberVO =  memberMapper.view(member);
		List<HobbyVO> memberHobbies = memberMapper.getMemberHobbies(member); // 유저가 선택한 취미 목록
		Map<Integer, String> mapHobbies = new HashMap<Integer, String>();
		for(int i = 0; i < memberHobbies.size(); i++) {
			HobbyVO hobby = memberHobbies.get(i);
			mapHobbies.put(hobby.getId(), hobby.getHobby());
		}
		memberVO.setHobbies(memberHobbies);
		memberVO.setMapHobbies(mapHobbies);
		map.put("memberVO", memberVO);
		map.put("hobbyList", hobbyMapper.list());
		return map;
	}

	public List<HobbyVO> fetchInsertFormData() {
		return hobbyMapper.list();
//		List<HobbyVO> list = null;
//		try {
//			list = hobbyDAO.list();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
	}

	public int insert(MemberVO member) {
		return memberMapper.insert(member);
//		try {
//			// 멤버를 먼저 생성
//			memberDAO.insert(member);
//			// 취미-멤버 테이블에 데이터 생성
//			Map<Integer, String> hobbies = member.getHobbies();
//			if (hobbies != null) {
//				for (var hobby : hobbies.entrySet()) {
//					memberHobbyDAO.insert(member.getId(), hobby.getKey());
//				}
//			}
//			BaseDAO.conn.commit();
//		} catch (Exception e) {
//			try {
//				// SQL 에러가 나면, 여기서 처리
//				e.printStackTrace();
//				BaseDAO.conn.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//			return 0;
//		}
//		return 1;
	}

//	public MemberVO authenticateMember(MemberVO member) {
//		return memberMapper.authenticateMember(member);

//		boolean hasAuth = false;
//		try {
//			hasAuth =  memberDAO.authenticateMember(member);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return hasAuth;
//	}
	
	public MemberVO login(MemberVO member) {
		MemberVO result = memberMapper.login(member);
		if(result != null && member.isEqualsPwd(result.getPassword())) {
			return result;
		}
		return null;
	}
	
//
//	public int updateUUID(MemberVO member) {
//		try {
//			memberDAO.updateUUID(member);
//			BaseDAO.conn.commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			try {
//				BaseDAO.conn.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//			return 0;
//		}
//		return 1;
//	}
//
	public MemberVO checkDuplicateId(MemberVO member) {
		return memberMapper.checkDuplicateId(member);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("#### loadUserByUsername ####");
		log.info("LOAD USER BY USERNAME: {}", username);
		MemberVO resultVO = memberMapper.login(MemberVO.builder().id(username).build());
		if(resultVO == null) {
			log.info("사용자가 존재하지 않습니다.");
			throw new UsernameNotFoundException(username + " 사용자가 존재하지 않습니다");
		}
		log.info("resultVO = {}", resultVO);
		//로그인 횟수를 카운트 한다
		memberMapper.loginCountInc(resultVO);
		return resultVO;
	}
	
	public static void main(String [] args) {
		BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
		System.out.println(bcryptPasswordEncoder.encode("1004"));
		System.out.println(bcryptPasswordEncoder.encode("1004"));
	}
}
