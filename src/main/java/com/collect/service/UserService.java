package com.collect.service;

import com.collect.domain.user.UserRepository;
import com.collect.dto.user.UserSaveDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Heo, Jin Han
 * @since 2018-04-06
 */
@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public Long saveUser(UserSaveDto userSaveDto) {
    return userRepository.save(userSaveDto.toEntity()).getId();
  }
}
