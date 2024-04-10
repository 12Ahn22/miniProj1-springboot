package org.mini.proj.code;

import java.util.List;

import org.mini.proj.code.mapper.CodeMapper;
import org.mini.proj.entity.CodeVO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * MVC 
 * Model : B/L 로직을 구현하는 부분(service + dao)  
 * View  : 출력(jsp) 
 * Controller : model와 view에 대한 제어를 담당 
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CodeService {
	private static final long serialVersionUID = 1L;
      
	private final CodeMapper codeMapper;

    public List<CodeVO> getList() {
    	return codeMapper.getList();
	}
}











