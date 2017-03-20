package com.vardanian.storeapp.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vardanian.storeapp.R;
import com.vardanian.storeapp.model.Product;
import com.vardanian.storeapp.view.ProductActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private static final String LOG_TAG = ProductAdapter.class.getSimpleName();
    private static final int REQUEST_PRODUCT = 1;
    private List<Product> products;
    private Context context;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_item_product, viewGroup, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductHolder productHolder, int position) {
        productHolder.product = getItem(position);
        productHolder.bindProductItem(productHolder.product);
    }

    public Product getItem(int position) {
        return products != null ? products.get(position) : null;
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }


    class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_product_price)
        TextView tvProductPrice;
        private Product product;
        private Context context;

        public ProductHolder(View itemView) {
            super(itemView);
            product = new Product();
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        public void bindProductItem(final Product product) {
            this.product = product;
            tvProductName.setText(product.getProductName());
            tvProductPrice.setText(String.valueOf(product.getProductPrice()));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.menu_item_sort_products_by_name:
            }
            Intent intent = ProductActivity.newIntent(v.getContext(), product.getId());
            ((Activity) context).startActivityForResult(intent, REQUEST_PRODUCT);
        }
    }
}
