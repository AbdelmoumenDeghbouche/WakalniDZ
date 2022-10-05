package com.digidz.wakalnidz.Activities;

import static android.content.ContentValues.TAG;

import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.digidz.wakalnidz.R;
import com.digidz.wakalnidz.View.CartFragment;
import com.digidz.wakalnidz.View.MainFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Main2Activity extends AppCompatActivity {

    private FloatingActionButton fab_go_to_cart;
    private Fragment dynamic_fragment;
    private int counter = 0;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.digidz.wakalnidz.R.layout.activity_main2);
        modifysupportactiobar();
        initViews();
        fragments_Logic();
    }


    private void fragments_Logic() {
        handling_main_fragment();
        fab_business_logic();
        if (dynamic_fragment != null) {
            Log.d(TAG, "fragments_Logic: fragment non null");
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.frame_main_container, dynamic_fragment)
                    .commit();

        }
    }

    private void fab_business_logic() {
        fab_go_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.frame_main_container, new CartFragment())
                        .commit();
                counter=0;
            }
        });
    }


    private void initViews() {
        fab_go_to_cart = findViewById(R.id.fab_go_to_cart);
    }

    private void handling_main_fragment() {
        getSupportFragmentManager().beginTransaction().add(R.id.frame_main_container, new MainFragment()).commit();
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

    @Override
    public void onBackPressed() {
        if (counter < 2) {
            Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();
            counter++;
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.frame_main_container, new MainFragment())
                    .commit();

        } else if (counter == 2) {
            super.onBackPressed();
            counter=0;
        }
    }
}