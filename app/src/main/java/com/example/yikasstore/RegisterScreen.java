package com.example.yikasstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterScreen extends AppCompatActivity {

    String name;
    String mail;
    String pass;
    EditText txtRegName;
    EditText txtRegMail;
    EditText txtPass;
    EditText txtPassAgain;
    Button btnSaveUser;
    Button btnClearAll;
    DatabaseReference dataRef;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);


        txtPass = findViewById(R.id.txtRegPass);
        txtPassAgain = findViewById(R.id.txtRegPassAgain);
        txtRegMail = findViewById(R.id.txtRegMail);
        txtRegName = findViewById(R.id.txtRegName);
        btnSaveUser = findViewById(R.id.btnSaveUser);
        btnClearAll = findViewById(R.id.btnClear);
        auth = FirebaseAuth.getInstance();

        btnClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllTexts();
            }
        });
        btnSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTextFields();
            }
        });
    }

    private void checkTextFields() {

        if (txtRegName.getText().toString().equals("") || txtRegMail.getText().toString().equals("")
                || txtPass.getText().toString().equals("") || txtPassAgain.getText().toString().equals("")) {

            Toast.makeText(getApplicationContext(), "lütfen gerekli yerleri doldurun!", Toast.LENGTH_SHORT).show();

        } else {
            mail = txtRegMail.getText().toString();
            pass = txtPass.getText().toString();
            addUser();
        }

    }

    private void addUser() {
        final String password = txtPass.getText().toString();
        final String passwordAgain = txtPassAgain.getText().toString();

        if (!password.equals(passwordAgain)) {
            Toast.makeText(getApplicationContext(), "Passwords did not match!", Toast.LENGTH_SHORT).show();
            txtPass.setText("");
            txtPassAgain.setText("");
        } else {
            auth.createUserWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(RegisterScreen.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) { //HATA VAR. HER ŞARTTA PASSWORDLERİ SIFIRLIYOR AMA KAYIT ALIYOR.

                                String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                dataRef = FirebaseDatabase.getInstance().getReference().child("ProLang");
                                UserModel user = new UserModel(uId, name, mail, pass);
                                dataRef.setValue(user);
                                sendVerify();
                                Intent intent = new Intent(RegisterScreen.this, MainMenuActivity.class);
                                startActivity(intent);
                                RegisterScreen.this.finish();
                            }
                        }
                    });
        }
    }

    private void sendVerify() {
        auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Objects.requireNonNull(auth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                clearAllTexts();
                                Toast.makeText(getApplicationContext(), "please check email for verification.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterScreen.this, MainMenuActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clearAllTexts() {
        txtRegName.setText("");
        txtRegMail.setText("");
        txtPass.setText("");
        txtPassAgain.setText("");
    }
}
