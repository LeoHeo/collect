package com.collect.domain.orderitem;

import com.collect.domain.item.Item;
import com.collect.domain.order.Order;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Heo, Jin Han
 * @since 2018-05-15
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "ITEM_ID")
  private Item item;

  @ManyToOne
  @JoinColumn(name = "ORDER_ID")
  private Order order;

  public void addOrder(Order order) {
    this.order = order;
  }

  @Builder
  public OrderItem(Item item, Order order) {
    this.item = item;
    this.order = order;
  }
}
