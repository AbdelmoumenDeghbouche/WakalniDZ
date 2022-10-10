package com.digidz.wakalnidz.Model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Cart_food_Model implements Parcelable {


    public static final Creator<Cart_food_Model> CREATOR = new Creator<Cart_food_Model>() {
        @Override
        public Cart_food_Model createFromParcel(Parcel in) {
            return new Cart_food_Model(in);
        }

        @Override
        public Cart_food_Model[] newArray(int size) {
            return new Cart_food_Model[size];
        }
    };
    private String food_name_in_cart_act;
    private String price_of_multiple_items_food_in_cart_act;
    private String quantity_of_food;
    private String price_of_single_item_food_in_cart_act;
    private String number_of_drawable_photo;
    private Drawable img_of_food_category;


    public Cart_food_Model(String quantity_of_food, String food_name_in_cart_act, String price_of_multiple_items_food_in_cart_act, String price_of_single_item_food_in_cart_act, String number_of_drawable_photo) {
        this.food_name_in_cart_act = food_name_in_cart_act;
        this.quantity_of_food = quantity_of_food;

        this.price_of_multiple_items_food_in_cart_act = price_of_multiple_items_food_in_cart_act;
        this.price_of_single_item_food_in_cart_act = price_of_single_item_food_in_cart_act;
        this.number_of_drawable_photo = number_of_drawable_photo;
    }

    protected Cart_food_Model(Parcel in) {
        food_name_in_cart_act = in.readString();
        price_of_multiple_items_food_in_cart_act = in.readString();
        quantity_of_food = in.readString();
        price_of_single_item_food_in_cart_act = in.readString();
        number_of_drawable_photo = in.readString();
    }

    public String getQuantity_of_food() {
        return quantity_of_food;
    }

    public void setQuantity_of_food(String quantity_of_food) {
        this.quantity_of_food = quantity_of_food;
    }

    public String getFood_name_in_cart_act() {
        return food_name_in_cart_act;
    }

    public void setFood_name_in_cart_act(String food_name_in_cart_act) {
        this.food_name_in_cart_act = food_name_in_cart_act;
    }

    public String getPrice_of_multiple_items_food_in_cart_act() {
        return price_of_multiple_items_food_in_cart_act;
    }

    public void setPrice_of_multiple_items_food_in_cart_act(String price_of_multiple_items_food_in_cart_act) {
        this.price_of_multiple_items_food_in_cart_act = price_of_multiple_items_food_in_cart_act;
    }

    public String getNumber_of_drawable_photo() {
        return number_of_drawable_photo;
    }

    public void setNumber_of_drawable_photo(String number_of_drawable_photo) {
        this.number_of_drawable_photo = number_of_drawable_photo;
    }

    public String getPrice_of_single_item_food_in_cart_act() {
        return price_of_single_item_food_in_cart_act;
    }

    public void setPrice_of_single_item_food_in_cart_act(String price_of_single_item_food_in_cart_act) {
        this.price_of_single_item_food_in_cart_act = price_of_single_item_food_in_cart_act;
    }

    public Drawable getImg_of_food_category() {
        return img_of_food_category;
    }

    public void setImg_of_food_category(Drawable img_of_food_category) {
        this.img_of_food_category = img_of_food_category;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(food_name_in_cart_act);
        parcel.writeString(price_of_multiple_items_food_in_cart_act);
        parcel.writeString(price_of_single_item_food_in_cart_act);
        parcel.writeString(number_of_drawable_photo);
        parcel.writeString(quantity_of_food);
    }

}
