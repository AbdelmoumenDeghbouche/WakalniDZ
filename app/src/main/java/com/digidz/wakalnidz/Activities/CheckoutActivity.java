package com.digidz.wakalnidz.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.digidz.wakalnidz.R;

public class CheckoutActivity extends AppCompatActivity {
    private RelativeLayout rel_lyt_master_card, rel_lyt_paypal, rel_lyt_visa_card, rel_empty_circle;
    private ImageView img_pay_on_arrival_true, img_view_back;
    private TextView txt_total_cost, txt_delivery_service_cost, txt_items_total_cost;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modifysupportactiobar();
        setContentView(com.digidz.wakalnidz.R.layout.activity_checkout);
        initViews();
        businessLogic();


    }

    private void businessLogic() {

        paymentLayoutsBackground();
        treatPriceComingFromCart();
        backBtnLogic();
    }

    private void backBtnLogic() {
        img_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void treatPriceComingFromCart() {
        Intent intent = getIntent();
        if (intent != null) {
            txt_items_total_cost.setText(intent.getStringExtra("txt_items_total_cost"));
            txt_total_cost.setText(intent.getStringExtra("txt_total_cost"));
            txt_delivery_service_cost.setText(intent.getStringExtra("txt_delivery_service_cost"));
        }
    }

    private void paymentLayoutsBackground() {
        rel_lyt_master_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rel_lyt_master_card.setBackground(getDrawable(R.drawable.stroke_bckg_orange_selected_item));
                rel_lyt_paypal.setBackground(getDrawable(R.drawable.white_radius_backgrond));
                rel_lyt_visa_card.setBackground(getDrawable(R.drawable.white_radius_backgrond));
            }
        });
        rel_lyt_paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rel_lyt_master_card.setBackground(getDrawable(R.drawable.white_radius_backgrond));
                rel_lyt_paypal.setBackground(getDrawable(R.drawable.stroke_bckg_orange_selected_item));
                rel_lyt_visa_card.setBackground(getDrawable(R.drawable.white_radius_backgrond));
            }
        });
        rel_lyt_visa_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rel_lyt_master_card.setBackground(getDrawable(R.drawable.white_radius_backgrond));
                rel_lyt_paypal.setBackground(getDrawable(R.drawable.white_radius_backgrond));
                rel_lyt_visa_card.setBackground(getDrawable(R.drawable.stroke_bckg_orange_selected_item));
            }
        });
        rel_empty_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_pay_on_arrival_true.setVisibility(View.VISIBLE);
            }
        });
        img_pay_on_arrival_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_pay_on_arrival_true.setVisibility(View.GONE);
            }
        });
    }

    private void initViews() {
        rel_lyt_master_card = (RelativeLayout) findViewById(R.id.rel_lyt_master_card);
        rel_lyt_paypal = (RelativeLayout) findViewById(R.id.rel_lyt_paypal);
        rel_lyt_visa_card = (RelativeLayout) findViewById(R.id.rel_lyt_visa_card);
        img_pay_on_arrival_true = (ImageView) findViewById(R.id.img_pay_on_arrival_true);
        rel_empty_circle = (RelativeLayout) findViewById(R.id.rel_empty_circle);
        txt_delivery_service_cost = (TextView) findViewById(R.id.txt_delivery_service_cost);
        txt_total_cost = (TextView) findViewById(R.id.txt_total_cost);
        txt_items_total_cost = (TextView) findViewById(R.id.txt_items_total_cost);
        img_view_back = (ImageView) findViewById(R.id.img_view_back);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void modifysupportactiobar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        this.getWindow().setStatusBarColor(getColor(R.color.backgroundAct));

        // to change the color of the icons in status bar to dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
        }

    }
}