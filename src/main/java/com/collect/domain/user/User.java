package com.collect.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Heo, Jin Han
 * @since 2018-03-31
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class User {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String address;

  @Enumerated(EnumType.STRING)
  private Provider provider;

  @Builder
  public User(String email, String password, String address, Provider provider) {
    this.email = email;
    this.password = password;
    this.address = address;
    this.provider = provider;
  }
}
