package com.vardanian.storeapp.data.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.vardanian.storeapp.data.db.ProductDbSchema.ProductTable;
import com.vardanian.storeapp.model.Product;

import java.util.UUID;

public class ProductCursorWrapper extends CursorWrapper {

    public ProductCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Product getProduct() {
        String uuidString = getString(getColumnIndex(ProductTable.Cols.UUID));
        String productName = getString(getColumnIndex(ProductTable.Cols.PRODUCT_NAME));
        double productPrice = getDouble(getColumnIndex(ProductTable.Cols.PRODUCT_PRICE));

        Product product = new Product(UUID.fromString(uuidString));
        product.setProductName(productName);
        product.setProductPrice(productPrice);

        return product;
    }
}
