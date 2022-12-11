package org.iesch.myapplication;

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

        verSiEstaLogueado();

        // Referenciamos cada textView y button con su elemento correspondiente
        etEmail = findViewById(R.id.EmailEditText);
        etPassword = findViewById(R.id.ContrasenaEditText);
        btnRegistrar = findViewById(R.id.RegistrarButton);
        btnLogin = findViewById(R.id.AccederButton);
        btnLoginGoogle = findViewById(R.id.GoogleButton);

        // Cargamos los textView con la informacion de email y contraseña que nos llega del Registrar Activity
        etEmail.setText(getIntent().getStringExtra("email"));
        etPassword.setText(getIntent().getStringExtra("contraseña"));

        // Llamada a los métodos que realizan el proceso de autenticación
        iniciarAnalytics();

        iniciarAuthentication();


    }

    private void guardarInfoDeLogin(String email) {
        // Guardo los datos del usuario
        SharedPreferences sesion = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_Editor = sesion.edit();
        Obj_Editor.putString("email", email);
        Obj_Editor.apply();
        Obj_Editor.commit();
    }

    private void verSiEstaLogueado() {
        // Comprobamos si el usuario está logueado mediante las SharedPreferences
        SharedPreferences sesion = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        String _email = sesion.getString("email", null);

        // Si el email es distinto de nulo, vamos al HomeActivity
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
        // Instanciamos FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Realizamos distintas acciones dependiendo de que boton cliquemos
        btnRegistrar.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegistrarActivity.class);
            startActivity(intent);
        });
        btnLogin.setOnClickListener(view -> loguearConEmailyPassword());
        btnLoginGoogle.setOnClickListener(view -> loguearConGoogle());
    }

    private void loguearConGoogle() {

        // Recogemos la informacion del logueo con Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Nos creamos el GoogleSignIn Client
        googleSignInClient = getClient(LoginActivity.this, gso);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Si el codigo del cliente de google coincide
        if (requestCode == 100) {
            // Significa que venimos de loguearnos con Google
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // La autenticacion con Google ha sido exitosa
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.w("FIREBASE-iferrerf", "firebasAuthConGoogle: " + account.getId());
                firebaseAuthConGoogle(account.getIdToken());
            } catch (ApiException e) {
                // La autenticacion con Google ha fallado
                Log.w("FIREBASE-iferrerf", "Google SignIn ha fallado", e);
            }
        }

    }

    private void firebaseAuthConGoogle(String idToken) {
        // Credencial con la que Firebase autentica a un usuario
        // Le pasamos la credencial de google a traves del token
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Si la credencial es correcta, creamos el usuario en Firebase
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            // Guardamos el email del usuario
                            guardarInfoDeLogin(user.getEmail());
                            // Abrimos el activity home
                            iraHomeActivity(user.getEmail());
                        } else {
                            Log.w("FIREBASE-iferrerf", "Logueo con Google: Fallo");
                            Toast.makeText(LoginActivity.this, "Error al loguear usuario con Google", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void loguearConEmailyPassword() {
        // Guardamos en Strings el email y constraseña de los campos de texto
        String _email = etEmail.getText().toString();
        String _password = etPassword.getText().toString();

        // Logueo mediante email y password
        auth.signInWithEmailAndPassword(_email, _password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // Si Firebase contiene el email y constraseña asociados, será satisfactorio
                if (task.isSuccessful()) {
                    // Si el logueo es correcto, abrimos el activityHome y guardamos la informacion del usuario
                    Log.d("FIREBASE-iferrerf", "signInUserWithEmail:success");
                    guardarInfoDeLogin(_email);
                    iraHomeActivity(_email);
                } else {
                    // Si el logueo falla, informamos al usuario
                    Log.w("FIREBASE-iferrerf", "signInUserWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Error al loguear usuario", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void iraHomeActivity(String user) {
        // Metodo que crea un activityMain nuevo
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        Toast.makeText(LoginActivity.this, "Bienvenido " + "\b"+user, Toast.LENGTH_SHORT).show();
    }


}













