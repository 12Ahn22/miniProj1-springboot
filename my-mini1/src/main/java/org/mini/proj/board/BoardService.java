package org.mini.proj.board;

import java.util.List;

import org.mini.proj.board.mapper.BoardMapper;
import org.mini.proj.entity.BoardVO;
import org.mini.proj.paging.PageRequestVO;
import org.mini.proj.paging.PageResponseVO;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
	private final BoardMapper boardMapper;
	
	public PageResponseVO<BoardVO> list(PageRequestVO pageRequestVO) throws Exception {
		List<BoardVO> list = null;
		PageResponseVO<BoardVO> pageResponseVO = null;
		int total = 0;
		try {
			list = boardMapper.getList(pageRequestVO);
			total = boardMapper.getTotalCount(pageRequestVO);
			
			log.info("list {}", list);
			log.info("total {}", total);
	        pageResponseVO = 
	        		new PageResponseVO<BoardVO>(list, total,pageRequestVO.getPageNo(), pageRequestVO.getSize());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return pageResponseVO;
	}
	
	public BoardVO view(BoardVO boardVO) {
		// boardMapper.increaseViewCount(boardVO.getBno());
		return boardMapper.view(boardVO);
	}

	
	public BoardVO fetchUpdateFormData(BoardVO boardVO) {
		return boardMapper.view(boardVO);
	}
	
	public int update(BoardVO boardVO) {
		return boardMapper.update(boardVO);
	}
	

	public int delete(BoardVO boardVO) {
		return boardMapper.delete(boardVO);
	}


	public int insert(BoardVO boardVO) {
		return boardMapper.insert(boardVO);
	}

}
