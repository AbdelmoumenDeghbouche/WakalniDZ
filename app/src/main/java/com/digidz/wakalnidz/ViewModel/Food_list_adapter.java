package com.digidz.wakalnidz.ViewModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.digidz.wakalnidz.Model.FoodModel;
import com.digidz.wakalnidz.R;

import java.util.ArrayList;

public class Food_list_adapter extends RecyclerView.Adapter<Food_list_adapter.ViewHolder> {
    private ArrayList<FoodModel> foodModelArrayList = new ArrayList<>();
    private Context context;

    public Food_list_adapter(Context context) {
        this.context = context;
    }


    public Food_list_adapter(ArrayList<FoodModel> foodModelArrayList) {
        this.foodModelArrayList = foodModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.food_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (foodModelArrayList.get(position).getFoodSpecialName() == null) {
            holder.txt_price_of_food.setVisibility(View.GONE);
            holder.txt_food_special_name.setVisibility(View.GONE);
            holder.rel_add_to_cart.setVisibility(View.GONE);
        }
        holder.txt_food_special_name.setText(foodModelArrayList.get(position).getFoodSpecialName());
        holder.txt_category_of_food.setText(foodModelArrayList.get(position).getCategory_name());
        holder.txt_price_of_food.setText(foodModelArrayList.get(position).getPrice());
        holder.card_view_food.setBackgroundResource(foodModelArrayList.get(position).getBckg_color());
        holder.img_of_food.setImageDrawable(foodModelArrayList.get(position).getImageView());
        if (foodModelArrayList.get(position).getFoodSpecialName() != null) {
            holder.rel_full_food_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context.getApplicationContext(), foodModelArrayList.get(position).getFoodSpecialName() + " Added ", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            holder.rel_full_food_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context.getApplicationContext(), foodModelArrayList.get(position).getCategory_name(), Toast.LENGTH_SHORT).show();
                }
            });

        }


    }


    @Override
    public int getItemCount() {
        return foodModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_food_special_name, txt_category_of_food, txt_price_of_food;
        private ImageView img_of_food;
        private CardView card_view_food;
        private RelativeLayout rel_add_to_cart, rel_full_food_cart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_food_special_name = itemView.findViewById(R.id.txt_food_special_name);
            txt_category_of_food = itemView.findViewById(R.id.txt_category_of_food);
            txt_price_of_food = itemView.findViewById(R.id.txt_price_of_food);
            img_of_food = itemView.findViewById(R.id.img_of_food);
            rel_add_to_cart = itemView.findViewById(R.id.rel_add_to_cart);
            rel_full_food_cart = itemView.findViewById(R.id.rel_full_food_cart);
            card_view_food = itemView.findViewById(R.id.card_view_food);
        }
    }
}
