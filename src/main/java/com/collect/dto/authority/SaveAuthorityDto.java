package com.collect.dto.authority;

import com.collect.domain.user.Authority;
import com.collect.domain.user.AuthorityName;
import com.collect.domain.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Heo, Jin Han
 * @since 2018-06-07
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class SaveAuthorityDto {

  @NotNull
  private AuthorityName name;

  private List<User> users = new ArrayList<>();

  public Authority toEntity() {
    return Authority.builder()
        .name(name)
        .users(users)
        .build();
  }
}
