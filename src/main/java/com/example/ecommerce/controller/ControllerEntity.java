package com.example.ecommerce.controller;

import com.example.ecommerce.model.Entity;
import com.example.ecommerce.services.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/entity")
public class ControllerEntity {
    @Autowired
    EntityService entityService;
    @PostMapping("/create")
    public String createEntity(@RequestBody Entity entity){
        try {
            entityService.create(entity);
            return "success";
        }
        catch (Exception e){
            System.out.println(" Message "+ e.getMessage() +" Stack trace : "+ e.getStackTrace());
            return "failed to created record";

        }

    }
}
