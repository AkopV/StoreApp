package com.vardanian.storeapp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.vardanian.storeapp.R;
import com.vardanian.storeapp.view.ProductActivityFragment;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_product_list);
        if (fragment == null) {
            fragment = new ProductActivityFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_product_list, fragment)
                    .commit();
        }
    }
}
