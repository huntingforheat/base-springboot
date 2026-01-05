package com.example.demo.config;

import com.example.demo.security.UsersDetailsService;
import com.example.demo.security.filter.JWTFilter;
import com.example.demo.security.handler.CustomAccessDeniedHandler;
import com.example.demo.utils.JWTUtil;
import com.password4j.Argon2Function;
import com.password4j.types.Argon2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.Argon2Password4jPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@Slf4j
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class CustomSecurityConfig {

    private final UsersDetailsService usersDetailsService;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JWTUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {

        Argon2Function argon2Fn = Argon2Function.getInstance(65536, 3, 4, 32, Argon2.ID);

        return new Argon2Password4jPasswordEncoder(argon2Fn);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        log.info("----------------- web configure ---------------");

//        CSS(new String[]{"/css/**"}),
//        JAVA_SCRIPT(new String[]{"/js/**"}),
//        IMAGES(new String[]{"/images/**"}),
//        WEB_JARS(new String[]{"/webjars/**"}),
//        FAVICON(new String[]{"/favicon.*", "/*/icon-*"}),
//        FONTS(new String[]{"/fonts/**"});

        // 정적 자원 인증 안함
        return(web) -> web.ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations()
                );
    }

    // 공개 접근 필터 체인
    @Bean
    @Order(1)
    public SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
        defaultSecuritySetting(http);
        http
                .securityMatchers(matcher -> matcher
                        .requestMatchers(publicRequestMatchers()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(publicRequestMatchers()).permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    // 인증 및 권한이 필요한 필터 체인
    // TODO: 테스트 목적으로 .permitAll()로 변경 테스트 후 .authenticated()로 교체 해야 함
    @Bean
    @Order(2)
    public SecurityFilterChain authenticatedFilterChain(HttpSecurity http) throws Exception {
        defaultSecuritySetting(http);
        http
                .securityMatchers(matcher -> matcher
                        .requestMatchers(authenticatedRequestMatchers()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(authenticatedRequestMatchers())
                        .permitAll())
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler))
                .addFilterBefore(new JWTFilter(jwtUtil, usersDetailsService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // 어드민 권한이 필요한 필터 체인
    @Bean
    @Order(3)
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
        defaultSecuritySetting(http);
        http
                .securityMatchers(matcher -> matcher
                        .requestMatchers(adminRequestMatchers()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(adminRequestMatchers())
                        .authenticated())
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler))
                .addFilterBefore(new JWTFilter(jwtUtil, usersDetailsService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return null;
    }

    private void defaultSecuritySetting(HttpSecurity http) throws Exception {
        log.info("------------------- configure --------------------");

        // AuthenticationManager 설정
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(usersDetailsService)
                .passwordEncoder(passwordEncoder());

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .authenticationManager(authenticationManager)
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                // 세션을 사용하지 않음 (JWT 기반 인증이므로 STATELESS 모드 설정)
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        ))
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));
    }

    // cors
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOriginPatterns(Arrays.asList("http://localhost:8090"));
        corsConfiguration.setAllowedMethods(Arrays.asList("HEAD","GET","POST","PUT","DELETE"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization","Cache-Control","Content-Type"));
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    // 공개 접근 endpoint
    private RequestMatcher[] publicRequestMatchers() {

        return new RequestMatcher[] {
                // TODO: 한번 확인해보기
                PathPatternRequestMatcher.pathPattern("/public/**")
        };
    }

    // 인증 및 권한이 필요한 endpoint
    private RequestMatcher[] authenticatedRequestMatchers() {

        return new RequestMatcher[] {
                PathPatternRequestMatcher.pathPattern("/api/**")
        };
    }

    // 어드민 권한이 필요한 endpoint
    private RequestMatcher[] adminRequestMatchers() {
        return new RequestMatcher[] {
                PathPatternRequestMatcher.pathPattern("/backoffice/**")
        };
    }

}
