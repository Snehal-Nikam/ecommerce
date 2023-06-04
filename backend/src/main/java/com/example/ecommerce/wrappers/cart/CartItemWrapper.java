package com.example.ecommerce.wrappers.cart;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Product;
import javax.validation.constraints.NotNull;


public class CartItemWrapper {
    private Integer id;
    private @NotNull Integer quantity;
    private @NotNull Product product;

    public CartItemWrapper() {
    }
    public CartItemWrapper(Cart cart) {
        this.id = cart.getId();
        this.quantity = cart.getQuantity();
        this.setProduct(cart.getProduct());
    }

    @Override
    public String toString() {
        return "CartWrapper{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", productName=" + product.getProductName() +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
