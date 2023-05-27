package com.example.ecommerce.controller;

import com.example.ecommerce.wrappers.product.ProductWrapper;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.services.CategoryService;
import com.example.ecommerce.services.ProductService;
import com.example.ecommerce.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ResponseUtil> create(@RequestBody ProductWrapper product){
        try {
            Optional<Category> category = categoryService.getCategory(product.getCategoryId());
            if (!category.isPresent())
                return new ResponseEntity<>(new ResponseUtil(false, "Invalid category. Category doesn't exist"), HttpStatus.CONFLICT);

            productService.create(product, category.get());
            return new ResponseEntity<>(new ResponseUtil(true, "Product is created"), HttpStatus.CREATED);
        }
        catch (Exception e){
            System.out.println(" Message "+ e.getMessage() +" Stack trace : "+ Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(new ResponseUtil(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/showAll")
    public ResponseEntity<List<ProductWrapper>> showAllProduct(){
        List<Product> products = productService.getAllProducts();
        List<ProductWrapper> allProducts= new ArrayList<ProductWrapper>();

        for(Product product:products) {
            allProducts.add(productService.createProductWrapper(product));
        }
        return new ResponseEntity<>(allProducts,HttpStatus.OK);
    }

    @PostMapping("edit/{productId}")
    public ResponseEntity<ResponseUtil> editProduct(@PathVariable("productId") Integer Id, @Valid @RequestBody ProductWrapper product){
        //if entity exist update
        try {
            if(Objects.nonNull(productService.getProduct(Id))){
                Optional<Category> category = categoryService.getCategory(product.getCategoryId());
                if (!category.isPresent()) {
                    return new ResponseEntity<ResponseUtil>(new ResponseUtil(false, "category does not exists"), HttpStatus.BAD_REQUEST);
                }
                productService.update(Id , product, category.get());
                return new ResponseEntity<>(new ResponseUtil(true, "Product is updated successfully"), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(new ResponseUtil(false, "Product doesn't exist in the system"), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            System.out.println(" Message "+ e.getMessage() +" Stack trace : "+ e.getStackTrace());
            return new ResponseEntity<ResponseUtil>(new ResponseUtil(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
