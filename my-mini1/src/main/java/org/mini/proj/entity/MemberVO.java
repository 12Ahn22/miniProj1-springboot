package org.mini.proj.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MemberVO implements UserDetails{
	private String id;
	private String name;
	private String password;
	private String address;
	private String phone;
	private Gender gender;
	private List<HobbyVO> hobbies; // 모든 취미
	private Map<Integer, String> mapHobbies; // 모든 취미
	private LocalDateTime last_login_time; // 마지막 로그인 시간
	private String roles; // 권한. ex) "USER, ADMIN, ..." 문자열
	private String account_locked; // 계정 잠금 여부 Y, N
	private int login_count; // 로그인 횟수
	
	// 자동 로그인을 위한 UUID
	private String memberUUID; 
	// 자동로그인 체크 여부
	private String autoLogin;
	
	// 요청 처리를 위한 필드
	private String action;
	
	// 보여주기 위한 데이터
	public MemberVO(String id, String name, String address, String phone, String gender, String[] hobbies) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
		if(gender.equalsIgnoreCase("f")) this.gender = Gender.F;	
		else this.gender = Gender.M;
	}
	
	public MemberVO(String id, String name, String address, String phone, String gender) {
		this(id, name, address, phone, gender, null);
	}
	
	public static enum Gender {
        F,
        M
    }
	
	public boolean isEqualsPwd(String pwd) {
		return this.password.equals(pwd);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collections = new ArrayList<GrantedAuthority>();

		Arrays.stream(roles.split(","))
			.forEach(role -> collections.add(new SimpleGrantedAuthority("ROLE_" + role.trim())));
		return collections;
	}

	@Override
	public String getUsername() {
		return id;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return "N".equalsIgnoreCase(account_locked);
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
