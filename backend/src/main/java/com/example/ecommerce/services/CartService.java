package com.example.ecommerce.services;

import com.example.ecommerce.exceptions.CustomException;
import com.example.ecommerce.exceptions.ProductNotExistsException;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repos.CartRepository;
import com.example.ecommerce.wrappers.cart.AddToCartWrapper;
import com.example.ecommerce.wrappers.cart.CartWrapper;
import com.example.ecommerce.wrappers.cart.CartItemWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    ProductService productService;

    @Autowired
    CartRepository cartRepository;

    public void addToCart(AddToCartWrapper addToCartWrapper, User user) throws ProductNotExistsException {

        // validate if the product id is valid
        Product product = productService.getProduct(addToCartWrapper.getProductId());//findById(addToCartDto.getProductId());

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartWrapper.getQuantity());
        cart.setCreatedDate(new Date());


        // save the cart
        cartRepository.save(cart);

    }

    public CartWrapper listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<CartItemWrapper> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart: cartList) {
            CartItemWrapper cartItemWrapper = new CartItemWrapper(cart);
            totalCost += cartItemWrapper.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemWrapper);
        }

        CartWrapper cartWrapper = new CartWrapper();
        cartWrapper.setTotalCost(totalCost);
        cartWrapper.setCartItems(cartItems);
        return cartWrapper;
    }

    public void deleteCartItem(Integer cartItemId, User user) {
        // the item id belongs to user

        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);

        if (optionalCart.isEmpty()) {
            throw new CustomException("cart item id is invalid: " + cartItemId);
        }

        Cart cart = optionalCart.get();

        if (cart.getUser() != user) {
            throw  new CustomException("cart item does not belong to user: " +cartItemId);
        }

        cartRepository.delete(cart);


    }

    public void deleteUserCartItems(User user) {
        cartRepository.deleteByUser(user);
    }
}
