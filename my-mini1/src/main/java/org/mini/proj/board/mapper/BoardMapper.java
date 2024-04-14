package org.mini.proj.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.mini.proj.entity.BoardVO;
import org.mini.proj.paging.PageRequestVO;

@Mapper
public interface BoardMapper {
	List<BoardVO> getList(PageRequestVO pageRequestVO);

	BoardVO view(BoardVO boardVO);

	int update(BoardVO boardVO);

	int delete(BoardVO boardVO);

	int insert(BoardVO boardVO);

	int getTotalCount(PageRequestVO pageRequestVO);

	int increaseViewCount(Integer bno);

}
