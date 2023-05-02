package com.example.ecommerce.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import javax.validation.constraints.NotBlank;


@jakarta.persistence.Entity
@Table(name = "categories")
public class Entity {
    @Id
    @GeneratedValue
    private Long id;

    private @NotBlank String entityName;

    private @NotBlank String description;

    private @NotBlank String imgURL;

    public Entity(){

    }
    public Entity(@NotBlank String entityName, @NotBlank String description) {
        this.entityName = entityName;
        this.description = description;
    }

    public Entity(@NotBlank String entityName, @NotBlank String description, @NotBlank String imageUrl) {
        this.entityName = entityName;
        this.description = description;
        this.imgURL = imageUrl;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String categoryName) {
        this.entityName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "User {category id=" + id + ", category name='" + entityName + "', description='" + description + "'}";
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setImgURL(String imgURL){
        this.imgURL=imgURL;
    }

    public String getImgURL(){
        return imgURL;
    }
}
