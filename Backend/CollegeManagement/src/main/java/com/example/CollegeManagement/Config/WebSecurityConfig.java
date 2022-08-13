package com.example.CollegeManagement.Config;

import com.example.CollegeManagement.security.AuthEntryPointJwt;
import com.example.CollegeManagement.security.AuthTokenFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("**/login").permitAll()
                .antMatchers("/student/register").hasAnyAuthority("ROLE_STAFF", "ROLE_ACCOUNTANT")
                .antMatchers("/payment").hasAuthority("ROLE_STUDENT")
                .antMatchers("/accountant/register").hasAuthority("ROLE_ADMIN")
                .antMatchers("/").permitAll()
                .antMatchers("/student/delete/**").hasAnyAuthority("ROLE_ACCOUNTANT", "ROLE_STAFF")
                .antMatchers("/fees/register").hasAuthority("ROLE_ACCOUNTANT")
                .antMatchers("/accountant/delete/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/course/**").hasAuthority("ROLE_STAFF")
                .antMatchers("/attendance/**").hasAnyAuthority("ROLE_STAFF")
                .and()
                .csrf().disable()
                .cors().and()
                .securityContext((securityContext) -> securityContext
                        .securityContextRepository(new RequestAttributeSecurityContextRepository())
                )
                .httpBasic();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
