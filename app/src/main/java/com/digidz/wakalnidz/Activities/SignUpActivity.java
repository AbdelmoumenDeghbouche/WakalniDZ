package com.digidz.wakalnidz.Activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.digidz.wakalnidz.Repositories.UserClassHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private TextView txt_login_to_acc, txt_full_name, txt_email_address, txt_create_acc;
    private ImageView img_view_btn_create_acc;
    private FirebaseAuth mAuth;
    private FirebaseDatabase rootNode;
    private DatabaseReference usersReference;
    private EditText edt_email, edt_pass, edt_confirm_pass, edt_full_name;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.digidz.wakalnidz.R.layout.activity_sign_up);
        modifysupportactiobar();
        initViews();

        createUser();
        businessLogic();
    }


    private void businessLogic() {
        txt_login_to_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
        img_view_btn_create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

        treatingIntentAndItsChanges();

    }

    private void treatingIntentAndItsChanges() {
        Intent intent = getIntent();
        if (intent != null) {
            boolean existedUser = intent.getBooleanExtra("JustUpdatingPassword", false);
            if (existedUser) {
                edt_full_name.setVisibility(View.GONE);
                edt_email.setVisibility(View.GONE);
                txt_create_acc.setText("Update Account");
                txt_email_address.setVisibility(View.GONE);
                txt_full_name.setVisibility(View.GONE);

            }

        }
    }

    private void initViews() {
        txt_login_to_acc = findViewById(R.id.txt_login_to_acc);
        img_view_btn_create_acc = findViewById(R.id.img_view_btn_create_acc);
        edt_email = findViewById(R.id.edt_email);
        edt_pass = findViewById(R.id.edt_pass);
        edt_full_name = findViewById(R.id.edt_full_name);
        edt_confirm_pass = findViewById(R.id.edt_confirm_pass);
        txt_full_name = (TextView) findViewById(R.id.txt_full_name);
        txt_email_address = (TextView) findViewById(R.id.txt_email_address);
        txt_create_acc = (TextView) findViewById(R.id.txt_create_acc);


    }

    private void createUser() {
        mAuth = FirebaseAuth.getInstance();
        if (checkAllTheCasesOfEditTextInCreatingAUser()) {
            mAuth.createUserWithEmailAndPassword(edt_email.getText().toString().trim().toLowerCase(), edt_pass.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "onComplete: " + edt_pass.getText());
                                Toast.makeText(SignUpActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, Main2Activity.class);
                                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(edt_full_name.getText().toString())
                                        .build();
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    user.updateProfile(userProfileChangeRequest);
                                    creatingUserInRealTimeDatabase(user);
                                }
                                intent.putExtra("UserName", edt_full_name.getText().toString());
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {

                                Toast.makeText(SignUpActivity.this, "Login error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


        }

    }

    private void creatingUserInRealTimeDatabase(FirebaseUser user) {
        rootNode = FirebaseDatabase.getInstance();
        usersReference = rootNode.getReference();
        UserClassHelper userHelper = new UserClassHelper(edt_email.getText().toString().trim().toLowerCase(), edt_pass.getText().toString(), edt_full_name.getText().toString());
        usersReference.child("users").child(user.getUid()).setValue(userHelper);


    }


    private boolean checkRegex(String str, boolean isItAnEmail) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        boolean symbolFlag = false;
        if (str.length() < 8) {
            return false;
        }
        if (isItAnEmail) {
            return str.contains("@") && str.contains(".com");


        } else {
            for (int i = 0; i < str.length(); i++) {
                ch = str.charAt(i);
                if (Character.isDigit(ch)) {
                    numberFlag = true;
                } else if (Character.isUpperCase(ch)) {
                    capitalFlag = true;
                } else if (Character.isLowerCase(ch)) {
                    lowerCaseFlag = true;
                } else if (!Character.isLetterOrDigit(ch)) {
                    symbolFlag = true;
                }

                if (numberFlag && capitalFlag && lowerCaseFlag && symbolFlag)
                    return true;
            }
        }


        return false;
    }

    private boolean checkAllTheCasesOfEditTextInCreatingAUser() {
        if (TextUtils.isEmpty(edt_email.getText().toString())) {
            edt_email.setError("Email cannot be empty");
            edt_email.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(edt_pass.getText().toString())) {
            edt_pass.setError("Email cannot be empty");
            edt_pass.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(edt_confirm_pass.getText().toString())) {
            edt_confirm_pass.setError("Email cannot be empty");
            edt_confirm_pass.requestFocus();
            return false;

        } else if (!checkRegex(edt_email.getText().toString(), true)) {
            edt_email.setError("Your email format is unsupportable :(");
            edt_email.requestFocus();
            return false;

        } else if (!checkRegex(edt_pass.getText().toString(), false)) {
            edt_pass.setError("password must contain 8 to 30 characters and must have at least 1 capital letter and 1 symbol");
            edt_pass.requestFocus();
            return false;

        } else if (!edt_confirm_pass.getText().toString().equals(edt_pass.getText().toString())) {
            if ((calculateAscii(edt_pass.getText().toString()) != calculateAscii(edt_confirm_pass.getText().toString()))) {
                edt_confirm_pass.setError("password must match !");
                edt_confirm_pass.requestFocus();
                return false;
            }


        }
        return true;

    }

    private int calculateAscii(String str) {
        char ch;
        int ascii = 0;
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            ascii = ascii + (int) ch;
        }

        return ascii;
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