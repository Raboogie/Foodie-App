package com.example.foodieapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.Resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;

public class FoodChooserActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "FoodChooserActivity";
    protected static final String KEY_FOOD_CHOICE = "food_choice";
    protected static final String KEY_FOOD_IMAGE_ID = "Food_Image_Id";

    ImageView[] imageButtons;
    GridLayout gridLayout;

    ImageView imageView;
    Resources res;
    Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_chooser);

        Log.d(TAG, "Inside onCreate method");

        gridLayout = findViewById(R.id.gridLayout_Food_Images);
        int numFoods = gridLayout.getChildCount();
        imageButtons = new ImageButton[numFoods];

        for (int i = 0; i < imageButtons.length; i++) {
            imageButtons[i] = (ImageButton) gridLayout.getChildAt(i);
            imageButtons[i].setOnClickListener(this);
        }
        Log.d(TAG, "End of onCreate method");
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "Inside of onClick");

        int viewId = v.getId();
        String textFoodChoice = findViewById(viewId).getContentDescription().toString();
        String imageId = returnDrawableAsString(textFoodChoice);

        Intent intentFoodChoice = new Intent();
        intentFoodChoice.putExtra(KEY_FOOD_CHOICE, textFoodChoice);
        intentFoodChoice.putExtra(KEY_FOOD_IMAGE_ID, imageId);
        setResult(RESULT_OK, intentFoodChoice);
        finish();

        ///// REVIEW CODE BELOW AND TRY TO GET IT TO WORK/////

        //int imageID = findViewById(viewId).
       /*///
        ImageView appIcon1ImageView = (ImageView)findViewById(viewId);
        appIcon1ImageView.setImageDrawable(v.getBackground());
        appIcon1ImageView.setTag(R.drawable.viewID);
        ///*/
        //int drawableId = Integer.parseInt(appIcon1ImageView.getTag().toString());
        ///
        //imageView = (ImageView)findViewById(viewId);
        //imageView.setImageDrawable(v.getBackground());
        //imageView.setTag(v.getBackground());

        //int drawableId = Integer.parseInt(imageView.getTag().toString());
        //res = getResources();
        //drawable = ResourcesCompat.getDrawable(res,imageView.getId(),null);
    }

    public static String returnDrawableAsString(String input) {
        String userFoodChoice = null;

        switch (input) {
            case "Chalupa Supreme":
                userFoodChoice = String.valueOf(R.drawable.chalupasupreme);
                break;
            case "Burrito Supreme":
                userFoodChoice = String.valueOf(R.drawable.burritosupreme);
                break;
            case "Beefy Layered Burrito":
                userFoodChoice = String.valueOf(R.drawable.beefylayerburrito);
                break;
            case "Soft Taco Supreme":
                userFoodChoice = String.valueOf(R.drawable.softtacosupreme);
                break;
            case "Nacho Cheese Doritos Locos Tacos Supreme":
                userFoodChoice = String.valueOf(R.drawable.nachocheesedoritoslocostacossupreme);
                break;
            case "nachos party pack beef":
                userFoodChoice = String.valueOf(R.drawable.nachospartypackbeef);
                break;
            case "nachos bell grande":
                userFoodChoice = String.valueOf(R.drawable.nachosbellgrande);
                break;
            case "Quesadilla":
                userFoodChoice = String.valueOf(R.drawable.quesadilla);
                break;
            case "Soft Taco":
                userFoodChoice = String.valueOf(R.drawable.softtaco);
                break;
            case "Cheese Quesadilla":
                userFoodChoice = String.valueOf(R.drawable.cheesequesadilla);
                break;
            case "crunchy taco supreme":
                userFoodChoice = String.valueOf(R.drawable.crunchytacosupreme);
                break;
            case "Chips and Nacho Cheese Sauce":
                userFoodChoice = String.valueOf(R.drawable.chipsandnachocheesesauce);
                break;
        }
        return userFoodChoice;
    }
}