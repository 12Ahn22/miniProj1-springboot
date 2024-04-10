package org.mini.proj.code.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.mini.proj.entity.CodeVO;

@Mapper
public interface CodeMapper {
	List<CodeVO> getList();
}
