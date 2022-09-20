package com.digidz.wakalnidz.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digidz.wakalnidz.Model.FoodModel;
import com.digidz.wakalnidz.R;
import com.digidz.wakalnidz.ViewModel.Food_list_adapter;

import java.util.ArrayList;


public class FoodCartsFragment extends Fragment {
    private RecyclerView rv_food_with_add_to_cart;
    private ArrayList<FoodModel> foods_list = new ArrayList<>();

    private Food_list_adapter adapter2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_food_carts, container, false);

        AssingElements(view);
        setting_up_list_2();
        setting_up_rv(view);
        return view;
    }

    private void setting_up_rv(View view) {
        adapter2 = new Food_list_adapter(foods_list, this.getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_food_with_add_to_cart.setAdapter(adapter2);
        rv_food_with_add_to_cart.setLayoutManager(linearLayoutManager);

    }

    private void setting_up_list_2() {
        FoodModel foodModel = new FoodModel("Pepperoni Pizza", getActivity().getDrawable(R.drawable.pop_1), "400", R.drawable.cardview_style_white);
        foods_list.add(foodModel);
        foods_list.add(new FoodModel("        Big Burger        ", getActivity().getDrawable(R.drawable.pop_2), "300", R.drawable.cardview_style_white));
        foods_list.add(new FoodModel("Margarette Pizza", getActivity().getDrawable(R.drawable.pop_3), "200", R.drawable.cardview_style_white));

    }


    private void AssingElements(View view) {
        rv_food_with_add_to_cart = view.findViewById(R.id.rv_food_with_add_to_cart);

    }
}