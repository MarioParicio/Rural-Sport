package org.iesch.myapplication.ui.padel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.iesch.myapplication.R;
import org.iesch.myapplication.ui.location.Lugar;

import java.util.ArrayList;

public class ListaLugaresAdapter extends BaseAdapter {

    // Creo los elementos que necesito
    private ArrayList<Lugar> listaLugares;
    private Context context;

    // Constructor al que le paso la lista de lugares y el contexto
    public ListaLugaresAdapter(ArrayList<Lugar> listaLugares, Context context) {
        this.listaLugares = listaLugares;
        this.context = context;
    }


    // Metodo necesarios al extender de BaseAdapter
    // Los modificamos para que sean aptos a nuestro programa
    @Override
    public int getCount() {
        return listaLugares.size();
    }

    @Override
    public Object getItem(int i) {
        return listaLugares.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        // Metodo más importante
        // Creo un objeto lugar cada vez que entra al método
        Lugar lugar = (Lugar) getItem(i);

        // Inflamos en el contexto el layout que tiene el formato de cada lugar
        view = LayoutInflater.from(context).inflate(R.layout.item_lugares, null);

        // Creamos los mismos elementos que en el layout y los enlazamos con su correspondiente
        ImageView imgLugar = (ImageView) view.findViewById(R.id.imgLugar_ImageView);
        TextView tvNombre = (TextView) view.findViewById(R.id.nombre_TextView);
        TextView tvLocalidad = (TextView) view.findViewById(R.id.localidad_TextView);
        TextView tvHorario = (TextView) view.findViewById(R.id.horario_TextView);
        TextView tvTipo = (TextView) view.findViewById(R.id.tipo_TextView);
        TextView tvAmbiente = (TextView) view.findViewById(R.id.ambiente_TextView);

        // Damos valor a cada elemento
        imgLugar.setImageResource(lugar.getImagenId());
        tvNombre.setText(lugar.getNombre());
        tvLocalidad.setText(lugar.getLocalidad());
        tvHorario.setText(lugar.getHorario());
        tvTipo.setText(lugar.getType());
        tvAmbiente.setText(lugar.getAmbiente());

        return view;
    }
}
