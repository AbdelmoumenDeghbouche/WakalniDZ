package com.digidz.wakalnidz.Model;

import android.graphics.drawable.Drawable;
import android.widget.RelativeLayout;

public class FoodModel {
    private String foodSpecialName;
    private Drawable imageView;
    private String category_name;
    private String price;
    private int bckg_color;
    private RelativeLayout rel_btn_add_to_cart;

    public FoodModel(String foodSpecialName, Drawable imageView, String price, RelativeLayout rel_btn_add_to_cart, int bckg_color) {
        this.foodSpecialName = foodSpecialName;
        this.imageView = imageView;
        this.price = price;
        this.rel_btn_add_to_cart = rel_btn_add_to_cart;
        this.bckg_color = bckg_color;
    }

    public FoodModel(Drawable imageView, String category_name, int bckg_color) {
        this.imageView = imageView;
        this.category_name = category_name;
        this.bckg_color = bckg_color;
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

    public Drawable getImageView() {
        return imageView;
    }

    public void setImageView(Drawable imageView) {
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

    public RelativeLayout getRel_btn_add_to_cart() {
        return rel_btn_add_to_cart;
    }

    public void setRel_btn_add_to_cart(RelativeLayout rel_btn_add_to_cart) {
        this.rel_btn_add_to_cart = rel_btn_add_to_cart;
    }
}
