package com.vardanian.storeapp.utils;

import com.vardanian.storeapp.model.Product;

import java.util.Comparator;

public class PriceFilter implements Comparator<Product> {

    public int compare(Product lhs, Product rhs) {
        int returnVal = 0;
        if(lhs.getProductPrice() < rhs.getProductPrice()){
            returnVal  =  -1;
        }else if(lhs.getProductPrice() > rhs.getProductPrice()){
            returnVal =  1;
        }else if(lhs.getProductPrice() == rhs.getProductPrice()){
            returnVal =  0;
        }
        return returnVal;
    }
}
