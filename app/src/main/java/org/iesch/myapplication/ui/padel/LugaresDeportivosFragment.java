package org.iesch.myapplication.ui.padel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.iesch.myapplication.R;
import org.iesch.myapplication.databinding.FragmentLugaresDeportivosBinding;
import org.iesch.myapplication.ui.location.Lugar;

import java.util.ArrayList;


public class LugaresDeportivosFragment extends Fragment {

    private FragmentLugaresDeportivosBinding binding;

    // Creo los elementos que necesito
    private ListView lvLugares;
    private ListaLugaresAdapter adaptador;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LugaresDeportivosFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LugaresDeportivosFragment newInstance(String param1, String param2) {
        LugaresDeportivosFragment fragment = new LugaresDeportivosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLugaresDeportivosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Asigno el ListView del xml al que he creado aqui
        lvLugares = binding.lvLista;

        // Me creo el adaptador
        adaptador = new ListaLugaresAdapter(obtenerListaLugares(), getContext());

        // asigno el adaptador al ListView
        lvLugares.setAdapter(adaptador);

        return root;
    }

    private ArrayList<Lugar> obtenerListaLugares() {
        // Creo un arrayList de lugares
        ArrayList<Lugar> listaLugares = new ArrayList<>();

        // Meto a fuego cada lugar en el arrayList
        listaLugares.add(new Lugar("Pistas deportivas", "Mirambel", "8:00 - 23:00", "Todo el año", "Exterior", R.drawable.padelmirambel));
        listaLugares.add(new Lugar("Pista de tenis y fronton", "Cantavieja", "Sin horario establecido", "Todo el año", "Exterior",  R.drawable.tenis2));
        listaLugares.add(new Lugar("Pista Padel", "Iglesuela del Cid", "Sin horario establecido", "Todo el año", "Exterior",  R.drawable.pistaiglesuela));
        listaLugares.add(new Lugar("Polideportivo", "Cantavieja", "Sin horario establecido", "Todo el año", "Interior",  R.drawable.polideportivocantavieja));
        listaLugares.add(new Lugar("Polideportivo", "Iglesuela del Cid", "Sin horario establecido", "Todo el año", "Interior",  R.drawable.polideportivoiglesuela));
        listaLugares.add(new Lugar("Piscina municipal", "Mirambel", "10:00 - 21:00", "Temporada verano", "Exterior", R.drawable.piscina1));
        listaLugares.add(new Lugar("Piscina municipal", "Cantavieja", "11:00 - 20:00", "Temporada verano", "Exterior", R.drawable.piscina4));
        listaLugares.add(new Lugar("Piscina municipal", "Iglesuela del Cid", "11:00 - 21:00", "Temporada verano", "Exterior",  R.drawable.piscina3));

        return listaLugares;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}