package com.vk.purchasetime.models;

import org.springframework.boot.autoconfigure.web.WebProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String productName;
    private double cost;
    private String category;
    private double discount;
    private String url;

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "product")
    private List<Hub> hubs = new ArrayList<>();


    public List<Hub> getHubs() {
        return hubs;
    }

    public void setHubs(List<Hub> hubs) {
        this.hubs = hubs;
    }



    public Product() {
    }

    public Product(String productName, double cost, ProductCategory category, double discount,String url) {
        this.url = url;
        this.productName = productName;
        this.cost = cost;
        this.category = category.toString();
        this.discount = discount;
    }


    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setCategory(ProductCategory category) {
        this.category = category.toString();
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getCost() {
        return cost;
    }

    public ProductCategory getCategory() {
        return ProductCategory.valueOf(category);
    }

    public double getDiscount() {
        return discount;
    }
}
