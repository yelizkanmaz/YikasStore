package com.example.yikasstore;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainMenuActivity extends AppCompatActivity {
    ImageView imgCats;
    ImageView imgDogs;
    ImageView imgTags;
    ImageView imgCollars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        init();
    }

    private void init() {
        imgCats = findViewById(R.id.imgCats);
        imgDogs = findViewById(R.id.imgDogs);
        imgTags = findViewById(R.id.imgTags);
        imgCollars = findViewById(R.id.imgCollars);

        imgCats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, CatsCategories.class));
            }
        });
        imgDogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, DogsCategories.class));
            }
        });

        imgTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, TagsActivity.class));
            }
        });

        imgCollars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, CollarsActivity.class));
            }
        });
    }
}
