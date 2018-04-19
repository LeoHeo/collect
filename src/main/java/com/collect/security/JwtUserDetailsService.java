package com.collect.security;

import com.collect.domain.user.User;
import com.collect.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Heo, Jin Han
 * @since 2018-04-05
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public JwtUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username);

    if (user == null) {
      throw new UsernameNotFoundException("Not found username: " + username);
    }

    return JwtUserFactory.create(user);
  }
}
