package com.example.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class BasicPage extends AppCompatActivity {

    private Button btnSubmit, btnLogout, btnViewHistory;
    private EditText etDrinking, etBathing, etWashing, etCleaning;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_page);

        // Firebase authentication instance
        auth = FirebaseAuth.getInstance();

        // Initialize views
        etDrinking = findViewById(R.id.et_drinking);
        etBathing = findViewById(R.id.et_bathing);
        etWashing = findViewById(R.id.et_washing);
        etCleaning = findViewById(R.id.et_cleaning);
        btnSubmit = findViewById(R.id.btn_submit);
        btnLogout = findViewById(R.id.logout);
        btnViewHistory = findViewById(R.id.btnViewHistory);

        // Submit usage
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWaterUsage();
            }
        });

        btnViewHistory.setOnClickListener(v -> {
            startActivity(new Intent(BasicPage.this, UsageHistoryActivity.class));
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Toast.makeText(BasicPage.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BasicPage.this, MainActivity.class));
                finish();
            }
        });
    }

    private void saveWaterUsage() {
        int drinking = getNumberFromEditText(etDrinking);
        int bathing = getNumberFromEditText(etBathing);
        int washing = getNumberFromEditText(etWashing);
        int cleaning = getNumberFromEditText(etCleaning);

        int totalLitres = (drinking * 1) + (bathing * 25) + (washing * 50) + (cleaning * 10);

        // Format current date (e.g., 12-Jun-2025)
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        // Prepare data
        HashMap<String, Object> map = new HashMap<>();
        map.put("Drinking (1L bottles)", drinking);
        map.put("Bathing (25L each)", bathing);
        map.put("Washing (50L each)", washing);
        map.put("Cleaning (10L each)", cleaning);
        map.put("Total Water Usage (L)", totalLitres);
        map.put("Date", currentDate);

        // Push to Firebase
        FirebaseDatabase.getInstance().getReference()
                .child("WaterUsage")
                .child(auth.getCurrentUser().getUid())
                .push()
                .setValue(map);

        Toast.makeText(this, "Water usage saved!\nTotal: " + totalLitres + " L", Toast.LENGTH_LONG).show();

        // Clear input fields
        etDrinking.setText("");
        etBathing.setText("");
        etWashing.setText("");
        etCleaning.setText("");
    }

    private int getNumberFromEditText(EditText editText) {
        String text = editText.getText().toString().trim();
        if (text.isEmpty()) return 0;
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
