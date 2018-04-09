package com.collect.domain.user.repository;

import com.collect.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Heo, Jin Han
 * @since 2018-03-31
 */
public interface UserRepository extends JpaRepository<User, Long> {
  User findByEmail(String email);
  User findByUsername(String username);
}
