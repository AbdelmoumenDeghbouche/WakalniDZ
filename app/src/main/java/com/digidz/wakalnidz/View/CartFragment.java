package com.digidz.wakalnidz.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digidz.wakalnidz.R;
import com.digidz.wakalnidz.Repositories.Utils;
import com.digidz.wakalnidz.ViewModel.Cart_list_adapter;


public class CartFragment extends Fragment {
    private static final String TAG = "";
    @SuppressLint("StaticFieldLeak")
    public static TextView txt_total_cost, txt_items_total_cost, txt_delivery_service_cost;
    private RecyclerView rv_cart;
    private ScrollView scv_cart;
    private RelativeLayout rel_empty_cart;
    private com.digidz.wakalnidz.ViewModel.Cart_list_adapter adapter;
    private TextView txt_tax_cost, txt_checkout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_cart, container, false);
        AssingElements(view);
        setUpListOfRv1();
        setUpRV1();
        emptyCart();
        handlingLogicOfPricing();
        return view;
    }

    private void handlingLogicOfPricing() {
        int cost = 0;
        txt_items_total_cost.setText(String.valueOf(cost) + " DZD");
        Log.d(TAG, "handlingLogicOfPricing: " + txt_items_total_cost.getText().toString().substring(0, 2));
    }

    private void emptyCart() {
        if (Utils.Cart_foods_list.isEmpty()) {
            scv_cart.setVisibility(View.GONE);
            rel_empty_cart.setVisibility(View.VISIBLE);


        } else {
            rel_empty_cart.setVisibility(View.GONE);
            scv_cart.setVisibility(View.VISIBLE);
            txt_checkout.setVisibility(View.VISIBLE);
        }
    }

    private void setUpRV1() {
        adapter = new Cart_list_adapter(Utils.Cart_foods_list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext().getApplicationContext(), RecyclerView.VERTICAL, false);
        rv_cart.setLayoutManager(linearLayoutManager);
        rv_cart.setAdapter(adapter);

    }

    private void setUpListOfRv1() {

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