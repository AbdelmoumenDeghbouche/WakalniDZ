package com.digidz.wakalnidz.Activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.digidz.wakalnidz.Model.Cart_food_Model;
import com.digidz.wakalnidz.Model.FoodModel;
import com.digidz.wakalnidz.R;
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

public class DetailsActivity extends AppCompatActivity {
    private ImageView img_of_food_in_details_act;
    private TextView txt_description, txt_add_quantity_of_food, txt_minus_quantity_of_food, txt_quantity_of_food, txt_price_of_food_in_detail_act, txt_food_name_in_details_act;
    private int quantity;
    private Intent intent;
    private TextView txt_add_to_cart, txt_added_to_cart;
    private FirebaseDatabase rootNode;

    private DatabaseReference cartElementsReference;
    private FoodModel foodModel_coming_from_rv;
    private ArrayList<Cart_food_Model> cartFoodListFromDB = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.digidz.wakalnidz.R.layout.activity_details);
        modifysupportactiobar();
        initViews();
        businessLogic();


    }

    private void businessLogic() {
        businessLogicOfQuantityOfFood();
        intent = getIntent();
        if (intent != null) {
            foodModel_coming_from_rv = intent.getParcelableExtra("FoodModel");
            txt_food_name_in_details_act.setText(foodModel_coming_from_rv.getFoodSpecialName());
            txt_price_of_food_in_detail_act.setText(foodModel_coming_from_rv.getPrice() + " DZD");
            int ss = intent.getIntExtra("image", 0);
            Glide.with(this)
                    .load(ss)
                    .into(img_of_food_in_details_act);

            GetCartListFromDB();
            txt_add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Cart_food_Model cartFoodModel = new Cart_food_Model("1", foodModel_coming_from_rv.getFoodSpecialName(), foodModel_coming_from_rv.getPrice(), foodModel_coming_from_rv.getPrice(), intent.getStringExtra("imgName"));
                    cartFoodListFromDB.add(cartFoodModel);
                    saveCartElementsToFirebaseDatabase(cartFoodListFromDB);
                }
            });
            removeElementFromCart(foodModel_coming_from_rv);
        }
    }

    private void removeElementFromCart(FoodModel foodModel) {
        txt_added_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFoodFromCartListInFirebase(foodModel);
                txt_added_to_cart.setVisibility(View.GONE);
                txt_add_to_cart.setVisibility(View.VISIBLE);

            }
        });
    }

    private void businessLogicOfQuantityOfFood() {

        quantity = Integer.parseInt(txt_quantity_of_food.getText().toString());
        txt_add_quantity_of_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_quantity_of_food.setText(String.valueOf(quantity = quantity + 1));
                if (Integer.parseInt(txt_quantity_of_food.getText().toString()) >= 2) {
                    txt_price_of_food_in_detail_act.setText((quantity * Integer.parseInt(txt_price_of_food_in_detail_act.getText().toString().substring(0, 3))) - 10 + " DZD");

                } else {
                    txt_price_of_food_in_detail_act.setText(quantity * Integer.parseInt(txt_price_of_food_in_detail_act.getText().toString().substring(0, 3)) + " DZD");

                }

            }
        });
        txt_minus_quantity_of_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_quantity_of_food.setText(String.valueOf(quantity = quantity - 1));

                if (Integer.parseInt(txt_quantity_of_food.getText().toString()) >= 2) {
                    if (Integer.parseInt(txt_quantity_of_food.getText().toString()) >= 2) {
                        Log.d(TAG, "onClick: ani fi quant >1");
                        txt_price_of_food_in_detail_act.setText((quantity * Integer.parseInt(txt_price_of_food_in_detail_act.getText().toString().substring(0, 3))) - 10 + " DZD");

                    }

                } else if (Integer.parseInt(txt_quantity_of_food.getText().toString()) <= 1) {

                    Log.d(TAG, "onClick: ani fi quant =1");
                    txt_price_of_food_in_detail_act.setText(foodModel_coming_from_rv.getPrice() + " DZD");
                    txt_quantity_of_food.setText(String.valueOf(quantity = 1));


                }
            }
        });

    }

    private void initViews() {
        txt_price_of_food_in_detail_act = findViewById(R.id.txt_price_of_food_in_detail_act);
        txt_minus_quantity_of_food = findViewById(R.id.txt_minus_quantity_of_food);
        txt_add_quantity_of_food = findViewById(R.id.txt_add_quantity_of_food);
        txt_quantity_of_food = findViewById(R.id.txt_quantity_of_food);
        txt_food_name_in_details_act = findViewById(R.id.txt_food_name_in_details_act);
        img_of_food_in_details_act = findViewById(R.id.img_of_food_in_details_act);
        txt_description = findViewById(R.id.txt_description);
        txt_add_to_cart = (TextView) findViewById(R.id.txt_add_to_cart);
        txt_added_to_cart = (TextView) findViewById(R.id.txt_added_to_cart);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void modifysupportactiobar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        this.getWindow().setStatusBarColor(getColor(R.color.main_orange));

        // to change the color of the icons in status bar to dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
        }

    }

    private void saveCartElementsToFirebaseDatabase(ArrayList<Cart_food_Model> cartFoodModelArrayList) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        rootNode = FirebaseDatabase.getInstance();
        cartElementsReference = rootNode.getReference();
        if (firebaseUser != null) {
            cartElementsReference.child("cart").child(firebaseUser.getUid()).setValue(cartFoodModelArrayList);

        }


    }

    private void deleteFoodFromCartListInFirebase(FoodModel FoodModelWantedTobeDeleted) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            FirebaseDatabase.getInstance().getReference().child("cart").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (txt_add_to_cart.getVisibility() == View.VISIBLE) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Cart_food_Model cartFoodModel = dataSnapshot.getValue(Cart_food_Model.class);
                                if (cartFoodModel.getFood_name_in_cart_act() != null && FoodModelWantedTobeDeleted.getFoodSpecialName() != null) {
                                    Log.d(TAG, "foodModels not null: ");
                                    if (dataSnapshot.getKey() != null) {
                                        if (cartFoodModel.getFood_name_in_cart_act().trim().toLowerCase().equals(FoodModelWantedTobeDeleted.getFoodSpecialName().trim().toLowerCase())) {
                                            FirebaseDatabase.getInstance().getReference().child("cart").child(currentUser.getUid()).child(dataSnapshot.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Log.d(TAG, "removed:ddss ");
                                                    Toast.makeText(DetailsActivity.this, "Food Deleted from Cart", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(DetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }

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

    private void GetCartListFromDB() {
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

                                if (IsThisArraylistContainAnObjectThatHasFields(foodModel_coming_from_rv, foodModel.getFood_name_in_cart_act().trim().toLowerCase(), foodModel.getPrice_of_single_item_food_in_cart_act().trim().toLowerCase())) {
                                    txt_add_to_cart.setVisibility(View.GONE);
                                    txt_added_to_cart.setVisibility(View.VISIBLE);

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

}