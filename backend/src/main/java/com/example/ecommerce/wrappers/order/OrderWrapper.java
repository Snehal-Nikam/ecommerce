package com.example.ecommerce.wrappers.order;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.User;

import javax.validation.constraints.NotNull;
import java.sql.Struct;

public class OrderWrapper {
    private Integer id;
    private @NotNull Integer userId;

    private String userName;

    private String userEmail;

    private String address;

    private String contact;

    private Double totalPrice;

    private String status;


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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderWrapper(Integer id, Integer userId, String userName, String userEmail, String address, String contact, Double totalPrice, String status) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.address = address;
        this.contact = contact;
        this.totalPrice = totalPrice;
        this.status = status;
    }
}
