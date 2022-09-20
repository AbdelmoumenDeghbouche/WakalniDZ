package com.digidz.wakalnidz.Activities;

import static android.content.ContentValues.TAG;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowInsetsController;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digidz.wakalnidz.Model.FoodModel;
import com.digidz.wakalnidz.R;
import com.digidz.wakalnidz.Repositories.Utils;
import com.digidz.wakalnidz.View.FoodCartsFragment;
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
        setUp_key_and_value_of_drawables();
        setting_up_list_1();
        setting_up_rv_1();
        handling_fragment();

    }

    private void handling_fragment() {
        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_food_container,new FoodCartsFragment());
        fragmentTransaction.commit();
    }

    private void setUp_key_and_value_of_drawables() {
        Utils.keyDrawableHashMap.put("1", R.drawable.pizza);
        Utils.keyDrawableHashMap.put("2", R.drawable.pop_2);
        Utils.keyDrawableHashMap.put("3", R.drawable.pop_3);

        Log.d(TAG, "setUp_key_and_value_of_drawables: " + Utils.keyDrawableHashMap);
    }



    private void setting_up_list_1() {

        FoodModel pizza_category = new FoodModel(getDrawable(R.drawable.cat_1), " Pizza ", R.drawable.cardview_style_2);
        foodCategories_list.add(pizza_category);
        foodCategories_list.add(new FoodModel(getDrawable(R.drawable.cat_2), "Burger", R.drawable.cardview_style_3));
        foodCategories_list.add(new FoodModel(getDrawable(R.drawable.cat_3), "HotDog", R.drawable.cardview_style_4));
        foodCategories_list.add(new FoodModel(getDrawable(R.drawable.cat_4), "  Drink  ", R.drawable.cardview_style_5));
        foodCategories_list.add(new FoodModel(getDrawable(R.drawable.cat_5), "Donuts", R.drawable.cardview_style));


    }

    private void setting_up_rv_1() {
        adapter = new Food_list_adapter(foodCategories_list, this);
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