package com.collect.web;

import com.collect.dto.order.SaveOrderDto;
import com.collect.exception.BadRequestException;
import com.collect.service.OrderService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Heo, Jin Han
 * @since 2018-05-16
 */
@RestController
@RequestMapping("/orders/**")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public void saveOrder(@Valid @RequestBody SaveOrderDto saveOrderDto, BindingResult result) {
    if (result.hasErrors()) {
      throw new BadRequestException("missing required value");
    }

    orderService.saveOrder(saveOrderDto);
  }
}
