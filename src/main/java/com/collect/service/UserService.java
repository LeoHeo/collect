package com.collect.service;

import com.collect.domain.user.AuthorityName;
import com.collect.domain.user.repository.AuthorityReposity;
import com.collect.domain.user.User;
import com.collect.domain.user.repository.UserRepository;
import com.collect.dto.user.UserSaveDto;
import com.collect.exception.AlreadyRegisterUserException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Heo, Jin Han
 * @since 2018-04-06
 */
@AllArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final AuthorityReposity authorityReposity;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Transactional
  public void saveUser(UserSaveDto userSaveDto) {
  userSaveDto.setPassword(bCryptPasswordEncoder.encode(userSaveDto.getPassword()));
  User findUser = userRepository.findByUsername(userSaveDto.getUsername());

  if (findUser != null) {
    throw new AlreadyRegisterUserException("Already register user");
  }

  User saveUser = userSaveDto.toEntity();
  saveUser.addAuthority(authorityReposity.findByName(AuthorityName.ROLE_USER));
  userRepository.save(saveUser);
}
}
