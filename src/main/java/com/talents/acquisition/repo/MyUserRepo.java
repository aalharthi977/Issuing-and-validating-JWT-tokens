package com.talents.acquisition.repo;

import com.talents.acquisition.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserRepo extends JpaRepository<MyUser, Integer> {
    Optional<MyUser> findByUsername(String username);
}
