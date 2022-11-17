package com.digidz.wakalnidz.ViewModel;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidz.wakalnidz.Model.Cart_food_Model;
import com.digidz.wakalnidz.R;
import com.digidz.wakalnidz.Repositories.Utils;
import com.digidz.wakalnidz.View.CartFragment;

import java.util.ArrayList;

public class Cart_list_adapter extends RecyclerView.Adapter<Cart_list_adapter.ViewHolder> {
    private final Context context;
    private ArrayList<Cart_food_Model> cartFoodModelArrayList = new ArrayList<>();
    private int cost, service_cost, total_quantity;

    public Cart_list_adapter(ArrayList<Cart_food_Model> cartFoodModelArrayList, Context context) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.itemView.getContext())
                .load(holder.itemView.getContext().getResources().getIdentifier
                        (cartFoodModelArrayList.get(position).
                                getNumber_of_drawable_photo().toString(), "drawable", holder.itemView.getContext().getPackageName()))
                .into(holder.img_of_food_in_cart_act);
        holder.txt_title_of_food_in_cart_activity.setText(cartFoodModelArrayList.get(position).getFood_name_in_cart_act().trim());
        holder.txt_price_of_single_item_in_cart.setText(cartFoodModelArrayList.get(position).getPrice_of_single_item_food_in_cart_act());
        holder.txt_add_quantity_of_food_in_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int d = Integer.parseInt(holder.txt_quantity_of_food_in_cart.getText().toString());
                Log.d(TAG, "first quantity: " + d);
                d = d + 1;
                Log.d(TAG, "first quantity after hitting plus: " + d);

                cartFoodModelArrayList.get(position).setQuantity_of_food(String.valueOf(d));
                holder.txt_quantity_of_food_in_cart.setText(cartFoodModelArrayList.get(position).getQuantity_of_food());
                notifyDataSetChanged();
                holder.txt_minus_quantity_of_food_in_cart.setClickable(true);
                Utils.price_of_multiple_items = d;
                notifyDataSetChanged();

            }
        });
        holder.txt_minus_quantity_of_food_in_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int d = Integer.parseInt(cartFoodModelArrayList.get(position).getQuantity_of_food());
                d = d - 1;
                cartFoodModelArrayList.get(position).setQuantity_of_food(String.valueOf(d));
                holder.txt_quantity_of_food_in_cart.setText(cartFoodModelArrayList.get(position).getQuantity_of_food());
                notifyDataSetChanged();
                Utils.price_of_multiple_items = d;
                CartFragment.txt_items_total_cost.setText(cost + " DZD");
                notifyDataSetChanged();

                if (cartFoodModelArrayList.get(position).getQuantity_of_food().equals("0")) {
                    Log.d(TAG, "onClick:garented quantity below 1 :" + cartFoodModelArrayList.get(position).getQuantity_of_food());
                    holder.txt_minus_quantity_of_food_in_cart.setClickable(false);
                    cartFoodModelArrayList.get(position).setQuantity_of_food("1");
                    holder.txt_quantity_of_food_in_cart.setText(cartFoodModelArrayList.get(position).getQuantity_of_food());
                    cartFoodModelArrayList.get(position).setPrice_of_multiple_items_food_in_cart_act(cartFoodModelArrayList.get(position).getPrice_of_single_item_food_in_cart_act());

                    holder.txt_price_of_multiple_item_in_cart.setText(cartFoodModelArrayList.get(position).getPrice_of_single_item_food_in_cart_act());
                    notifyDataSetChanged();

                }

            }
        });
        cost = 0;
        total_quantity = 0;

        treatPriceOfFoodLogic(holder.txt_price_of_single_item_in_cart, holder.txt_price_of_multiple_item_in_cart, position, holder.txt_price_of_multiple_item_in_cart, holder.txt_quantity_of_food_in_cart);
        for (Cart_food_Model cartFoodModel : cartFoodModelArrayList) {
            cost = cost + (Integer.parseInt(cartFoodModel.getPrice_of_multiple_items_food_in_cart_act()));
            total_quantity = total_quantity + (Integer.parseInt(cartFoodModel.getQuantity_of_food()));
        }
        CartFragment.txt_items_total_cost.setText(cost + " DZD");
        treatPriceOfDeliveryServiceLogic();
        holder.txt_quantity_of_food_in_cart.setText(cartFoodModelArrayList.get(position).getQuantity_of_food());

    }

    private void treatPriceOfDeliveryServiceLogic() {
        if (total_quantity >= 10) {
            service_cost = 100;
            CartFragment.txt_delivery_service_cost.setText(service_cost + " DZD");

        } else {
            service_cost = 50;
            CartFragment.txt_delivery_service_cost.setText(service_cost + " DZD");

        }
        CartFragment.txt_total_cost.setText(cost + service_cost + " DZD");

    }


    private void treatPriceOfFoodLogic(TextView txt_price_of_single_item_in_cart, TextView txt_price_of_multiple_item_in_cart, int position, TextView price_of_quantity_of_food, TextView quantity_of_food) {

        txt_price_of_single_item_in_cart.setText(cartFoodModelArrayList.get(position).getPrice_of_single_item_food_in_cart_act());

        int i = Integer.parseInt(txt_price_of_single_item_in_cart.getText().toString());
        int d;
        if (cartFoodModelArrayList.get(position).getQuantity_of_food() == null || cartFoodModelArrayList.get(position).getQuantity_of_food().equals("0")) {
            d = 1;
        } else {
            d = Integer.parseInt(cartFoodModelArrayList.get(position).getQuantity_of_food());


        }
        price_of_quantity_of_food.setText(String.valueOf(i * d));
        cartFoodModelArrayList.get(position).setPrice_of_multiple_items_food_in_cart_act(String.valueOf(i * d));


    }


    @Override
    public int getItemCount() {
        return cartFoodModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txt_title_of_food_in_cart_activity;
        private final TextView txt_price_of_single_item_in_cart;
        private final TextView txt_price_of_multiple_item_in_cart;
        private final TextView txt_add_quantity_of_food_in_cart;
        private final TextView txt_minus_quantity_of_food_in_cart;
        private final TextView txt_quantity_of_food_in_cart;
        private final ImageView img_of_food_in_cart_act;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_add_quantity_of_food_in_cart = itemView.findViewById(com.digidz.wakalnidz.R.id.txt_add_quantity_of_food_in_cart);
            txt_minus_quantity_of_food_in_cart = itemView.findViewById(R.id.txt_minus_quantity_of_food_in_cart);
            txt_price_of_multiple_item_in_cart = itemView.findViewById(R.id.txt_price_of_multiple_item_in_cart);
            txt_price_of_single_item_in_cart = itemView.findViewById(R.id.txt_price_of_single_item_in_cart);
            txt_title_of_food_in_cart_activity = itemView.findViewById(R.id.txt_title_of_food_in_cart_activity);
            txt_quantity_of_food_in_cart = itemView.findViewById(R.id.txt_quantity_of_food_in_cart);
            img_of_food_in_cart_act = itemView.findViewById(R.id.img_of_food_in_cart_act);

        }
    }
}
