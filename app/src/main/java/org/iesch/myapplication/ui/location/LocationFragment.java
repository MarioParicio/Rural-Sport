package org.iesch.myapplication.ui.location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.iesch.myapplication.R;

public class LocationFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            // Especificamos la latitud y longitud que nos mostrará el mapa como el punto central del mapa
            LatLng zoom = new LatLng(40.547240, -0.348118);
            // Especificamos la cantidad de zoom que hace referencia al punto del mapa señalado anteriormente
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zoom,11.3f));

            // Creamos nuevas localizaciones en el mapa
            LatLng piscinaMirambel = new LatLng(40.591093, -0.341594);
            LatLng pistasMirambel = new LatLng(40.591989, -0.342386);
            LatLng pabellonCantavieja = new LatLng(40.52532706763187, -0.4047808490225969);
            LatLng piscinaCantavieja = new LatLng(40.522967609070136, -0.40470706110145194);
            LatLng frontonCantavieja = new LatLng(40.523244, -0.404215);
            LatLng piscinaIglesuela = new LatLng(40.48186855251597, -0.3233418361936311);
            LatLng pabellonIglesuela = new LatLng(40.48157221456962, -0.32274539967352234);


            // Asignamos difrerentes marcadores en  las posiciones que hemos definido anteriormente y le damos un titulo a cada una
            googleMap.addMarker(new MarkerOptions().position(piscinaMirambel).title("Piscina Municipal"));
            googleMap.addMarker(new MarkerOptions().position(pabellonCantavieja).title("Pabellón Polideportivo"));
            googleMap.addMarker(new MarkerOptions().position(piscinaCantavieja).title("Piscina Municipal"));
            googleMap.addMarker(new MarkerOptions().position(pistasMirambel).title("Pistas Polideportivas"));
            googleMap.addMarker(new MarkerOptions().position(frontonCantavieja).title("Pistas de frontón y tenis"));
            googleMap.addMarker(new MarkerOptions().position(piscinaIglesuela).title("Piscina Municipal"));
            googleMap.addMarker(new MarkerOptions().position(pabellonIglesuela).title("Pabellon Polideportivo"));

        }

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}