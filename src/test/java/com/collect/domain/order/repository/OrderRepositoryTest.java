package com.collect.domain.order.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.collect.domain.item.Item;
import com.collect.domain.item.ItemRepository;
import com.collect.domain.order.Order;
import com.collect.domain.orderitem.OrderItemRepository;
import com.collect.domain.user.Provider;
import com.collect.domain.user.User;
import com.collect.domain.user.repository.AuthorityReposity;
import com.collect.domain.user.repository.UserRepository;
import com.collect.dto.item.SaveItemDto;
import com.collect.dto.order.SaveOrderDto;
import com.collect.dto.orderItem.SaveOrderItemDto;
import com.collect.dto.user.UserSaveDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Heo, Jin Han
 * @since 2018-05-16
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class OrderRepositoryTest {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthorityReposity authorityReposity;

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Test
  public void should_save_order_expect_200 () {
    // 유저 생성
    final UserSaveDto userSaveDto = new UserSaveDto();
    userSaveDto.setUsername("hjh548866");
    userSaveDto.setAddress("seoul");
    userSaveDto.setPassword("123456");
    userSaveDto.setEmail("hjh5488@gmail.com");
    userSaveDto.setProvider(Provider.create("google"));

    // 아이템 생성
    final SaveItemDto saveItemDto = new SaveItemDto();
    saveItemDto.setName("desk");
    saveItemDto.setPrice(new BigDecimal(150000));
    saveItemDto.setStockQuantity(2);
    final Item item = itemRepository.save(saveItemDto.toEntity());

    // 유저 저장
    final User saveUser = userRepository.save(userSaveDto.toEntity());

    // 주문 생성
    final SaveOrderDto saveOrderDto = new SaveOrderDto();
    saveOrderDto.setOrderDate(LocalDateTime.now());
    final Order order = saveOrderDto.toEntity();
    order.setUser(saveUser);

    // 주문 저장
    final Order saveOrder = orderRepository.save(order);

    // 주문과 아이템 연관관계 설정
    final SaveOrderItemDto saveOrderItemDto = new SaveOrderItemDto();
    saveOrderItemDto.setOrder(saveOrder);
    saveOrderItemDto.setItem(item);

    orderItemRepository.save(saveOrderItemDto.toEntity());

    final Optional<User> findUser = userRepository.findByEmail("hjh5488@gmail.com");
    final List<Order> orders = findUser.get().getOrders();

    assertThat(orders).hasSize(1);

  }

}