package com.example.fooddeli.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeli.Adaptor.CategoryAdaptor;
import com.example.fooddeli.Adaptor.PopularAdaptor;
import com.example.fooddeli.Domain.CategoryDomain;
import com.example.fooddeli.Domain.FoodDomain;
import com.example.fooddeli.R;
import com.example.fooddeli.Helper.TinyDB;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;
    private CardView cartButton;
    private LinearLayout settingsContainer, profileContainer;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            recyclerViewCategory();
            recyclerViewPopular();
            bottomNavigation();
            TinyDB tinyDB = new TinyDB(this);
            String userName = tinyDB.getString("user_name");
            TextView userNameText = findViewById(R.id.user_name_text);
            if (userName != null && !userName.isEmpty()) {
                userNameText.setText(userName);
            }

            Button logoutBtn = findViewById(R.id.btn_logout);
            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tinyDB.putBoolean("is_logged_in", false);
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: " + e.getMessage());
        }
    }
    
    private void bottomNavigation() {
        try {
            cartButton = findViewById(R.id.cartButton);
            settingsContainer = findViewById(R.id.settingsContainer);
            profileContainer = findViewById(R.id.profileContainer);
            
            cartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, CartActivity.class));
                }
            });
            
            settingsContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Settings clicked");
                    showLogoutDialog();

                }
            });
            
            profileContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Show logout dialog
                    // showLogoutDialog();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error in bottomNavigation: " + e.getMessage());
        }
    }
    
    private void showLogoutDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void recyclerViewCategory() {
        try {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewCategoryList = findViewById(R.id.recyclerview);
            recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

            ArrayList<CategoryDomain> category = new ArrayList<>();
            category.add(new CategoryDomain("Pizza", "cat_1"));
            category.add(new CategoryDomain("Burger", "cat_2")); 
            category.add(new CategoryDomain("Hot dog", "cat_3"));
            category.add(new CategoryDomain("Drink", "cat_4"));
            category.add(new CategoryDomain("Donut", "cat_5"));

            adapter = new CategoryAdaptor(category);
            recyclerViewCategoryList.setAdapter(adapter);
        } catch (Exception e) {
            Log.e(TAG, "Error in recyclerViewCategory: " + e.getMessage());
        }
    }

    private void recyclerViewPopular() {
        try {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewPopularList = findViewById(R.id.recyclerViewPopular);
            recyclerViewPopularList.setLayoutManager(linearLayoutManager);

            ArrayList<FoodDomain> foodList = new ArrayList<>();
            foodList.add(new FoodDomain("Pepperoni pizza", "pop_1", 
                    "slices pepperoni, mozzerella cheese, fresh oregano, ground black pepper, pizza sauce", 900.0));
            foodList.add(new FoodDomain("Cheese Burger", "cat_2", 
                    "beef, Gouda Cheese, Special Sauce, Lettuce, tomato", 800.0));
            foodList.add(new FoodDomain("Vegetable pizza", "pizza", 
                    "olive oil, Vegetable oil, pitted kalamata, cherry tomatoes, fresh oregano, basil", 850.0));

            adapter2 = new PopularAdaptor(foodList);
            recyclerViewPopularList.setAdapter(adapter2);
        } catch (Exception e) {
            Log.e(TAG, "Error in recyclerViewPopular: " + e.getMessage());
        }
    }
}