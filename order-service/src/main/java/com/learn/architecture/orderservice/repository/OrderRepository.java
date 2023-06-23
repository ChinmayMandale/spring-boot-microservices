package com.learn.architecture.orderservice.repository;

import com.learn.architecture.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
