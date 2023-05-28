package com.example.ecommerce.controller;

import com.example.ecommerce.exceptions.ProductNotExistsException;
import com.example.ecommerce.model.User;
import com.example.ecommerce.services.AuthenticationService;
import com.example.ecommerce.services.CartService;
import com.example.ecommerce.utils.ResponseUtil;
import com.example.ecommerce.wrappers.cart.AddToCartWrapper;
import com.example.ecommerce.wrappers.cart.CartWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private AuthenticationService authenticationService;


    // post cart api
    @PostMapping("/add")
    public ResponseEntity<ResponseUtil> addToCart(@RequestBody AddToCartWrapper addToCart,
                                                  @RequestParam("token") String token) throws ProductNotExistsException {
        // authenticate the token
        authenticationService.authenticate(token);


        // find the user

        User user = authenticationService.getUser(token);


        cartService.addToCart(addToCart, user );

        return new ResponseEntity<>(new ResponseUtil(true, "Added to cart"), HttpStatus.CREATED);
    }


    // get all cart items for a user
    @GetMapping("/")
    public ResponseEntity<CartWrapper> getCartItems(@RequestParam("token") String token) {
        // authenticate the token
        authenticationService.authenticate(token);

        // find the user
        User user = authenticationService.getUser(token);

        // get cart items

        CartWrapper cartWrapper = cartService.listCartItems(user);
        return new ResponseEntity<>(cartWrapper, HttpStatus.OK);
    }

    // delete a cart item for a user

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ResponseUtil> deleteCartItem(@PathVariable("cartItemId") Integer itemId,
                                                      @RequestParam("token") String token) {

        // authenticate the token
        authenticationService.authenticate(token);

        // find the user
        User user = authenticationService.getUser(token);

        cartService.deleteCartItem(itemId, user);

        return new ResponseEntity<>(new ResponseUtil(true, "Item has been removed"), HttpStatus.OK);

    }
}
