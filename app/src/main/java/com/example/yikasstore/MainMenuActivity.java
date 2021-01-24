package com.example.yikasstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.menu_profile:
                Intent intent = new Intent(MainMenuActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.menu_campaigns:
                Intent intent2 = new Intent(MainMenuActivity.this, CampaignsActivity.class);
                startActivity(intent2);
                break;

            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent3 = new Intent(MainMenuActivity.this, LoginActivity.class);
                startActivity(intent3);
                break;

            case R.id.menu_contact:
                Intent intent4 = new Intent(MainMenuActivity.this, ContactsActivity.class);
                startActivity(intent4);
                break;

            case R.id.submenu_cats:
                Intent intent5 = new Intent(MainMenuActivity.this, CatsCategories.class);
                startActivity(intent5);
                break;

            case R.id.submenu_dogs:
                Intent intent6 = new Intent(MainMenuActivity.this, DogsCategories.class);
                startActivity(intent6);
                break;
        }
        return true;
    }
}
