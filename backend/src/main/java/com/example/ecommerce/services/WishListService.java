package com.example.ecommerce.services;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.WishList;
import com.example.ecommerce.repos.WishListRepository;
import com.example.ecommerce.wrappers.product.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    ProductService productService;
    CategoryService categoryService;
    public void creatWishlist(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<ProductWrapper> getWishListForUser(User user) {
        final List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<ProductWrapper> productWrappers = new ArrayList<>();
        for (WishList wishList: wishLists) {
            Product product = wishList.getProduct();
            //String id = String.valueOf(product.getCategory());
            productWrappers.add(productService.createProductWrapper(product));
        }
        return productWrappers;
    }


}
