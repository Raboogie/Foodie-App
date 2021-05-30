package com.example.foodieapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.foodieapp.FoodChooserActivity.KEY_FOOD_CHOICE;
import static com.example.foodieapp.FoodChooserActivity.KEY_FOOD_IMAGE_ID;

public class AddItemActivity extends AppCompatActivity {
    private static final String TAG = "AddItemActivity";

    private static final int FOOD_CHOOSER_REQUEST = 2;// REQUEST CODE
    protected static final String KEY_FOOD_DESCRIPTION = "Food Description";

    private TextView tvMealTitle;
    private EditText etDescription, etMealIngredients, etMealCalories, etMealLink;
    private ImageView ivNewMealItem = null;

    private String userInputDescription, userInputIngredients;
    private int userCalories;
    private String userRecipeLink;

    MealItem meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        tvMealTitle = findViewById(R.id.tvMealTitle);
        etDescription = findViewById(R.id.etDescription);
        etMealIngredients = findViewById(R.id.etIngredients);
        etMealCalories = findViewById(R.id.etCalories);
        etMealLink = findViewById(R.id.etFoodLink);

      /*  if (getIntent().hasExtra("KEY_MEAL_ITEM_LIST")) {
            meal = getIntent().getParcelableExtra("KEY_MEAL_ITEM_LIST");
        }*/
    }

    public void launchFoodChooserActivity(View view) {
        Log.d(TAG, "Inside launchFoodChooserActivity method");

        Intent intentLaunchFoodChooserActivity = new Intent(this, FoodChooserActivity.class);
        startActivityForResult(intentLaunchFoodChooserActivity, FOOD_CHOOSER_REQUEST);

        Log.d(TAG, "End of launchFoodChooserActivity method");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "Inside onActivityResult method");

        if (resultCode == RESULT_OK && requestCode == FOOD_CHOOSER_REQUEST) {
            String foodChosen = data.getStringExtra(KEY_FOOD_CHOICE);
            String foodDescription = data.getStringExtra((KEY_FOOD_IMAGE_ID));
            tvMealTitle.setText(foodChosen);

            if (etDescription != null) {
                userInputDescription = etDescription.getText().toString();
            }

            if (etMealIngredients != null) {
                userInputIngredients = etMealIngredients.getText().toString();
            }
            if (etMealCalories != null) {
                userCalories =  Integer.parseInt(etMealCalories.getText().toString());
            }

            if (etMealLink != null) {
                userRecipeLink = etMealLink.getText().toString();
            }

            Intent intentFoodChoice = new Intent();
            intentFoodChoice.putExtra(KEY_FOOD_CHOICE, foodChosen);
            intentFoodChoice.putExtra(KEY_FOOD_DESCRIPTION,userInputDescription);
            intentFoodChoice.putExtra(KEY_FOOD_IMAGE_ID,foodDescription);
            setResult(RESULT_OK, intentFoodChoice);
            finish();
        } else {
            Log.d(TAG, "Problem Inside Add Item Activity");
        }
        Log.d(TAG, "End of onActivityResult method");
    }
}