package com.example.CollegeManagement.Config;

import com.example.CollegeManagement.Entity.Accountant;
import com.example.CollegeManagement.Repository.AccountantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAccountantAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private AccountantRepository accountantRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        Accountant accountant = accountantRepository.findByEmail(email);
        if (accountant == null) throw new UsernameNotFoundException("Account Not Exist with this email");
        if (passwordEncoder.matches(password, accountant.getPassword())) {
            List<GrantedAuthority> list = new ArrayList<>();
            list.add(new SimpleGrantedAuthority(accountant.getRole()));
            return new UsernamePasswordAuthenticationToken(email, password, list);
        }
        return (Authentication) new BadCredentialsException("Invalid Details");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
