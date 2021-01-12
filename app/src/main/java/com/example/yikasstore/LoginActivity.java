package com.example.yikasstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText txtMail;
    EditText txtPassword;
    Button btnLogin;
    TextView txtForgetPassword;
    TextView txtNewAccount;
    FirebaseAuth auth;
    String userMail;
    String userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        init();
    }

    private void init() {
        txtNewAccount = findViewById(R.id.txtCreateAnAccount);
        txtForgetPassword = findViewById(R.id.txtForgetPassword);
        txtMail = findViewById(R.id.txtLoginMail);
        txtPassword = findViewById(R.id.txtLoginPassword);
        btnLogin = findViewById(R.id.btnLoginEnter);
        auth = FirebaseAuth.getInstance();
        userMail = txtMail.getText().toString();
        userPass = txtPassword.getText().toString();

        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ResetPassScreen.class);
                startActivity(intent);
            }
        });

        txtNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterScreen.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userMail = txtMail.getText().toString();
                userPass = txtPassword.getText().toString();
                if (userPass.isEmpty() || userMail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Fill All Areas!", Toast.LENGTH_SHORT).show();
                } else {
                    checkUserMailAndPass();
                }
            }
        });
    }

    private void checkUserMailAndPass() {
        auth.signInWithEmailAndPassword(userMail, userPass).addOnCompleteListener(LoginActivity.this
                , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
