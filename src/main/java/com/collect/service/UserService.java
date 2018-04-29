package com.collect.service;

import com.collect.domain.user.AuthorityName;
import com.collect.domain.user.Provider;
import com.collect.domain.user.User;
import com.collect.domain.user.repository.AuthorityReposity;
import com.collect.domain.user.repository.UserRepository;
import com.collect.dto.user.UserSaveDto;
import com.collect.dto.user.ValidEmailDto;
import com.collect.exception.AlreadyRegisterUserException;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
  public User saveUser(UserSaveDto userSaveDto) {
    userSaveDto.setPassword(bCryptPasswordEncoder.encode(userSaveDto.getPassword()));
    Optional<User> findUser = userRepository.findByEmail(userSaveDto.getEmail());

    if (findUser.isPresent()) {
      return findUser.get();
    }

    final User saveUser = userSaveDto.toEntity();
    saveUser.addAuthority(authorityReposity.findByName(AuthorityName.ROLE_USER));
    userRepository.save(saveUser);

    return saveUser;
  }

  @Transactional
  public User saveOAuth2User(OAuth2AuthenticationToken user) {
    final Map<String, Object> attributes = user.getPrincipal().getAttributes();
    final Optional<User> findUser = userRepository.findByEmail(attributes.get("email").toString());
    final String id = Optional.ofNullable(attributes.get("sub")).orElse(attributes.get("id")).toString();

    final String findProvider = findUser.isPresent()
        ? findUser.get().getProvider().toString()
        : user.getAuthorizedClientRegistrationId();

    final boolean isSameProvider = findProvider.equalsIgnoreCase(user.getAuthorizedClientRegistrationId());


    if (findUser.isPresent() && isSameProvider) {
      return findUser.get();
    }

    if (findUser.isPresent() && !isSameProvider) {
      throw new AlreadyRegisterUserException("you've already another account");
    }

    final String password = bCryptPasswordEncoder.encode(id + attributes.get("email").toString());

    final UserSaveDto newUser = UserSaveDto.builder()
        .username(attributes.get("name").toString())
        .email(attributes.get("email").toString())
        .password(password)
        .provider(Provider.create(user.getAuthorizedClientRegistrationId()))
        .build();

    final User saveUser = newUser.toEntity();

    saveUser.addAuthority(authorityReposity.findByName(AuthorityName.ROLE_USER));
    userRepository.save(saveUser);

    return saveUser;
  }

  @Transactional(readOnly = true)
  public ValidEmailDto findByEmail(ValidEmailDto validEmailDto) {
    final Optional<User> findUser = userRepository.findByEmail(validEmailDto.getEmail());

    if (!findUser.isPresent()) {
      throw new IllegalArgumentException("invalid email");
    }

    return validEmailDto;
  }
}
