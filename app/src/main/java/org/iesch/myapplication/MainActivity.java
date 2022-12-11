package org.iesch.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.iesch.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    TextView tvEmail;

    private AppBarConfiguration mAppBarConfiguration;
    private AppBarConfiguration mAppBarConfiguration2;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        // Referencio los textView con los que tengo creados
         DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_ranking, R.id.nav_location, R.id.nav_pistas)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        // Accedemos al item salir del menu de navegacion
        navigationView.getMenu().getItem(4).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            // Al hacer click en el menu item salir, cerramos sesion
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                cerrarSesion();
                return true;
            }
        });
    }


    private void cerrarSesion() {
        // Cargamos las shared preferences que teniamos guardadas al volver al login
        SharedPreferences sesion = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_Editor = sesion.edit();
        Obj_Editor.clear();
        Obj_Editor.apply();

        // Cerramos la sesión en Firebase
        FirebaseAuth.getInstance().signOut();
        // Volver a la página anterior. Lo mismo que boton de atrás
        // En este caso llamamos al método dos veces para que si o si nos vuelva al loggin al hacer click
        onBackPressed();
        onBackPressed();
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}