package com.example.yikasstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ProductDetailActivity extends AppCompatActivity {
RecyclerView rvProductDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        rvProductDetail = findViewById(R.id.rvProductDetail);

    }
}
