package com.collect.domain.user.repository;

import com.collect.domain.user.Authority;
import com.collect.domain.user.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Heo, Jin Han
 * @since 2018-04-06
 */
public interface AuthorityReposity extends JpaRepository<Authority, Long> {
  Authority findByName(AuthorityName authorityName);
}
