package com.example.ecommerce.services;

import com.example.ecommerce.model.WishList;
import com.example.ecommerce.repos.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    @Autowired
    WishListRepository wishListRepository;

    public void creatWishlist(WishList wishList) {
        wishListRepository.save(wishList);
    }
}
