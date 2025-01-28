package com.springboot.security.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 어플리케이션의 보안 설정
 *
 * @author Flature
 * @version 1.0.0
 */
@Configuration
public class SecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * HttpSecurity를 사용한 보안 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // REST API는 CSRF 비활성화
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/sign-api/sign-in", "/sign-api/sign-up", "/sign-api/exception").permitAll()
                        .requestMatchers(HttpMethod.GET, "/product/**").permitAll()
                        .requestMatchers("**exception**").permitAll()
                        .anyRequest().hasRole("ADMIN")
                )
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .httpBasic(httpBasic -> httpBasic.disable()); // httpBasic() 비활성화

        return http.build();
    }

    /**
     * Swagger 및 기타 리소스에 대한 접근 허용 설정
     */
    @Bean
    public SecurityFilterChain swaggerFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**")
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}



// [[ WebSecurityConfigurerAdapter 지원 중단
//    => 위와 같이 SecurityFilterChain을 Bean으로 정의하는 방식 사용 ]]


///**
// * 어플리케이션의 보안 설정
// *
// * @author Flature
// * @version 1.0.0
// */
//@Configuration
////@EnableWebSecurity // Spring Security에 대한 디버깅 모드를 사용하기 위한 어노테이션 (default : false)
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    private final JwtTokenProvider jwtTokenProvider;
//
//    @Autowired
//    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.httpBasic().disable() // REST API는 UI를 사용하지 않으므로 기본설정을 비활성화
//
//            .csrf().disable() // REST API는 csrf 보안이 필요 없으므로 비활성화
//
//            .sessionManagement()
//            .sessionCreationPolicy(
//                SessionCreationPolicy.STATELESS) // JWT Token 인증방식으로 세션은 필요 없으므로 비활성화
//
//            .and()
//            .authorizeRequests() // 리퀘스트에 대한 사용권한 체크
//            .antMatchers("/sign-api/sign-in", "/sign-api/sign-up",
//                "/sign-api/exception").permitAll() // 가입 및 로그인 주소는 허용
//            .antMatchers(HttpMethod.GET, "/product/**").permitAll() // product로 시작하는 Get 요청은 허용
//
//            .antMatchers("**exception**").permitAll()
//
//            .anyRequest().hasRole("ADMIN") // 나머지 요청은 인증된 ADMIN만 접근 가능
//
//            .and()
//            .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
//            .and()
//            .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//
//            .and()
//            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
//                UsernamePasswordAuthenticationFilter.class); // JWT Token 필터를 id/password 인증 필터 이전에 추가
//    }
//
//    /**
//     * Swagger 페이지 접근에 대한 예외 처리
//     *
//     * @param webSecurity
//     */
//    @Override
//    public void configure(WebSecurity webSecurity) {
//        webSecurity.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
//            "/swagger-ui.html", "/webjars/**", "/swagger/**", "/sign-api/exception");
//    }
//}