package com.example.CollegeManagement.Controller;

import com.example.CollegeManagement.Config.CustomAccountantAuthenticationProvider;
import com.example.CollegeManagement.Dto.JwtResponse;
import com.example.CollegeManagement.Dto.Message;
import com.example.CollegeManagement.Entity.Accountant;
import com.example.CollegeManagement.Event.FeesAlertEvent;
import com.example.CollegeManagement.Service.AccountantService;
import com.example.CollegeManagement.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/accountant")
@Slf4j
@CrossOrigin(origins = "*")
public class AccountController {
    @Autowired
    private AccountantService accountantService;
    @Autowired
    private CustomAccountantAuthenticationProvider customAccountantAuthenticationProvider;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private JwtUtils jwtUtils;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Accountant accountant) {
        if (accountant == null)
            return ResponseEntity.badRequest().body(new Message("Not a valid Details"));
        if (accountantService.findByEmail(accountant.getPassword()) != null)
            return ResponseEntity.badRequest().body(new Message("Email Already Exists.."));
        return accountantService.register(accountant);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Accountant accountant) {
        Authentication authentication;
        AuthenticationManager authenticationManager = new ProviderManager(customAccountantAuthenticationProvider);
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(accountant.getEmail(), accountant.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtUtils.generateJwtToken(authentication);
            System.out.println("\nTOKEN\n" + jwtToken);
            Accountant loggedInAccountant = accountantService.findByEmail(authentication.getPrincipal().toString());
            return ResponseEntity.ok(new JwtResponse(jwtToken,
                                                        loggedInAccountant.getId(),
                                                        loggedInAccountant.getName(),
                                                        loggedInAccountant.getEmail(),
                                                        loggedInAccountant.getRole()));

        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body(new Message("Check your login credentials..."));
    }
    @GetMapping("/logout")
    public ResponseEntity<HttpResponse> logout() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            SecurityContextHolder.clearContext();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("/alert")
    public ResponseEntity<HttpResponse> sendAlert() {
        List<String> students = accountantService.getUnPaidStudents();
        if (students.size() > 0) {
            publisher.publishEvent(new FeesAlertEvent(this, students));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> delete(@PathVariable("id") Long id){
        return accountantService.delete(id);
    }

}
