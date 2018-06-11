package com.collect.domain.order;

import com.collect.domain.orderitem.OrderItem;
import com.collect.domain.user.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author Heo, Jin Han
 * @since 2018-05-15
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ORDERS")
public class Order {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(columnDefinition = "BINARY(16)", name = "ORDER_ID")
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private User user;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems = new ArrayList<>();

  private LocalDateTime orderDate;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @Builder
  public Order(LocalDateTime orderDate, OrderStatus orderStatus) {
    this.orderDate = orderDate;
    this.orderStatus = orderStatus;
  }

  public void setUser(User user) {
    if (this.user != null) {
      this.user.getOrders().remove(this);
    }
    this.user = user;
    user.getOrders().add(this);
  }

  public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
    orderItem.addOrder(this);
  }

}
