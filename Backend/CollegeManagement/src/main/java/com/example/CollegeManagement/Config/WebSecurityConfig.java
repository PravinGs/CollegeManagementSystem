package com.example.CollegeManagement.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println(http.authorizeRequests());
        http
                .authorizeRequests()
                .antMatchers("/student/login").permitAll()
                .antMatchers("/student/register").hasAnyAuthority("ROLE_STAFF", "ROLE_ACCOUNTANT")
                .antMatchers("/accountant/register").hasAuthority("ROLE_ADMIN")
                .antMatchers("/").permitAll()
                .antMatchers("/accountant/login").permitAll()
                .antMatchers("/student/delete/**").hasAnyAuthority("ROLE_ACCOUNTANT", "ROLE_STAFF")
                .antMatchers("/fees/register").hasAuthority("ROLE_ACCOUNTANT")
                .antMatchers("/accountant/delete/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/course/**").hasAuthority("ROLE_STAFF")
                .antMatchers("/attendance/**").hasAnyAuthority("ROLE_STAFF")
                .and()
                .formLogin().loginPage("/login")
                .and()
                .csrf().disable()
                .httpBasic();
        return http.build();
    }
}
