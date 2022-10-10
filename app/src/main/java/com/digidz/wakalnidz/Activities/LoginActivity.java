package com.digidz.wakalnidz.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.digidz.wakalnidz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView txt_sign_up_to_acc;
    private ImageView img_view_btn_login_acc;
    private EditText edt_email_login, edt_pass_login;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.digidz.wakalnidz.R.layout.activity_login);
        modifysupportactiobar();
        initViews();
        businessLogic();
    }

    private void businessLogic() {
        mAuth = FirebaseAuth.getInstance();
        img_view_btn_login_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkAllTheCasesOfEditTextInCreatingAUser()) {
                    mAuth.signInWithEmailAndPassword(edt_email_login.getText().toString(), edt_pass_login.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Login in Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Login Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }


            }
        });
        txt_sign_up_to_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    private void initViews() {
        txt_sign_up_to_acc = (TextView) findViewById(R.id.txt_sign_up_to_acc);
        img_view_btn_login_acc = (ImageView) findViewById(R.id.img_view_btn_login_acc);
        edt_email_login = (EditText) findViewById(R.id.edt_email_login);
        edt_pass_login = (EditText) findViewById(R.id.edt_pass_login);
    }

    private boolean checkAllTheCasesOfEditTextInCreatingAUser() {
        if (TextUtils.isEmpty(edt_email_login.getText().toString())) {
            edt_email_login.setError("Email cannot be empty");
            edt_email_login.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(edt_pass_login.getText().toString())) {
            edt_pass_login.setError("Email cannot be empty");
            edt_pass_login.requestFocus();
            return false;

        }
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void modifysupportactiobar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        this.getWindow().setStatusBarColor(getColor(R.color.bckg_act));

        // to change the color of the icons in status bar to dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
        }

    }
}