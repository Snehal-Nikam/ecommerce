package com.example.ecommerce.services;

import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepo;

    public void create(ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        productRepo.save(product);
    }
    public static Product getProductFromDto(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setImgURL(productDto.getImgURL());
        product.setPrice(productDto.getPrice());
        product.setProductName(productDto.getName());
        return product;
    }
}
