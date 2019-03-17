package com.saboo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText fullName , userName , email , password;
    Button registerBtn;
    TextView loginRedBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //FirebaseApp.initializeApp(RegisterActivity.this);

        mAuth = FirebaseAuth.getInstance();

        fullName = findViewById(R.id.fullName);
        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        registerBtn = findViewById(R.id.registerBtn);
        loginRedBtn = findViewById(R.id.loginRedBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(fullName.getText()) || TextUtils.isEmpty(userName.getText()) || TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(password.getText()) ) {
                    Toast.makeText(RegisterActivity.this , "Please don't leave the fields blank" , Toast.LENGTH_SHORT).show();
                }
                else {
                    register(fullName.getText().toString() , userName.getText().toString() , email.getText().toString() , password.getText().toString());
                }
            }
        });

        loginRedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });
    }

    private void register(final String fNameString, final String uNameString, final String emailString, String pwdString) {
        mAuth.createUserWithEmailAndPassword(emailString,pwdString)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            HashMap<String,Object> map = new HashMap<>();
                            map.put("fullName",fNameString);
                            map.put("userName",uNameString);
                            map.put("isTeacher",false);
                            FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(uid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                        finish();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(RegisterActivity.this , "Unable to complete registration!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}