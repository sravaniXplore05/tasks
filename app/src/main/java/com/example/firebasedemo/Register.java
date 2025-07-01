package com.example.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
private EditText email,password;
private Button regbutton;
private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        regbutton=findViewById(R.id.registerbutton);
        auth=FirebaseAuth.getInstance();
        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_pass = password.getText().toString();
                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_pass)) {
                    Toast.makeText(Register.this, "invalid credentials", Toast.LENGTH_SHORT).show();
                } else if (txt_pass.length() < 6) {
                    Toast.makeText(Register.this, "passsod too short", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser(txt_email,txt_pass);
                }
            }

            private void registerUser(String txtEmail, String txtPass) {
                auth.createUserWithEmailAndPassword(txtEmail, txtPass).addOnCompleteListener(Register.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful())
                     {
                         Toast.makeText(Register.this, "Register user succesfull", Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(Register.this, BasicPage.class));
                         finish();
                     }
                     else {
                         Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                     }
                    }
                });
            }
        });


    }

}