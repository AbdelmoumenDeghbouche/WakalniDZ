package com.digidz.wakalnidz.Activities;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowInsetsController;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digidz.wakalnidz.Model.FoodModel;
import com.digidz.wakalnidz.R;
import com.digidz.wakalnidz.ViewModel.Food_list_adapter;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private RecyclerView categories_rv;
    private Food_list_adapter adapter;
    private ArrayList<FoodModel> foodCategories_list = new ArrayList<>();

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.digidz.wakalnidz.R.layout.activity_main2);
        modifysupportactiobar();
        initViews();
        setting_up_list();
        setting_up_rv();

    }

    private void setting_up_list() {
        FoodModel pizza_category = new FoodModel(getDrawable(R.drawable.cat_1), "Pizza", R.drawable.cardview_style);
        foodCategories_list.add(pizza_category);
        foodCategories_list.add(new FoodModel(getDrawable(R.drawable.cat_2), "Burger", R.drawable.cardview_style));
        foodCategories_list.add(new FoodModel(getDrawable(R.drawable.cat_3), "HotDog", R.drawable.cardview_style));
        foodCategories_list.add(new FoodModel(getDrawable(R.drawable.cat_4), "Drink", R.drawable.cardview_style));
        foodCategories_list.add(new FoodModel(getDrawable(R.drawable.cat_5), "Donuts", R.drawable.cardview_style));


    }

    private void setting_up_rv() {
        adapter = new Food_list_adapter(foodCategories_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        categories_rv.setLayoutManager(linearLayoutManager);
        categories_rv.setAdapter(adapter);


    }

    private void initViews() {
        categories_rv = findViewById(R.id.rv_food_categories);

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
}