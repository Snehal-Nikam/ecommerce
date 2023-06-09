package com.example.ecommerce.wrappers.cart;

import java.util.List;

public class CartWrapper {
    private List<CartItemWrapper> cartItems;
    private double totalCost;

    public CartWrapper(List<CartItemWrapper> cartItemWrapperList, double totalCost) {
        this.cartItems = cartItemWrapperList;
        this.totalCost = totalCost;
    }

    public CartWrapper() {
    }
    
    public void setCartItems(List<CartItemWrapper> cartItems) {
        this.cartItems = cartItems;
    }

    public List<CartItemWrapper> getCartItems() {
        return cartItems;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
