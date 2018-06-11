package com.collect.dto.order;

import com.collect.domain.order.Order;
import com.collect.domain.order.OrderStatus;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

/**
 * @author Heo, Jin Han
 * @since 2018-05-16
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class SaveOrderDto {

  @NotNull
  @CreatedDate
  private LocalDateTime orderDate;

  @NotNull
  private OrderStatus orderStatus = OrderStatus.ORDER;

  public Order toEntity() {
    return Order.builder()
        .orderDate(orderDate)
        .orderStatus(orderStatus)
        .build();
  }
}
