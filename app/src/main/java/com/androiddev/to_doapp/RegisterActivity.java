package com.androiddev.to_doapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar regToolbar;
    private TextView regQn;
    private EditText regUsername, regPassword, regEmail, regPhone;
    private Button regBtn;
    private FirebaseAuth mAuth;

    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        regToolbar = findViewById(R.id.registerToolbar);
        setSupportActionBar(regToolbar);
        getSupportActionBar().setTitle("Registration");

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);

        regUsername = findViewById(R.id.registerUsername);
        regEmail = findViewById(R.id.registerEmail);
        regPassword = findViewById(R.id.registerPassword);
        regPhone = findViewById(R.id.registerPhone);
        regBtn = findViewById(R.id.registerButton);
        regQn = findViewById(R.id.registerPageQuestion);

        regQn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = regUsername.getText().toString().trim();
                String email = regEmail.getText().toString().trim();
                String password = regPassword.getText().toString().trim();
                String phone = regPhone.getText().toString().trim();

                if (TextUtils.isEmpty(username)){
                    regUsername.setError(" Username is Required ");
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    regEmail.setError(" Email is Required ");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    regPassword.setError(" Password is Required ");
                    return;
                }
                else if (TextUtils.isEmpty(phone)){
                    regPhone.setError(" Phone is Required ");
                    return;
                }
                else{
                    loader.setMessage("Please Wait!!!");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                            }
                            else{
                                String error = task.getException().toString();
                                Toast.makeText(RegisterActivity.this, "Registration failed!" + error, Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }
                        }
                    });
                }

            }
        });

    }
}
