package com.collect.security;

import com.collect.domain.user.User;
import com.collect.domain.user.repository.UserRepository;
import java.util.Optional;
import javax.jws.soap.SOAPBinding.Use;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Heo, Jin han
 * @since 2018-04-05
 */
@Service
@Primary
public class JwtUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public JwtUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> findUser = userRepository.findByEmail(username);

    if (!findUser.isPresent()) {
      throw new UsernameNotFoundException("Not found username: " + username);
    }

    return JwtUserFactory.create(findUser.get());
  }
}
