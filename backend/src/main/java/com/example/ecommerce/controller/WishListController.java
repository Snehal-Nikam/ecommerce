package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.WishList;
import com.example.ecommerce.services.AuthenticationService;
import com.example.ecommerce.services.WishListService;
import com.example.ecommerce.utils.ResponseUtil;
import com.example.ecommerce.wrappers.product.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
    @Autowired
    WishListService wishListService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<ResponseUtil> addToWishList(@RequestBody Product product, @RequestParam("token") String token) {
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        WishList wishList = new WishList(user, product);
        wishListService.creatWishlist(wishList);
        //return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Add to wishlist"){}, HttpStatus.CREATED);
        ResponseUtil apiResponse = new ResponseUtil(true, "Added to the Wishlist.");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
        @GetMapping("/{token}")
        public ResponseEntity<List<ProductWrapper>> getWishList(@PathVariable("token") String token) {
            authenticationService.authenticate(token);
            User user = authenticationService.getUser(token);
            List<ProductWrapper> wishListForUser = wishListService.getWishListForUser(user);
            return new ResponseEntity<>(wishListForUser, HttpStatus.OK);
        }

    }

