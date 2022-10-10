package com.digidz.wakalnidz.Model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class FoodModel implements Parcelable {
    public static final Creator<FoodModel> CREATOR = new Creator<FoodModel>() {
        @Override
        public FoodModel createFromParcel(Parcel in) {
            return new FoodModel(in);
        }

        @Override
        public FoodModel[] newArray(int size) {
            return new FoodModel[size];
        }
    };
    private String foodSpecialName;
    private String imageView;
    private String food_category;
    private Drawable img_of_food_category;
    private String category_name;
    private String price;
    private int bckg_color;

    public FoodModel(String food_category, String foodSpecialName, String imageView, String price, int bckg_color) {
        this.foodSpecialName = foodSpecialName;
        this.imageView = imageView;
        this.price = price;
        this.bckg_color = bckg_color;
        this.food_category = food_category;

    }

    protected FoodModel(Parcel in) {
        foodSpecialName = in.readString();
        category_name = in.readString();
        price = in.readString();
        bckg_color = in.readInt();
    }


    public FoodModel(Drawable img_of_food_category, String category_name, int bckg_color) {
        this.img_of_food_category = img_of_food_category;
        this.category_name = category_name;
        this.bckg_color = bckg_color;
    }

    public String getFood_category() {
        return food_category;
    }

    public void setFood_category(String food_category) {
        this.food_category = food_category;
    }

    public Drawable getImg_of_food_category() {
        return img_of_food_category;
    }

    public void setImg_of_food_category(Drawable img_of_food_category) {
        this.img_of_food_category = img_of_food_category;
    }

    public int getBckg_color() {
        return bckg_color;
    }

    public void setBckg_color(int bckg_color) {
        this.bckg_color = bckg_color;
    }

    public String getFoodSpecialName() {
        return foodSpecialName;
    }

    public void setFoodSpecialName(String foodSpecialName) {
        this.foodSpecialName = foodSpecialName;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(foodSpecialName);
        parcel.writeString(category_name);
        parcel.writeString(price);
        parcel.writeInt(bckg_color);
    }
}

