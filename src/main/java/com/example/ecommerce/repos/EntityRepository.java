package com.example.ecommerce.repos;

import com.example.ecommerce.model.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository extends JpaRepository<Entity, Integer> {
}
