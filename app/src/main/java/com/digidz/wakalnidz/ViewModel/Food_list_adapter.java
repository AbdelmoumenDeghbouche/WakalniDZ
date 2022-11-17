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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidz.wakalnidz.Activities.DetailsActivity;
import com.digidz.wakalnidz.Model.Cart_food_Model;
import com.digidz.wakalnidz.Model.FoodModel;
import com.digidz.wakalnidz.R;
import com.digidz.wakalnidz.View.MainFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Food_list_adapter extends RecyclerView.Adapter<Food_list_adapter.ViewHolder> {
    private final Context context;
    ArrayList<Cart_food_Model> cartFoodListFromDB = new ArrayList<>();
    ArrayList<Cart_food_Model> tmp_cart_array_list = new ArrayList<>();
    private ArrayList<FoodModel> foodModelArrayList = new ArrayList<>();
    private FirebaseDatabase rootNode;
    private DatabaseReference cartElementsReference;


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
        treatViewsOfTheRV(holder, position);
        doNotDuplicateFoodInCartAndGetCartListFromDB(position, holder.txt_view_add_to_cart_from_main, holder.txt_view_added_to_cart_from_main);


    }

    private void treatViewsOfTheRV(ViewHolder holder, int position) {

        if (foodModelArrayList.get(position).getFoodSpecialName() == null) {
            holder.txt_price_of_food.setVisibility(View.GONE);
            holder.txt_food_special_name.setVisibility(View.GONE);
            holder.rel_add_to_cart.setVisibility(View.GONE);
            holder.img_of_food.setVisibility(View.GONE);
            holder.img_of_food_category.setVisibility(View.VISIBLE);

        } else {
            holder.txt_category_of_food.setVisibility(View.GONE);
            holder.txt_dollar.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView.getContext())
                    .load(holder.itemView.getContext().getResources().getIdentifier(foodModelArrayList.get(position).getImageView(), "drawable", holder.itemView.getContext().getPackageName()))
                    .into(holder.img_of_food);
        }
        holder.txt_view_added_to_cart_from_main.setClickable(false);
        holder.txt_food_special_name.setText(foodModelArrayList.get(position).getFoodSpecialName());
        holder.txt_category_of_food.setText(foodModelArrayList.get(position).getCategory_name());
        holder.txt_price_of_food.setText(foodModelArrayList.get(position).getPrice());
        holder.card_view_food.setBackgroundResource(foodModelArrayList.get(position).getBckg_color());
        holder.img_of_food_category.setImageDrawable(foodModelArrayList.get(position).getImg_of_food_category());
        holder.img_of_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);

                intent.putExtra("FoodModel", foodModelArrayList.get(position));
                int ResourceID = holder.itemView.getContext().getResources().getIdentifier(foodModelArrayList.get(position).getImageView(), "drawable", holder.itemView.getContext().getPackageName());
                intent.putExtra("image", ResourceID);
                intent.putExtra("imgName",foodModelArrayList.get(position).getImageView());
                Log.d(TAG, "onClick: " + foodModelArrayList.get(position).getImageView());
                context.startActivity(intent);

            }
        });
        holder.txt_view_added_to_cart_from_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (holder.txt_view_added_to_cart_from_main.getVisibility() == View.VISIBLE && holder.txt_view_add_to_cart_from_main.getVisibility() == View.GONE) {
                    deleteFoodFromCartListInFirebase(foodModelArrayList.get(position));
                    animationToRight();
                    holder.txt_view_added_to_cart_from_main.setVisibility(View.GONE);
                }

            }

            private void animationToRight() {
                Slide slide = new Slide();
                slide.setSlideEdge(Gravity.END);
                slide.setDuration(500);
                TransitionManager.beginDelayedTransition(holder.rel_add_to_cart, slide);
                holder.txt_view_added_to_cart_from_main.setVisibility(View.GONE);
                holder.txt_view_add_to_cart_from_main.setVisibility(View.VISIBLE);
                holder.txt_view_added_to_cart_from_main.setClickable(false);
                holder.txt_view_add_to_cart_from_main.setClickable(true);

            }

        });
        holder.txt_view_add_to_cart_from_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmp_cart_array_list = cartFoodListFromDB;

                Cart_food_Model cartFoodModel = new Cart_food_Model("1", foodModelArrayList.get(position).getFoodSpecialName(), foodModelArrayList.get(position).getPrice(), foodModelArrayList.get(position).getPrice(), foodModelArrayList.get(position).getImageView());
                tmp_cart_array_list.add(cartFoodModel);
                saveCartElementsToFirebaseDatabase(tmp_cart_array_list);
                animationToLeft();

            }

            private void animationToLeft() {
                Slide slide = new Slide();
                slide.setSlideEdge(Gravity.START);
                slide.setDuration(500);
                TransitionManager.beginDelayedTransition(holder.rel_add_to_cart, slide);
                holder.txt_view_add_to_cart_from_main.setVisibility(View.GONE);
                holder.txt_view_added_to_cart_from_main.setVisibility(View.VISIBLE);
                holder.txt_view_added_to_cart_from_main.setClickable(true);
                holder.txt_view_add_to_cart_from_main.setClickable(false);

            }

        });
        holder.img_of_food_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Food_list_adapter food_list_adapter = new Food_list_adapter(foodModelList(foodModelArrayList.get(position).getCategory_name().trim().toLowerCase(), MainFragment.foods_list), context);
                MainFragment.rv_food_with_add_to_cart.setAdapter(food_list_adapter);
            }
        });

    }


    private void deleteFoodFromCartListInFirebase(FoodModel FoodModelWantedTobeDeleted) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            FirebaseDatabase.getInstance().getReference().child("cart").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Cart_food_Model cartFoodModel = dataSnapshot.getValue(Cart_food_Model.class);
                            if (cartFoodModel.getFood_name_in_cart_act() != null && FoodModelWantedTobeDeleted.getFoodSpecialName() != null) {
                                Log.d(TAG, "foodModels not null: ");
                                if (cartFoodModel.getFood_name_in_cart_act().trim().toLowerCase().equals(FoodModelWantedTobeDeleted.getFoodSpecialName().trim().toLowerCase())) {
                                    FirebaseDatabase.getInstance().getReference().child("cart").child(currentUser.getUid()).child(dataSnapshot.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.d(TAG, "removed:ddss ");
                                            Toast.makeText(context, "Food Deleted from Cart", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    private void doNotDuplicateFoodInCartAndGetCartListFromDB(int position, TextView txt_view_add_to_cart_from_main, TextView txt_view_added_to_cart_from_main) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            cartFoodListFromDB.clear();

            FirebaseDatabase.getInstance().getReference().child("cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        cartFoodListFromDB = new ArrayList<>();
                        for (DataSnapshot dss : snapshot.getChildren()) {
                            Cart_food_Model foodModel = dss.getValue(Cart_food_Model.class);
                            cartFoodListFromDB.add(foodModel);

                            if (foodModel != null) {

                                if (IsThisArraylistContainAnObjectThatHasFields(foodModelArrayList.get(position), foodModel.getFood_name_in_cart_act().trim().toLowerCase(), foodModel.getPrice_of_single_item_food_in_cart_act().trim().toLowerCase())) {
                                    txt_view_add_to_cart_from_main.setVisibility(View.GONE);
                                    txt_view_added_to_cart_from_main.setVisibility(View.VISIBLE);
                                    txt_view_add_to_cart_from_main.setClickable(false);
                                    txt_view_added_to_cart_from_main.setClickable(true);

                                }

                            }


                        }

                    }


                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    private boolean IsThisArraylistContainAnObjectThatHasFields(FoodModel aFoodModel, String toLowerCase, String toLowerCase1) {
        if (aFoodModel.getFoodSpecialName() != null) {
            return aFoodModel.getFoodSpecialName().trim().toLowerCase().equals(toLowerCase) &&
                    aFoodModel.getPrice().trim().toLowerCase().equals(toLowerCase1);
        }

        return false;

    }

    //    private void handleImgLogicByGlide(ImageView img_of_food, int position, Context context) {
//        Glide.with(context)
//                .load(foodModelArrayList.get(position).getImageView())
//                .into(img_of_food);
//    }
    private void saveCartElementsToFirebaseDatabase(ArrayList<Cart_food_Model> cartFoodModelArrayList) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        rootNode = FirebaseDatabase.getInstance();
        cartElementsReference = rootNode.getReference();
        if (firebaseUser != null) {
            cartElementsReference.child("cart").child(firebaseUser.getUid()).setValue(cartFoodModelArrayList);

        }


    }

    private ArrayList<FoodModel> foodModelList(String category, ArrayList<FoodModel> foodModelArrayList1) {
        ArrayList<FoodModel> arrayList = new ArrayList<>();
        for (FoodModel foodModel : foodModelArrayList1) {
            if (foodModel.getFood_category().trim().toLowerCase().equals(category)) {
                arrayList.add(foodModel);
            }
        }
        return arrayList;
    }


    @Override
    public int getItemCount() {
        return foodModelArrayList.size();
    }

    public void filterList(ArrayList<FoodModel> foodModels) {
        foodModelArrayList = foodModels;
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return foodModelArrayList.isEmpty();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt_view_added_to_cart_from_main;
        private final TextView txt_food_special_name;
        private final TextView txt_category_of_food;
        private final TextView txt_price_of_food;
        private final TextView txt_dollar;
        private final TextView txt_view_add_to_cart_from_main;
        private final ImageView img_of_food;
        private final ImageView img_of_food_category;
        private final CardView card_view_food;
        private final RelativeLayout rel_add_to_cart;
        private RelativeLayout rel_full_food_cart;

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
