package com.latest.springlatest.security.user.repository;

import com.latest.springlatest.security.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}