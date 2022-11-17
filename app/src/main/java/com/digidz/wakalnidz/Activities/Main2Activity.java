package com.digidz.wakalnidz.Activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.digidz.wakalnidz.R;
import com.digidz.wakalnidz.View.CartFragment;
import com.digidz.wakalnidz.View.MainFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main2Activity extends AppCompatActivity {

    public static String nameOfUser;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FloatingActionButton fab_go_to_cart;
    private Fragment dynamic_fragment;
    private LinearLayout lin_layout_settings, lin_layout_home;
    private int counter = 0;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.digidz.wakalnidz.R.layout.activity_main2);
        modifysupportactiobar();
        initViews();
        initFirebaseAuth();
        fragments_Logic();

        businessLogic();


    }

    private void businessLogic() {
        signOutBusinessLogic();
        Intent intent = getIntent();
        if (intent != null) {
            nameOfUser = intent.getStringExtra("UserName");
            Log.d(TAG, "NameOfUser: " + nameOfUser);
        }
        bottomActionBarBL();

    }

    private void bottomActionBarBL() {
        lin_layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.frame_main_container, new MainFragment())
                        .commit();
            }
        });
    }

    private void signOutBusinessLogic() {
        lin_layout_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(Main2Activity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Main2Activity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
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
                counter = 0;
            }
        });
    }


    private void initViews() {
        fab_go_to_cart = findViewById(R.id.fab_go_to_cart);
        lin_layout_settings = findViewById(R.id.lin_layout_settings);
        lin_layout_home = (LinearLayout) findViewById(R.id.lin_layout_home);
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
            counter = 0;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(Main2Activity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        else if (user.getDisplayName()==null){


        }
    }
}