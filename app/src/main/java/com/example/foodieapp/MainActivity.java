package com.example.foodieapp;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {
    //public static final String KEY_MEAL_ITEM_LIST = "Meal_Item_List";
    public static final int MEAL_ITEM_REQUEST = 1; // Request code
    private static final int CHOOSER_REQUEST = 5;
    protected static final String ACTION_CUSTOM_BROADCAST = "I_AM_HOME";
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private ArrayList<MealItem> mealItemData;
    private MealItemAdapter mealItemAdapter;

    private int gridColumnCount; // use column number in integers.xml

    private String[] mealItemTitles;
    private String[] mealItemInfo;
    private TypedArray mealItemImages;

    private FoodieReceiver foodieReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ///
        foodieReceiver = new FoodieReceiver();
        registerReceiver(foodieReceiver, addIntentFilters());
        ///

        gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));

        //itemTouchHelper.attachToRecyclerView(recyclerView);

        mealItemData = new ArrayList<>();
        mealItemAdapter = new MealItemAdapter(this, mealItemData);

        //////
        mealItemAdapter.setOnItemListenerListener(new MealItemAdapter.OnItemListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                /*Snackbar.make(view, "setOnItemListenerListener", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                int pos = recyclerView.getChildLayoutPosition(view);
                Intent intent = new Intent(view.getContext(), MealItemActivity.class);
                intent.putExtra("selected_items", mealItemData.get(pos));
                startActivity(intent);
            }

            @Override
            public void OnItemLongClickListener(View view, int position) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext());
                alertBuilder.setTitle("Confirm Item deletion");
                alertBuilder.setMessage("REMOVE?");

                alertBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int location = recyclerView.getChildLayoutPosition(view);
                        mealItemData.remove(location);
                        mealItemAdapter.notifyItemRemoved(location);
                        mealItemImages.recycle();
                    }
                });

                alertBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertBuilder.create().show();
            }
        });
        //////

        recyclerView.setAdapter(mealItemAdapter);

        // load meal item data
        loadMealItemData();

        // Check for specific Receiver Change
        checkFoodieReceiverChange();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddItemActivity = new Intent(view.getContext(), AddItemActivity.class);
                startActivityForResult(intentAddItemActivity, MEAL_ITEM_REQUEST);
            }
        });
    }

    private void loadMealItemData() {
        mealItemData.clear();

        mealItemTitles = getResources().getStringArray(R.array.meal_item_titles);
        mealItemInfo = getResources().getStringArray(R.array.meal_item_info);
        mealItemImages = getResources().obtainTypedArray(R.array.meal_item_images);

        for (int i = 0; i < mealItemTitles.length; i++) {
            mealItemData.add(new MealItem(mealItemTitles[i], mealItemInfo[i], mealItemImages.getResourceId(i, 0)));
        }

        mealItemAdapter.notifyDataSetChanged();
        mealItemImages.recycle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Log.d(TAG, "Inside onActivityResult method");

        if (resultCode == RESULT_OK && requestCode == MEAL_ITEM_REQUEST) {
            String foodChosen = data.getStringExtra(FoodChooserActivity.KEY_FOOD_CHOICE);
            String foodDescription = data.getStringExtra(AddItemActivity.KEY_FOOD_DESCRIPTION);
            String imagesID = data.getStringExtra(FoodChooserActivity.KEY_FOOD_IMAGE_ID);
            //String res = String.valueOf(R.drawable.chalupasupreme);
            int i = Integer.parseInt(imagesID);

            mealItemImages = getResources().obtainTypedArray(R.array.meal_item_images);
            mealItemData.add(new MealItem(foodChosen, foodDescription, i));
            mealItemAdapter.notifyDataSetChanged();
            mealItemImages.recycle();
        } else {
            Log.d("TAG", "Problem");
        }
        //Log.d(TAG, "End of onActivityResult method");
    }

    protected IntentFilter addIntentFilters() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CUSTOM_BROADCAST);
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
        intentFilter.addAction(Intent.ACTION_BATTERY_OKAY);
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        return intentFilter;
    }

    private void checkFoodieReceiverChange() {
        foodieReceiver.setOnIntegerChangeListener(new OnIntegerChangeListener() {
            @Override
            public void onIntegerChanged(int newValue) {
                if (foodieReceiver.getKeyFoodieRec() == 1) {
                    Random rnd = new Random();
                    int pos = rnd.nextInt(6);
                    Intent intent = new Intent(recyclerView.getContext(),MealItemActivity.class);
                    intent.putExtra("random_selected", mealItemData.get(pos));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "inside onDestroy will unregister Receiver now");

        this.unregisterReceiver(foodieReceiver);//when not using LocalBroadcastManager

        // when using LocalBroadcastManager
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(foodieReceiver);

        super.onDestroy();
    }
} // End of Main Activity








// Goes above onCreate
/*
    ItemTouchHelper itemTouchHelper; // for drag and swipe
    private int dragDirections = itemTouchHelper.LEFT | itemTouchHelper.RIGHT | itemTouchHelper.UP | itemTouchHelper.DOWN;
    private int swipeDirections = itemTouchHelper.LEFT | itemTouchHelper.RIGHT;
    */

// Goes below gridColumnCount in the onCreate
/*
        gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        if (gridColumnCount > 1) {
            swipeDirections = 0;
        } // don't wont to swipe when there are multiple columns
        */

        /*
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(dragDirections,swipeDirections) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(mealItemData, from, to);
                mealItemAdapter.notifyItemMoved(from,to);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mealItemData.remove(viewHolder.getAdapterPosition()); // whichever item it was that was swiped on is the ot that will get removed
                mealItemAdapter.notifyItemRemoved(viewHolder.getAdapterPosition()); // notify that the item was removed then pass in the position from which it was removed
            }
        });
        */