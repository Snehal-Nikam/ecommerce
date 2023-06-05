package com.example.ecommerce.services;

import com.example.ecommerce.exceptions.ProductNotExistsException;
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
        product.setProductName(productWrapper.getProductName());
        product.setDescription(productWrapper.getDescription());
        product.setImgURL(productWrapper.getImgURL());
        product.setPrice(productWrapper.getPrice());
        product.setProductName(productWrapper.getProductName());
        product.setActive(productWrapper.isActive());
        product.setUnitsInStock(productWrapper.getUnitsInStock());
        return product;
    }

    public Product getProduct(Integer productId) throws ProductNotExistsException {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotExistsException("product id is invalid: " + productId);
        }
        return optionalProduct.get();

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
        productWrapper.setActive(product.isActive());
        productWrapper.setUnitsInStock(product.getUnitsInStock());
        productWrapper.setProductName(product.getProductName());
        productWrapper.setId(product.getId());
        productWrapper.setProductName(product.getProductName());
        return productWrapper;
    }

    public void update(Integer prodId, ProductWrapper productWrapper, Category category) {
        Product product = getProductFromWrapper(productWrapper, category);
        product.setId(prodId);
        productRepo.save(product);
    }


}
