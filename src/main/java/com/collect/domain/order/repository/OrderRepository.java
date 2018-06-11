package com.collect.domain.order.repository;

import com.collect.domain.order.Order;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Heo, Jin Han
 * @since 2018-05-16
 */

public interface OrderRepository extends JpaRepository<Order, UUID> {

}
