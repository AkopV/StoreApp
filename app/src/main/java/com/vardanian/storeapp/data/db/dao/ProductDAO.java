package com.vardanian.storeapp.data.db.dao;

import com.vardanian.storeapp.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductDAO {

    public void addProduct(Product product);
    public Product getProduct(UUID id);
    public List<Product> getProducts();
    public void saveProducts(List<Product> products);
    public void updateProduct(Product product);
    public boolean deleteProduct(Product product);
}
