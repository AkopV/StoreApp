package com.vardanian.storeapp.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class ProductActivity extends SingleFragmentActivity {

    public static final String EXTRA_PRODUCT_ID = "com.vardanian.storeapp.product_id";

    public static Intent newIntent(Context packageContext, UUID productId) {
        Intent intent = new Intent(packageContext, ProductActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID productId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_PRODUCT_ID);
        return ProductActivityFragment.newInstance(productId);
    }
}
