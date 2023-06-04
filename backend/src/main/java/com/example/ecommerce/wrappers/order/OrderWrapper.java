package com.example.ecommerce.wrappers.order;

import com.example.ecommerce.model.Order;

import javax.validation.constraints.NotNull;
public class OrderWrapper {
    private Integer id;
    private @NotNull Integer userId;

    public OrderWrapper() {
    }

    public OrderWrapper(Order order) {
        this.setId(order.getId());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
