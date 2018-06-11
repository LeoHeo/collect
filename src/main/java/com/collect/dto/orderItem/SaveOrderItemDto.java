package com.collect.dto.orderItem;

import com.collect.domain.item.Item;
import com.collect.domain.order.Order;
import com.collect.domain.orderitem.OrderItem;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Heo, Jin Han
 * @since 2018-06-06
 */
@Getter
@Setter
@NoArgsConstructor
public class SaveOrderItemDto {

  @NotNull
  private Item item;

  @NotNull
  private Order order;

  public OrderItem toEntity() {
    return OrderItem.builder()
        .item(item)
        .order(order)
        .build();
  }
}
