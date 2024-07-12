package com.talents.acquisition.configuration;

import com.talents.acquisition.model.MyUser;
import com.talents.acquisition.repo.MyUserRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MyUserDetailsService implements UserDetailsService {


    private final MyUserRepo myUserRepo;

    public MyUserDetailsService(MyUserRepo myUserRepo) {
        this.myUserRepo = myUserRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        myUserRepo.findByUsername(username).ifPresentOrElse(
//                user -> return User.builder()
//                        .username()
//                        .password("$2a$12$fVji/uixGlV0UHQax9eBBeTdHT.ScOe3IMddds7BwtXknwZgJyDxK")
//                        .roles("ADMIN", "OPERATOR")
//                        .build();,
//                () -> throw new UsernameNotFoundException("User not found: " + username);
//        );

        Optional<MyUser> user = myUserRepo.findByUsername(username);
        if (user.isPresent()) {
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found" + username);
        }

    }

    private String[] getRoles(MyUser user) {
        if (user.getRole() == null){
            return new String[]{"USER"};
        }
        return user.getRole().split(",");
    }

}
