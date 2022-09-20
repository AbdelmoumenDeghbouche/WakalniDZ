package com.digidz.wakalnidz.Activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.digidz.wakalnidz.Model.FoodModel;
import com.digidz.wakalnidz.R;

public class DetailsActivity extends AppCompatActivity {
    private ImageView img_of_food_in_details_act;
    private TextView txt_description, txt_add_quantity_of_food, txt_minus_quantity_of_food, txt_quantity_of_food, txt_price_of_food_in_detail_act, txt_food_name_in_details_act;
    private int quantity;
    private Intent intent;
    private FoodModel foodModel_coming_from_rv;

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
            Log.d(TAG, "ssss: " + ss);

            img_of_food_in_details_act.setImageDrawable(getDrawable(ss));
            Log.d(TAG, "businessLogic: " + Drawable.createFromPath(intent.getStringExtra("Foodmodel_str")));
        }

    }

    private void businessLogicOfQuantityOfFood() {

        quantity = Integer.parseInt(txt_quantity_of_food.getText().toString());
        txt_add_quantity_of_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_quantity_of_food.setText(String.valueOf(quantity = quantity + 1));
                if (Integer.parseInt(txt_quantity_of_food.getText().toString()) >= 2) {
                    txt_price_of_food_in_detail_act.setText(String.valueOf((quantity * Integer.parseInt(txt_price_of_food_in_detail_act.getText().toString().substring(0, 3))) - 10) + " DZD");

                } else {
                    txt_price_of_food_in_detail_act.setText(String.valueOf(quantity * Integer.parseInt(txt_price_of_food_in_detail_act.getText().toString().substring(0, 3))) + " DZD");

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
                        txt_price_of_food_in_detail_act.setText(String.valueOf((quantity * Integer.parseInt(txt_price_of_food_in_detail_act.getText().toString().substring(0, 3))) - 10) + " DZD");

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