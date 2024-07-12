package com.talents.acquisition.controller;


import com.talents.acquisition.model.LoginForm;
import com.talents.acquisition.model.MyUser;
import com.talents.acquisition.repo.MyUserRepo;
import com.talents.acquisition.security.MyUserDetailsService;
import com.talents.acquisition.webtoken.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@Slf4j
public class RegistrationController {


    private final MyUserRepo myUserRepo;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    public RegistrationController(MyUserRepo myUserRepo, PasswordEncoder passwordEncoder) {
        this.myUserRepo = myUserRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register/user")
    public MyUser createUer(@RequestBody MyUser user) {
        log.info("Creating user: " + user.toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return myUserRepo.save(user);
    }

    @PostMapping("register/authenticate")
    public String authenticate(@RequestBody LoginForm loginForm) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(), loginForm.password()
        ));
        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(myUserDetailsService.loadUserByUsername(loginForm.username()));
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
