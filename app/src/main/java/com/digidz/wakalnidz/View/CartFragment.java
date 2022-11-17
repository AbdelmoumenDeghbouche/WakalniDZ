package com.digidz.wakalnidz.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digidz.wakalnidz.Activities.CheckoutActivity;
import com.digidz.wakalnidz.Model.Cart_food_Model;
import com.digidz.wakalnidz.R;
import com.digidz.wakalnidz.ViewModel.Cart_list_adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CartFragment extends Fragment {
    private static final String TAG = "";
    @SuppressLint("StaticFieldLeak")
    public static TextView txt_total_cost, txt_items_total_cost, txt_delivery_service_cost;
    private RecyclerView rv_cart;
    private ScrollView scv_cart;
    private RelativeLayout rel_empty_cart;
    private com.digidz.wakalnidz.ViewModel.Cart_list_adapter adapter;
    private TextView txt_tax_cost, txt_checkout;
    private ArrayList<Cart_food_Model> cartFoodListFromDB = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_cart, container, false);
        AssingElements(view);
        setUpListOfRv1();
        setUpRV1();
        handlingLogicOfPricing();
        businessLogic();
        return view;
    }

    private void businessLogic() {
        gotoCheckOutActivity();
    }

    private void gotoCheckOutActivity() {

        txt_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CheckoutActivity.class);
                intent.putExtra("txt_items_total_cost",txt_items_total_cost.getText().toString());
                intent.putExtra("txt_delivery_service_cost",txt_delivery_service_cost.getText().toString());
                intent.putExtra("txt_total_cost",txt_total_cost.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void handlingLogicOfPricing() {
        int cost = 0;
        txt_items_total_cost.setText(cost + " DZD");

        Log.d(TAG, "handlingLogicOfPricing: " + txt_items_total_cost.getText().toString().substring(0, 2));
    }

    private void emptyCart() {
        if (cartFoodListFromDB.isEmpty()) {
            scv_cart.setVisibility(View.GONE);
            rel_empty_cart.setVisibility(View.VISIBLE);


        } else {
            rel_empty_cart.setVisibility(View.GONE);
            scv_cart.setVisibility(View.VISIBLE);
            txt_checkout.setVisibility(View.VISIBLE);
        }
    }

    private void setUpRV1() {


    }

    private void setUpListOfRv1() {
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

                            Log.d(TAG, "existssss: " + cartFoodListFromDB.get(0).getFood_name_in_cart_act());

                        }
                        for (Cart_food_Model cartFoodModel : cartFoodListFromDB) {
                            Log.d(TAG, "bbbb: " + cartFoodModel.getFood_name_in_cart_act());

                        }
                    }
                    adapter = new Cart_list_adapter(cartFoodListFromDB, getContext());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    rv_cart.setLayoutManager(linearLayoutManager);
                    rv_cart.setAdapter(adapter);
                    emptyCart();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }


    private void AssingElements(View view) {
        rv_cart = view.findViewById(R.id.rv_cart);
        txt_checkout = view.findViewById(R.id.txt_checkout);
        txt_delivery_service_cost = view.findViewById(R.id.txt_delivery_service_cost);
        txt_tax_cost = view.findViewById(R.id.txt_tax_cost);
        txt_total_cost = view.findViewById(R.id.txt_total_cost);
        txt_items_total_cost = view.findViewById(R.id.txt_items_total_cost);
        scv_cart = view.findViewById(R.id.scv_cart);
        rel_empty_cart = view.findViewById(R.id.rel_empty_cart);


    }
}