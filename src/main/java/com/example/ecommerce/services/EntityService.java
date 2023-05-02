package com.example.ecommerce.services;

import com.example.ecommerce.model.Entity;
import com.example.ecommerce.repos.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityService {
    @Autowired
    EntityRepository entityRepo;

    public void create(Entity entity){
        entityRepo.save(entity);
    }
}
