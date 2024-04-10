package org.mini.proj.hobby.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.mini.proj.entity.HobbyVO;

@Mapper
public interface HobbyMapper {
	List<HobbyVO> list();
}
