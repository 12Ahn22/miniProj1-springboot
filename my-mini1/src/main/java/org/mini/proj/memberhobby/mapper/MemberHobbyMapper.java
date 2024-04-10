package org.mini.proj.memberhobby.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.mini.proj.entity.MemberHobbyVO;
import org.mini.proj.entity.MemberVO;

@Mapper
public interface MemberHobbyMapper {

	void deleteAll(MemberVO member);

	void insert(MemberHobbyVO memberHobbyVO);

}
