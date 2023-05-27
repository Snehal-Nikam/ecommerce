package com.example.ecommerce.utils.cart;

import java.util.List;

public class CartUtil {
    private List<CartItemUtil> cartItems;
    private double totalCost;

    public CartUtil() {
    }

    public List<CartItemUtil> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemUtil> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
