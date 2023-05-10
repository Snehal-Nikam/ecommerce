package com.example.ecommerce.services;

import com.example.ecommerce.wrappers.product.ProductWrapper;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepo;

    public void create(ProductWrapper productWrapper, Category category) {
        Product product = getProductFromWrapper(productWrapper, category);
        productRepo.save(product);
    }
    public Product getProductFromWrapper(ProductWrapper productWrapper, Category category) {
        Product product = new Product();
        product.setCategory(category);
        product.setDescription(productWrapper.getDescription());
        product.setImgURL(productWrapper.getImgURL());
        product.setPrice(productWrapper.getPrice());
        product.setProductName(productWrapper.getName());
        product.setActive(productWrapper.isActive());
        product.setUnitsInStock(productWrapper.getUnitsInStock());
        return product;
    }

    public Optional<Product> getProduct(Integer productId) {
        return productRepo.findById(productId);
    }
    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }

    public ProductWrapper createProductWrapper(Product product) {
        ProductWrapper productWrapper = new ProductWrapper();
        productWrapper.setCategoryId(product.getCategory().getId());
        productWrapper.setDescription(product.getDescription());
        productWrapper.setImgURL(product.getImgURL());
        productWrapper.setPrice(product.getPrice());
        product.setActive(productWrapper.isActive());
        product.setUnitsInStock(productWrapper.getUnitsInStock());
        productWrapper.setName(product.getProductName());
        return productWrapper;
    }

    public void update(Integer prodId, ProductWrapper productWrapper, Category category) {
        Product product = getProductFromWrapper(productWrapper, category);
        product.setId(prodId);
        productRepo.save(product);
    }

}
