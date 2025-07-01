package com.example.firebasedemo;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class UsageHistoryActivity extends AppCompatActivity {

    private LinearLayout linearHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_history);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.history_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Usage History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linearHistory = findViewById(R.id.linear_history);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference()
                .child("WaterUsage")
                .child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        linearHistory.removeAllViews();

                        for (DataSnapshot child : snapshot.getChildren()) {
                            Map<String, Object> data = (Map<String, Object>) child.getValue();

                            if (data != null) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("Date: ").append(data.get("Date")).append("\n");
                                sb.append("1 - Drinking (1L bottles): ").append(data.get("Drinking (1L bottles)")).append("\n");
                                sb.append("2 - Bathing (25L each): ").append(data.get("Bathing (25L each)")).append("\n");
                                sb.append("3 - Washing (50L each): ").append(data.get("Washing (50L each)")).append("\n");
                                sb.append("4 - Cleaning (10L each): ").append(data.get("Cleaning (10L each)")).append("\n");
                                sb.append("Total: ").append(data.get("Total Water Usage (L)")).append(" Litres");

                                TextView tv = new TextView(UsageHistoryActivity.this);
                                tv.setText(sb.toString());
                                tv.setPadding(16, 16, 16, 16);
                                tv.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);

                                linearHistory.addView(tv);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Handle error
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
