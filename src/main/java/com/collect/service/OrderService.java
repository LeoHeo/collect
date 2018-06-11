package com.collect.service;

import com.collect.domain.order.repository.OrderRepository;
import com.collect.dto.order.SaveOrderDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Heo, Jin Han
 * @since 2018-05-16
 */

@AllArgsConstructor
@Service
public class OrderService {

  private final OrderRepository orderRepository;

  @Transactional
  public void saveOrder(SaveOrderDto saveOrderDto) {
    orderRepository.save(saveOrderDto.toEntity());
  }
}
