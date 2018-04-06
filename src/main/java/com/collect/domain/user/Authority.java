package com.collect.domain.user;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Heo, Jin Han
 * @since 2018-04-04
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@EqualsAndHashCode
public class Authority {

  @Id
  @GeneratedValue
  private Long id;

  @Enumerated(EnumType.STRING)
  private AuthorityName name;

  @ManyToMany(mappedBy = "authorities")
  private List<User> users = new ArrayList<>();
}
