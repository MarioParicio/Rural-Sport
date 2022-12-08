package org.iesch.myapplication;

import static android.content.ContentValues.TAG;

import static com.google.android.gms.auth.api.signin.GoogleSignIn.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.iesch.myapplication.ui.home.HomeFragment;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnRegistrar, btnLogin, btnLoginGoogle;

    private FirebaseAnalytics firebaseAnalytics;

    private FirebaseAuth auth;

    private GoogleSignInClient googleSignInClient;

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.EmailEditText);
        etPassword = findViewById(R.id.ContrasenaEditText);
        btnRegistrar = findViewById(R.id.RegistrarButton);
        btnLogin = findViewById(R.id.AccederButton);
        btnLoginGoogle = findViewById(R.id.GoogleButton);


        etEmail.setText(getIntent().getStringExtra("email"));
        etPassword.setText(getIntent().getStringExtra("contraseña"));

        iniciarAnalytics();

        iniciarAuthentication();

        verSiEstaLogueado();


    }

    private void verSiEstaLogueado() {

        SharedPreferences sesion = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        String _email = sesion.getString("email", null);

        if (_email != null) {
            iraHomeActivity(_email);
        }
    }

    private void iniciarAnalytics() {
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString("iferrerf", "Aplicacion Iniciada");
        firebaseAnalytics.logEvent("iferrerf", bundle);
    }


    private void iniciarAuthentication() {
        auth = FirebaseAuth.getInstance();

        btnRegistrar.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegistrarActivity.class);
            startActivity(intent);
        });
        btnLogin.setOnClickListener(view -> loguearConEmailyPassword());
        btnLoginGoogle.setOnClickListener(view -> loguearConGoogle());
    }

    private void loguearConGoogle() {
        // Al hacer click en el boton de login con Google:
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Nos creamos el GoogleSignIn Client
        googleSignInClient = getClient(LoginActivity.this, gso);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,100);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100){
            // Esto significa que venimos de loguearnos con Google
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                // La autenticacion con Google ha sido exitosa
                Log.w("FIREBASE", "firebasAuthConGoogle: "+ account.getId());
                firebaseAuthConGoogle(account.getIdToken());
            } catch (ApiException e){
                // La autenticacion con Google ha fallado
                Log.w("FIREBASE","Google SignIn ha fallado",e);
            }
        }

    }

    private void firebaseAuthConGoogle (String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            // Voy a HomeActivity
                            iraHomeActivity(user.getEmail());
                        } else {
                            Log.w("FIREBASE", "Logueo con Google: Fallo");
                        }
                    }
                });
    }


    private void loguearConEmailyPassword() {
        String _email = etEmail.getText().toString();
        String _password = etPassword.getText().toString();

        auth.signInWithEmailAndPassword(_email, _password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("FIREBASE-iferrerf", "signInUserWithEmail:success");
                    FirebaseUser user = auth.getCurrentUser();
                    iraHomeActivity(_email);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("FIREBASE-iferrerf", "signInUserWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Error al loguear usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void iraHomeActivity(String email) {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("email", email);
        startActivity(i);
    }




}













