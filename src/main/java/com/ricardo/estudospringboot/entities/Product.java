package com.ricardo.estudospringboot.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String  name;
    private Double price;
    private String ImgUrl;

    @Transient
    private Set<Category> categories = new HashSet<>();



    public Product(){};

    public Product(Integer id, String name, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        ImgUrl = imgUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.ImgUrl = imgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(getId(), product.getId());
    }

    public Set<Category> getCategories() {
        return categories;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
