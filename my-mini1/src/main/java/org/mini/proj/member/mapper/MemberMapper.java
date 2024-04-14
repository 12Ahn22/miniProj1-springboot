package org.mini.proj.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.mini.proj.entity.HobbyVO;
import org.mini.proj.entity.MemberVO;
import org.mini.proj.paging.PageRequestVO;

@Mapper
public interface MemberMapper {
	List<MemberVO> getList(PageRequestVO pageRequestVO);

	MemberVO view(MemberVO memberVO);

	int update(MemberVO memberVO);

	int delete(MemberVO memberVO);

	int insert(MemberVO memberVO);

	int getTotalCount(PageRequestVO pageRequestVO);

	MemberVO checkDuplicateId(MemberVO member);

	List<HobbyVO> getMemberHobbies(MemberVO member);

	MemberVO login(MemberVO member);

	int updateMemberLastLogin(String name);

	void loginCountClear(String name);

	void loginCountInc(MemberVO resultVO);
}
