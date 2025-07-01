package com.example.fooddeli.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.fooddeli.R;

public class PaymentActivity extends AppCompatActivity {
    private ImageButton backBtn;
    private TextView totalAmountTextView, payNowBtn;
    private EditText cardNumberEditText, expiryDateEditText, cvvEditText, nameEditText;
    private CardView payNowCardView;
    private double totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        totalAmount = getIntent().getDoubleExtra("totalAmount", 0.0);

        initView();
        setListeners();
    }

    private void initView() {
        backBtn = findViewById(R.id.backBtn);
        totalAmountTextView = findViewById(R.id.totalAmountTextView);
        cardNumberEditText = findViewById(R.id.cardNumberEditText);
        expiryDateEditText = findViewById(R.id.expiryDateEditText);
        cvvEditText = findViewById(R.id.cvvEditText);
        nameEditText = findViewById(R.id.nameEditText);
        payNowBtn = findViewById(R.id.payNowBtn);
        payNowCardView = findViewById(R.id.payNowCardView);


        totalAmountTextView.setText("Total Amount: PKR " + String.format("%.2f", totalAmount));
    }

    private void setListeners() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        payNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    processPayment();
                }
            }
        });
    }

    private boolean validateFields() {
        String cardNumber = cardNumberEditText.getText().toString().trim();
        String expiryDate = expiryDateEditText.getText().toString().trim();
        String cvv = cvvEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();

        if (cardNumber.isEmpty()) {
            Toast.makeText(this, "Please enter card number", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (expiryDate.isEmpty()) {
            Toast.makeText(this, "Please enter expiry date", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (cvv.isEmpty()) {
            Toast.makeText(this, "Please enter CVV", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter cardholder name", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void processPayment() {

        payNowBtn.setText("Processing...");
        

        payNowCardView.postDelayed(new Runnable() {
            @Override
            public void run() {
                showSuccessDialog();
            }
        }, 1500);
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment Successful");
        builder.setMessage("Your order has been placed successfully!");
        builder.setPositiveButton("OK", (dialog, which) -> {

            new com.example.fooddeli.Helper.ManagementCart(this).clearCart();

            Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
        builder.setCancelable(false);
        builder.show();
    }
} 