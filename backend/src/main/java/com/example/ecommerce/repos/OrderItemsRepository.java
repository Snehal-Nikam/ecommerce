package com.example.ecommerce.repos;

import com.example.ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderItemsRepository extends JpaRepository<OrderItem, Integer>{
}
