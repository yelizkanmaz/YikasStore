package com.example.yikasstore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


@SuppressWarnings("deprecation")
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth auth;
    TextView forgetPassword;
    TextView createAnAccount;
    EditText txtMail;
    EditText txtPassword;
    Button btnLogin;
    Button btnLoginWGoogle;
    String userMail;
    String userPass;
    String prefName, prefMail, prefPass;
    DatabaseReference dRef;

    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        forgetPassword = findViewById(R.id.txtForgetPassword);
        createAnAccount = findViewById(R.id.txtCreateAnAccount);
        txtMail = findViewById(R.id.txtLoginMail);
        txtPassword = findViewById(R.id.txtLoginPassword);
        btnLogin = findViewById(R.id.btnLoginEnter);
        auth = FirebaseAuth.getInstance();
        userMail = txtMail.getText().toString();
        userPass = txtPassword.getText().toString();
        dRef = FirebaseDatabase.getInstance().getReference().child("yikas");
        btnLoginWGoogle = findViewById(R.id.btn_google);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(LoginActivity.this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        btnLoginWGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        rememberUser();

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResetPassScreen.class);
                startActivity(intent);
            }
        });
        createAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    Toast.makeText(getApplicationContext(), "Lütfen Tüm Alanları Doldurunuz", Toast.LENGTH_SHORT).show();
                } else {
                    checkUserMailAndPass();
                }
            }
        });
    }

    private void rememberUser() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void onStart() {
        LoginActivity.super.onStart();
        getUser();
    }

    public void getUser() {
        SharedPreferences pref = getSharedPreferences(prefName, MODE_PRIVATE);
        String userMail = pref.getString(prefMail, null);
        String userPassword = pref.getString(prefPass, null);

        if (userMail != null || userPassword != null) {
            showLogout(userMail);
        }
    }

    private void showLogout(String username) {
        Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
        intent.putExtra("user", username);
        startActivity(intent);
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

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Hatalı Giriş Yaptınız.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Giriş Yapıldı.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
                            finish();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Google Play Servis Hatası", Toast.LENGTH_SHORT).show();
    }
}
