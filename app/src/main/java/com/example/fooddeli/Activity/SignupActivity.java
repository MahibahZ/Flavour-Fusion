package com.example.fooddeli.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.fooddeli.Helper.TinyDB;
import com.example.fooddeli.R;

public class SignupActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText username = findViewById(R.id.editTextUsername);
        EditText email = findViewById(R.id.editTextEmail);
        EditText password = findViewById(R.id.editTextPassword);
        Button signupBtn = findViewById(R.id.btn_signup);

        TinyDB tinyDB = new TinyDB(this);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                if (userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    tinyDB.putString("user_username", userName);
                    tinyDB.putString("user_email", userEmail);
                    tinyDB.putString("user_password", userPassword);
                    tinyDB.putString("user_name", userName);
                    tinyDB.putBoolean("is_logged_in", true);
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
} 