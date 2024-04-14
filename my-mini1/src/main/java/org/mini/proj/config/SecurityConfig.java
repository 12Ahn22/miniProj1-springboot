package org.mini.proj.config;

import org.mini.proj.handler.AuthFailureHandler;
import org.mini.proj.handler.AuthSucessHandler;
import org.mini.proj.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity // 시큐리티 필터 등록
// @EnableGlobalMethodSecurity(prePostEnabled = true) 
// 특정 페이지에 특정 권한이 있는 유저만 접근을 허용할 경우 권한 및 
// 인증을 미리 체크하겠다는 설정을 활성화한다.
public class SecurityConfig {
	@Autowired
	private MemberService memberService;
	@Autowired
	private AuthSucessHandler authSucessHandler;
	@Autowired
	private AuthFailureHandler authFailureHandler;
	
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    AuthenticationManager authenticationManager(
    AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	 
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
			.csrf((csrf) -> csrf.disable())
			.authorizeHttpRequests((authorize) -> authorize
					.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll() // 이걸 해야 JSP 페이지를 포워딩 가능
					.requestMatchers("/","/resources/**","/WEB-INF/**","/board/list","/intro", "/member/insertForm","/member/loginForm**" ,"/member/insert").permitAll()
					.anyRequest().authenticated())
			.userDetailsService(memberService)
			.formLogin(form -> form
					.loginPage("/member/loginForm")
					.permitAll()
					.usernameParameter("email")
					.passwordParameter("password")
					.loginProcessingUrl("/login")
					.defaultSuccessUrl("/")
					.successHandler(authSucessHandler)
					.failureHandler(authFailureHandler)
			)
			.logout((logoutConfig)-> logoutConfig
					.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID")
					.permitAll());
		return http.build();
	}
}
