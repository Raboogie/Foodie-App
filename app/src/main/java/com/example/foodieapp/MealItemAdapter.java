package com.example.foodieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MealItemAdapter extends RecyclerView.Adapter<MealItemAdapter.ViewHolder> {
    private ArrayList<MealItem> mealItemsData;
    private Context context;
    private OnItemListener onItemListener;

    public MealItemAdapter(Context context, ArrayList<MealItem> mealItemArrayList) {
        this.context = context;
        mealItemsData = mealItemArrayList;
        
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false));
        View v = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(viewHolder);
        v.setOnLongClickListener(viewHolder);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealItem currMealItem = mealItemsData.get(position);
        holder.bindItem(currMealItem);
    }

    @Override
    public int getItemCount() {
        return mealItemsData.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView textTitle, textInfo;
        private ImageView imageViewMealItem;

        // constructor takes in a View
        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.title);
            textInfo = itemView.findViewById(R.id.subTitle);
            imageViewMealItem = itemView.findViewById(R.id.imageViewMealItem);
        }

        public void bindItem(MealItem currentMealItem) {
            textTitle.setText(currentMealItem.getTitle());
            textInfo.setText(currentMealItem.getInfo());
            Glide.with(context).load(currentMealItem.getImageId()).into(imageViewMealItem);
        }

        @Override
        public void onClick(View v) {
            //MealItemAdapter.OnItemListener onItemListener = null;

            if (onItemListener != null){
                onItemListener.OnItemClickListener(v, v.getId());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            //MealItemAdapter.OnItemListener onItemListener = null;

            if (onItemListener != null){
                onItemListener.OnItemLongClickListener(v,v.getId());
            }
            return true;
        }
    }// end of ViewHolder class

    public interface OnItemListener {
        void OnItemClickListener(View view, int position);
        void OnItemLongClickListener(View view, int position);
    }

    public void setOnItemListenerListener(OnItemListener listener){
        this.onItemListener = listener;
    }
}