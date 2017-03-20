package com.vardanian.storeapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.vardanian.storeapp.R;
import com.vardanian.storeapp.data.db.dao.DatabaseStorage;
import com.vardanian.storeapp.model.Product;

import java.util.List;
import java.util.UUID;

public class ProductPagerActivity extends AppCompatActivity {

    private static final String EXTRA_PRODUCT_ID = "com.vardanian.storeapp.product_id";

    public ViewPager vp;
    private List<Product> products;

    public static Intent newIntent(Context packageContext, UUID productId) {
        Intent intent = new Intent(packageContext, ProductPagerActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_pager);

        UUID productId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_PRODUCT_ID);

        vp = (ViewPager) findViewById(R.id.activity_product_pager_view_pager);
        products = DatabaseStorage.get(this).getProducts();
        FragmentManager fragmentManager = getSupportFragmentManager();
        vp.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Product product = products.get(position);
                return ProductActivityFragment.newInstance(product.getId());
            }

            @Override
            public int getCount() {
                return products.size();
            }
        });

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(productId)) {
                vp.setCurrentItem(i);
                break;
            }
        }
    }
}
