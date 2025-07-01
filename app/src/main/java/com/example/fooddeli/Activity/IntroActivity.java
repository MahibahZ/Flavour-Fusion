package com.example.fooddeli.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fooddeli.R;

public class IntroActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;
    private TextView errorText;
    private Button loginBtn;
    

    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "admin123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        errorText = findViewById(R.id.errorText);
        loginBtn = findViewById(R.id.startBtn);

        loginBtn.setOnClickListener(view -> {

            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();


            if (validateCredentials(username, password)) {
                Toast.makeText(IntroActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
                finish();
            } else {
                errorText.setVisibility(View.VISIBLE);
                errorText.setText("Invalid username or password");
            }
        });
    }
    
    private boolean validateCredentials(String username, String password) {
        return VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password);
    }
}
