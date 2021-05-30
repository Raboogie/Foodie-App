package com.example.foodieapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MealItemActivity extends AppCompatActivity {
    private TextView mealTitle;
    private TextView mealInfo;
    private ImageView mealImage;

    private static final String TAG = "MealItemActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_item);

        mealTitle = findViewById(R.id.tvMealTitle);
        mealInfo = findViewById(R.id.tvMealInfo);
        mealImage = (ImageView) findViewById(R.id.imageViewMealItem);

        if (getIntent().hasExtra("selected_items")) {
            MealItem meal = getIntent().getParcelableExtra("selected_items");
            mealTitle.setText(meal.getTitle());
            mealInfo.setText(meal.getInfo());
            mealImage.setImageResource(meal.getImageId());
            Log.d(TAG,"MealItemActivity Inside onCreate: ");
        }

        if (getIntent().hasExtra("random_selected")) {
            MealItem meal = getIntent().getParcelableExtra("random_selected");
            mealTitle.setText(meal.getTitle());
            mealInfo.setText(meal.getInfo());
            mealImage.setImageResource(meal.getImageId());
            Toast.makeText(this, "HAPPY COOKING " + mealTitle.getText(), Toast.LENGTH_SHORT).show();
            Log.d(TAG,"MealItemActivity Inside onCreate: Triggered by random_selected intent");
        }
    }
}