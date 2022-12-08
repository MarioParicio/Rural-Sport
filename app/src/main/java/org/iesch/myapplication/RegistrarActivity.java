package org.iesch.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistrarActivity extends AppCompatActivity {

    TextView etEmail, etPassword;

    Button btnRegistrar;


    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        etEmail = findViewById(R.id.EmailEditTextRegistrar);
        etPassword = findViewById(R.id.ContrasenaEditTextRegistrar);
        btnRegistrar = findViewById(R.id.RegistrarRegistrarButton);

        auth = FirebaseAuth.getInstance();

        btnRegistrar.setOnClickListener(view -> registrarConEmailPassword());

    }


    private void registrarConEmailPassword() {

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d("FIREBASE-iferrerf", "createUserWithEmail:success");
                        Toast.makeText(RegistrarActivity.this, "El usuario ha sido creado correctamente mediante FirebaseLogin", Toast.LENGTH_SHORT).show();
                        iraLoginActivity(email, password);
                    } else {
                        Log.w("FIREBASE-iferrerf", "createUserWithEmail:failure");
                        Toast.makeText(RegistrarActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void iraLoginActivity(String email, String password) {
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra("email", email);
        i.putExtra("contraseña", password);
        startActivity(i);
    }


}