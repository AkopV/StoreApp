package com.vardanian.storeapp.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.vardanian.storeapp.R;
import com.vardanian.storeapp.model.Product;
import com.vardanian.storeapp.data.db.dao.DatabaseStorage;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductActivityFragment extends Fragment {

    private static final String ARG_PRODUCT_ID = "product_id";

    @BindView(R.id.et_product_name)
    public EditText etProductName;
    @BindView(R.id.et_product_price)
    public EditText etProductPrice;
    private Product product;

    public ProductActivityFragment() {
    }

    public static ProductActivityFragment newInstance(UUID productId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT_ID, productId);

        ProductActivityFragment fragment = new ProductActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID productId = (UUID) getArguments().getSerializable(ARG_PRODUCT_ID);
        product = DatabaseStorage.get(getActivity()).getProduct(productId);
    }

    @Override
    public void onPause() {
        super.onPause();

        DatabaseStorage.get(getActivity()).updateProduct(product);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_product, container, false);

        ButterKnife.bind(this, root);
        initUI();
        return root;
    }

    public void initUI() {
        etProductName.setText(product.getProductName());
        etProductName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                product.setProductName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etProductPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = etProductPrice.getText().toString();
                if (input != null && input != "") {
                    product.setProductPrice(Double.parseDouble(charSequence.toString()));
                } else {
                    etProductPrice.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != Activity.RESULT_OK) {
            return;
        }
    }
}
