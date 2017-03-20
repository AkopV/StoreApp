package com.vardanian.storeapp.data.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vardanian.storeapp.data.db.ProductCursorWrapper;
import com.vardanian.storeapp.data.db.ProductDbSchema;
import com.vardanian.storeapp.data.db.ProductDbSchema.ProductTable;
import com.vardanian.storeapp.data.db.ProductDbSchema.ProductTable.Cols;
import com.vardanian.storeapp.data.db.ProductOpenHelper;
import com.vardanian.storeapp.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.vardanian.storeapp.data.db.ProductDbSchema.ProductTable.NAME;

public class DatabaseStorage implements ProductDAO {

    private static final String TAG = DatabaseStorage.class.getSimpleName();
    private static DatabaseStorage databaseStorage;
    private Context context;
    private SQLiteDatabase db;

    public static DatabaseStorage get(Context context) {
        if (databaseStorage == null) {
            databaseStorage = new DatabaseStorage(context);
        }
        return databaseStorage;
    }

    private DatabaseStorage(Context context) {
        this.context = context.getApplicationContext();
        db = new ProductOpenHelper(context).getWritableDatabase();
    }

    private static ContentValues getContentValues(Product product) {
        ContentValues values = new ContentValues();
        values.put(Cols.UUID, String.valueOf(product.getId()));
        values.put(Cols.PRODUCT_NAME, product.getProductName());
        values.put(Cols.PRODUCT_PRICE, product.getProductPrice());

        return values;
    }

    private ProductCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = db.query(
                NAME,
                null, // Columns - null select all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new ProductCursorWrapper(cursor);
    }

    @Override
    public void addProduct(Product product) {
        ContentValues values = getContentValues(product);
        db.insert(NAME, null, values);
        Log.i(TAG, "Insert product to database");
    }

    @Override
    public Product getProduct(UUID id) {
        String[] whereArgs = new String[]{String.valueOf(id)};
        ProductCursorWrapper cursor = queryCrimes(
                ProductTable.Cols.UUID + " =?",
                whereArgs
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            Log.i(TAG, "Get product from database");
            return cursor.getProduct();
        } finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
            cursor.close();
        }
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        ProductCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                products.add(cursor.getProduct());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return products;
    }

    @Override
    public void saveProducts(List<Product> products) {
        for (Product product : products) {
            ContentValues values = getContentValues(product);
            if (hasProduct(product.getId())) {
                long id = db.insert(ProductTable.NAME, null, values);
                Log.i(TAG, "Inserted id=" + id);
            }
        }
        db.close();
    }

    @Override
    public void updateProduct(Product product) {
        String uuidString = product.getId().toString();
        ContentValues values = getContentValues(product);

        db.update(ProductTable.NAME, values,
                ProductTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    @Override
    public boolean deleteProduct(Product product) {
        boolean deleteSuccessfull = false;
        deleteSuccessfull = db.delete(
                NAME,
                Cols.UUID + " = " + product.getId(),
                null) > 0;
        db.close();

        return deleteSuccessfull;
    }

    public boolean hasProduct(UUID id) {
        String searchValue = "SELECT * FROM " + NAME + " WHERE " + Cols.UUID + " =?";
        // Add the String you are searching by here.
        // Put it in an array to avoid an unrecognized token error
        Cursor c = db.rawQuery(searchValue, new String[] {String.valueOf(id)});

        boolean hasProduct = false;
        if (c.moveToFirst()) {
            hasProduct = true;

            // Region if you had multiple records to check for, use this region.
            int count = 0;
            while (c.moveToNext()) {
                count++;
            }
            // Here, count is records found
            Log.d(TAG, String.format("%d records found", count));
        }
        c.close();
        db.close();
        return hasProduct;
    }
}
