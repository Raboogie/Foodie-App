package com.example.foodieapp;

import android.os.Parcel;
import android.os.Parcelable;

public class MealItem implements Parcelable {
    private String title;
    private String info;
    private int imageResource;

    MealItem(String title, String info) {
        this.title = title;
        this.info = info;
    }

    MealItem(String title, String info, int imageId) {
        this.title = title;
        this.info = info;
        this.imageResource = imageId;
    }


    protected MealItem(Parcel in) {
        title = in.readString();
        info = in.readString();
        imageResource = in.readInt();
    }

    public static final Creator<MealItem> CREATOR = new Creator<MealItem>() {
        @Override
        public MealItem createFromParcel(Parcel in) {
            return new MealItem(in);
        }

        @Override
        public MealItem[] newArray(int size) {
            return new MealItem[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public int getImageId() {
        return imageResource;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(info);
        dest.writeInt(imageResource);
    }
}
