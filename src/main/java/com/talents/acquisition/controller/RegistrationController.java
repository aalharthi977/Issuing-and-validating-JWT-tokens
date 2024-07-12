package com.talents.acquisition.controller;


import com.talents.acquisition.model.MyUser;
import com.talents.acquisition.repo.MyUserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
}
