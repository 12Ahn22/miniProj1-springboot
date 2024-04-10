package org.mini.proj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberHobbyVO {
	private String memberId;
	private Integer hobbyId; 
}
