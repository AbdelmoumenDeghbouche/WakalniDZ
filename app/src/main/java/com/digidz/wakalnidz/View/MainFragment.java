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
import com.digidz.wakalnidz.Repositories.Utils;
import com.digidz.wakalnidz.ViewModel.Food_list_adapter;

import java.util.ArrayList;


public class MainFragment extends Fragment {
    private RecyclerView categories_rv;
    private Food_list_adapter adapter;
    private  ArrayList<FoodModel> foodCategories_list = new ArrayList<>();
    public static RecyclerView rv_food_with_add_to_cart;
    public static ArrayList<FoodModel> foods_list = new ArrayList<>();
    private Food_list_adapter adapter2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_main, container, false);
        AssingElements(view);
        setUp_key_and_value_of_drawables();
        setting_up_list_1();
        setting_up_rv_1();
        setting_up_list_2();
        setting_up_rv();
        return view;
    }

    private void setting_up_rv() {
        adapter2 = new Food_list_adapter(foods_list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_food_with_add_to_cart.setAdapter(adapter2);
        rv_food_with_add_to_cart.setLayoutManager(linearLayoutManager);

    }

    private void setting_up_list_2() {
        FoodModel foodModel = new FoodModel("Pizza", "Pepperoni Pizza", getActivity().getDrawable(R.drawable.pop_1), "400", R.drawable.cardview_style_white);
        foods_list.add(foodModel);
        foods_list.add(new FoodModel("Burger", "        Big Burger        ", getActivity().getDrawable(R.drawable.pop_2), "300", R.drawable.cardview_style_white));
        foods_list.add(new FoodModel("Pizza", "Margarette Pizza", getActivity().getDrawable(R.drawable.pop_3), "200", R.drawable.cardview_style_white));

    }

    private void setting_up_list_1() {

        FoodModel pizza_category = new FoodModel(getActivity().getDrawable(R.drawable.cat_1), " Pizza ", R.drawable.cardview_style_2);
        foodCategories_list.add(pizza_category);
        foodCategories_list.add(new FoodModel(getActivity().getDrawable(R.drawable.cat_2), "Burger", R.drawable.cardview_style_3));
        foodCategories_list.add(new FoodModel(getActivity().getDrawable(R.drawable.cat_3), "HotDog", R.drawable.cardview_style_4));
        foodCategories_list.add(new FoodModel(getActivity().getDrawable(R.drawable.cat_4), "  Drink  ", R.drawable.cardview_style_5));
        foodCategories_list.add(new FoodModel(getActivity().getDrawable(R.drawable.cat_5), "Donuts", R.drawable.cardview_style));

    }

    private void setting_up_rv_1() {
        adapter = new Food_list_adapter(foodCategories_list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext().getApplicationContext(), RecyclerView.HORIZONTAL, false);
        categories_rv.setLayoutManager(linearLayoutManager);
        categories_rv.setAdapter(adapter);


    }

    private void setUp_key_and_value_of_drawables() {
        Utils.keyDrawableHashMap.put("1", R.drawable.pizza);
        Utils.keyDrawableHashMap.put("2", R.drawable.pop_2);
        Utils.keyDrawableHashMap.put("3", R.drawable.pop_3);

    }

    private void AssingElements(View view) {
        categories_rv = view.findViewById(R.id.rv_food_categories);
        rv_food_with_add_to_cart = view.findViewById(R.id.rv_food_with_add_to_cart);

    }
}