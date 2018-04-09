package com.collect.domain.user;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Heo, Jin Han
 * @since 2018-03-31
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@EqualsAndHashCode
public class User {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String address;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Provider provider;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private boolean enabled;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "USER_AUTHORITY",
      joinColumns = { @JoinColumn( name = "USER_ID", referencedColumnName = "ID")},
      inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")}
  )
  private List<Authority> authorities = new ArrayList<>();

  public void addAuthority(Authority authority) {
    authorities.add(authority);
    authority.getUsers().add(this);
  }


  @Builder
  public User(
      String username,
      String password,
      String address,
      Provider provider,
      String email,
      boolean enabled,
      List<Authority> authorities
  ) {
    this.username = username;
    this.password = password;
    this.address = address;
    this.provider = provider;
    this.email = email;
    this.enabled = enabled;
    this.authorities = authorities;
  }
}
