package com.example.ecommerce.services;

import com.example.ecommerce.exceptions.CustomException;
import com.example.ecommerce.exceptions.ProductNotExistsException;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repos.CartRepository;
import com.example.ecommerce.utils.cart.AddToCartUtil;
import com.example.ecommerce.utils.cart.CartUtil;
import com.example.ecommerce.utils.cart.CartItemUtil;
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

    public void addToCart(AddToCartUtil addToCartUtil, User user) throws ProductNotExistsException {

        // validate if the product id is valid
        Product product = productService.getProduct(addToCartUtil.getProductId());//findById(addToCartDto.getProductId());

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartUtil.getQuantity());
        cart.setCreatedDate(new Date());


        // save the cart
        cartRepository.save(cart);

    }

    public CartUtil listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<CartItemUtil> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart: cartList) {
            CartItemUtil cartItemUtil = new CartItemUtil(cart);
            totalCost += cartItemUtil.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemUtil);
        }

        CartUtil cartUtil = new CartUtil();
        cartUtil.setTotalCost(totalCost);
        cartUtil.setCartItems(cartItems);
        return cartUtil;
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
}
