package com.example.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText EMAIL,PASSWORD;
    private Button LoginButton;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        EMAIL=findViewById(R.id.EMAIL);
        PASSWORD=findViewById(R.id.PASSWORD);
        LoginButton=findViewById(R.id.Loginbutton);
        auth=FirebaseAuth.getInstance();
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email=EMAIL.getText().toString();
                String txt_pasword=PASSWORD.getText().toString();

                loginUser(txt_email,txt_pasword);
            }

            private void loginUser(String txtEmail, String txtPasword) {
                auth.signInWithEmailAndPassword(txtEmail,txtPasword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Login.this, "login succesfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, BasicPage.class));
                        finish();
                    }
                });
            }
        });

    }
}