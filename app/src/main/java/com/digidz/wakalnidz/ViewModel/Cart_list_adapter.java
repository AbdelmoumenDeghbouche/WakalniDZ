package com.digidz.wakalnidz.ViewModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidz.wakalnidz.Model.FoodModel;
import com.digidz.wakalnidz.R;

import java.util.ArrayList;

public class Cart_list_adapter extends RecyclerView.Adapter<Cart_list_adapter.ViewHolder> {
    private ArrayList<FoodModel> cartFoodModelArrayList = new ArrayList<>();
    private Context context;

    public Cart_list_adapter(ArrayList<FoodModel> cartFoodModelArrayList) {
        this.cartFoodModelArrayList = cartFoodModelArrayList;
    }

    public Cart_list_adapter(ArrayList<FoodModel> cartFoodModelArrayList, Context context) {
        this.cartFoodModelArrayList = cartFoodModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cart_checkout_card_view, parent, false);
        return new Cart_list_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return cartFoodModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_title_of_food_in_cart_activity, txt_price_of_single_item_in_cart, txt_price_of_multiple_item_in_cart, txt_add_quantity_of_food_in_cart, txt_minus_quantity_of_food_in_cart, txt_quantity_of_food_in_cart;
        private ImageView img_of_food_in_cart_act;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_add_quantity_of_food_in_cart = itemView.findViewById(com.digidz.wakalnidz.R.id.txt_add_quantity_of_food_in_cart);
            txt_minus_quantity_of_food_in_cart = itemView.findViewById(R.id.txt_minus_quantity_of_food);
            txt_price_of_multiple_item_in_cart = itemView.findViewById(R.id.txt_price_of_multiple_item_in_cart);
            txt_price_of_single_item_in_cart = itemView.findViewById(R.id.txt_price_of_single_item_in_cart);
            txt_title_of_food_in_cart_activity = itemView.findViewById(R.id.txt_title_of_food_in_cart_activity);
            txt_quantity_of_food_in_cart = itemView.findViewById(R.id.txt_quantity_of_food_in_cart);
            img_of_food_in_cart_act = itemView.findViewById(R.id.img_of_food_in_cart_act);

        }
    }
}
