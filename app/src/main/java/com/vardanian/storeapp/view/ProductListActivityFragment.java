package com.vardanian.storeapp.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vardanian.storeapp.R;
import com.vardanian.storeapp.model.Product;
import com.vardanian.storeapp.data.db.dao.DatabaseStorage;
import com.vardanian.storeapp.utils.NameSorter;
import com.vardanian.storeapp.utils.PriceFilter;
import com.vardanian.storeapp.view.ProductPagerActivity;
import com.vardanian.storeapp.view.adapter.ProductAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListActivityFragment extends Fragment {

    private static final int REQUEST_PRODUCT = 1;


    private static final String DIALOG_PRICE = "DialogPrice";
    private static final int REQUEST_PRICE = 0;


    @BindView(R.id.rv_product_list)
    public RecyclerView rv_productList;
    private List<Product> products;
    private ProductAdapter adapter;

    public ProductListActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_product_list, container, false);

        ButterKnife.bind(this, root);
        products = new ArrayList<>();
        rv_productList.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_product_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_product:
                Product product = new Product();
                DatabaseStorage.get(getActivity()).addProduct(product);
                Intent intent = ProductPagerActivity
                        .newIntent(getActivity(), product.getId());
                startActivity(intent);
                return true;
            case  R.id.menu_item_sort_products_by_name:
                products = DatabaseStorage.get(getActivity()).getProducts();
                Collections.sort(products, new NameSorter(-1));
                adapter = new ProductAdapter(getContext(), products);
                rv_productList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.menu_item_sort_products_by_price:
                products = DatabaseStorage.get(getActivity()).getProducts();
                Collections.sort(products, new PriceFilter());
                adapter = new ProductAdapter(getContext(), products);
                rv_productList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.menu_item_filter_products:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        DatabaseStorage databaseStorage = DatabaseStorage.get(getActivity());
        List<Product> products = databaseStorage.getProducts();

        if (adapter == null) {
            adapter = new ProductAdapter(getContext(), products);
            rv_productList.setAdapter(adapter);
        } else {
            adapter.setProducts(products);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PRODUCT) {

        }
    }
}
