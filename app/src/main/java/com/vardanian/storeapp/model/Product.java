package com.vardanian.storeapp.model;

import java.util.UUID;

public class Product {

    private UUID id;
    private String productName;
    private double productPrice;

    public Product() {
        id = UUID.randomUUID();
    }

    public Product(UUID id) {
        this.id = id;
    }

    public Product(UUID id, String productName, double productPrice) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public UUID getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
