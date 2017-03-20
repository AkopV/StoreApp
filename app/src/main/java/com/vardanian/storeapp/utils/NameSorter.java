package com.vardanian.storeapp.utils;

import com.vardanian.storeapp.model.Product;

import java.util.Comparator;

public class NameSorter implements Comparator<Product> {

    int order = -1;

    public NameSorter(int order) {
        this.order = order;
    }

    public int compare(Product lhs, Product rhs) {
        return lhs.getProductName().compareTo(rhs.getProductName());
    }
}
