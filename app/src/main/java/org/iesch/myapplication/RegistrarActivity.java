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

    // Elemento de firebase para crear usuarios
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);


        // Referenciamos los textView creados con los que tenemos de la interfaz
        etEmail = findViewById(R.id.EmailEditTextRegistrar);
        etPassword = findViewById(R.id.ContrasenaEditTextRegistrar);
        btnRegistrar = findViewById(R.id.RegistrarRegistrarButton);

        // Instanciamos el FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Registramos el usuario al hacer clic en el boton
        btnRegistrar.setOnClickListener(view -> registrarConEmailPassword());

    }

    private void registrarConEmailPassword() {
        // Guardamos en strings los valores de los campos de texto
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        // Si los campos no están vacíos
        if (!email.isEmpty() && !password.isEmpty()) {

            // Llamo al metodo de crear usuario con email y contraseña
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    // Si el resultado es satisfactorio, informamos al usuario y vamos a la pantalla de login
                    if (task.isSuccessful()) {
                        Log.d("FIREBASE-iferrerf", "createUserWithEmail:success");
                        Toast.makeText(RegistrarActivity.this, "El usuario ha sido creado correctamente mediante FirebaseLogin", Toast.LENGTH_SHORT).show();
                        iraLoginActivity(email, password);
                    } else {
                        // Si no es satisfactorio, informamos al usuario
                        Log.w("FIREBASE-iferrerf", "createUserWithEmail:failure");
                        Toast.makeText(RegistrarActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else{
            Toast.makeText(RegistrarActivity.this, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show();
        }
    }

    // Metodo que crea un activity Login nuevo al que le pasamos el email y contraseña
    private void iraLoginActivity(String email, String password) {
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra("email", email);
        i.putExtra("contraseña", password);
        startActivity(i);
    }


}