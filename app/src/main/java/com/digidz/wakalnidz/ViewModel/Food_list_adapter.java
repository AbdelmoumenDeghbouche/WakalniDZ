package com.digidz.wakalnidz.ViewModel;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.digidz.wakalnidz.Activities.DetailsActivity;
import com.digidz.wakalnidz.Model.Cart_food_Model;
import com.digidz.wakalnidz.Model.FoodModel;
import com.digidz.wakalnidz.R;
import com.digidz.wakalnidz.Repositories.Utils;
import com.digidz.wakalnidz.View.MainFragment;

import java.util.ArrayList;

public class Food_list_adapter extends RecyclerView.Adapter<Food_list_adapter.ViewHolder> {
    private ArrayList<FoodModel> foodModelArrayList = new ArrayList<>();
    private Context context;


    public Food_list_adapter(ArrayList<FoodModel> foodModelArrayList, Context context) {
        this.foodModelArrayList = foodModelArrayList;
        this.context = context;
    }

    public void setFoodModelArrayList(ArrayList<FoodModel> foodModelArrayList) {
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
        //TODO: fix when u get back from cart fragment if food model already exist in cart u need to let the add text view in main activity as added not add +

        if (foodModelArrayList.get(position).getFoodSpecialName() == null) {
            holder.txt_price_of_food.setVisibility(View.GONE);
            holder.txt_food_special_name.setVisibility(View.GONE);
            holder.rel_add_to_cart.setVisibility(View.GONE);
            holder.img_of_food.setVisibility(View.GONE);
            holder.img_of_food_category.setVisibility(View.VISIBLE);

        } else {
            holder.txt_category_of_food.setVisibility(View.GONE);
            holder.txt_dollar.setVisibility(View.VISIBLE);
        }

        holder.txt_food_special_name.setText(foodModelArrayList.get(position).getFoodSpecialName());
        holder.txt_category_of_food.setText(foodModelArrayList.get(position).getCategory_name());
        holder.txt_price_of_food.setText(foodModelArrayList.get(position).getPrice());
        holder.card_view_food.setBackgroundResource(foodModelArrayList.get(position).getBckg_color());
        holder.img_of_food.setImageDrawable(foodModelArrayList.get(position).getImageView());
        holder.img_of_food_category.setImageDrawable(foodModelArrayList.get(position).getImg_of_food_category());
        holder.img_of_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("FoodModel", foodModelArrayList.get(position));
                intent.putExtra("image", Utils.keyDrawableHashMap.get(String.valueOf(position + 1)));
                Log.d(TAG, "onClick: " + foodModelArrayList.get(position).getImageView().toString());
                context.startActivity(intent);

            }
        });
        holder.txt_view_added_to_cart_from_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.Cart_foods_list.remove(position);
                animationToRight();
            }

            private void animationToRight() {
                Slide slide = new Slide();
                slide.setSlideEdge(Gravity.END);
                slide.setDuration(500);
                TransitionManager.beginDelayedTransition(holder.rel_add_to_cart, slide);
                holder.txt_view_added_to_cart_from_main.setVisibility(View.GONE);
                holder.txt_view_add_to_cart_from_main.setVisibility(View.VISIBLE);
            }
        });
        holder.txt_view_add_to_cart_from_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.Cart_foods_list.add(new Cart_food_Model("1", foodModelArrayList.get(position).getFoodSpecialName(), foodModelArrayList.get(position).getPrice(), foodModelArrayList.get(position).getPrice(), Utils.keyDrawableHashMap.get(String.valueOf(position + 1))));
                animationToLeft();

            }


            private void animationToLeft() {
                Slide slide = new Slide();
                slide.setSlideEdge(Gravity.START);
                slide.setDuration(500);
                TransitionManager.beginDelayedTransition(holder.rel_add_to_cart, slide);
                holder.txt_view_add_to_cart_from_main.setVisibility(View.GONE);
                holder.txt_view_added_to_cart_from_main.setVisibility(View.VISIBLE);

            }

        });
        holder.img_of_food_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Food_list_adapter food_list_adapter = new Food_list_adapter(foodModelList(foodModelArrayList.get(position).getCategory_name().toString().trim().toLowerCase(), MainFragment.foods_list), context);
                MainFragment.rv_food_with_add_to_cart.setAdapter(food_list_adapter);
            }
        });
    }

    private ArrayList<FoodModel> foodModelList(String category, ArrayList<FoodModel> foodModelArrayList1) {
        ArrayList<FoodModel> arrayList = new ArrayList<>();
        for (FoodModel foodModel : foodModelArrayList1) {
            if (foodModel.getFood_category().toString().trim().toLowerCase().equals(category)) {
                arrayList.add(foodModel);
            }
        }
        return arrayList;
    }


    @Override
    public int getItemCount() {
        return foodModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_view_added_to_cart_from_main, txt_food_special_name, txt_category_of_food, txt_price_of_food, txt_dollar, txt_view_add_to_cart_from_main;
        private ImageView img_of_food, img_of_food_category;
        private CardView card_view_food;
        private RelativeLayout rel_add_to_cart, rel_full_food_cart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_food_special_name = itemView.findViewById(R.id.txt_food_special_name);
            txt_category_of_food = itemView.findViewById(R.id.txt_category_of_food);
            txt_price_of_food = itemView.findViewById(R.id.txt_price_of_food);
            img_of_food = itemView.findViewById(R.id.img_of_food);
            txt_view_added_to_cart_from_main = itemView.findViewById(R.id.txt_view_added_to_cart_from_main);
            rel_add_to_cart = itemView.findViewById(R.id.rel_add_to_cart);
            card_view_food = itemView.findViewById(R.id.card_view_food);
            txt_dollar = itemView.findViewById(R.id.txt_dollar);
            txt_view_add_to_cart_from_main = itemView.findViewById(R.id.txt_view_add_to_cart_from_main);
            img_of_food_category = itemView.findViewById(R.id.img_of_food_category);
        }
    }
}
