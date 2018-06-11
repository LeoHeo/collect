package com.collect.dto.item;

import com.collect.domain.item.Item;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Builder;
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
public class SaveItemDto {

  @NotNull
  private String name;

  @NotNull
  private BigDecimal price;

  @NotNull
  private int stockQuantity;

  public Item toEntity() {
    return Item.builder()
        .name(name)
        .price(price)
        .stockQuantity(stockQuantity)
        .build();
  }
}
