package com.vardanian.storeapp.data.db;

public class ProductDbSchema {

    public static final class ProductTable {
        public static final String NAME = "products";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String PRODUCT_NAME = "product_name";
            public static final String PRODUCT_PRICE = "product_price";
        }
    }
}
