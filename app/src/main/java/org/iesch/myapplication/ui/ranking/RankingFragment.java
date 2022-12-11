package org.iesch.myapplication.ui.ranking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.iesch.myapplication.databinding.FragmentRankingBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RankingFragment extends Fragment {

    private FragmentRankingBinding binding;

    // Creamos los elementos necesarios
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private RankingPadelAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRankingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Referenciamos y damos valor a cada elemento creado
        recyclerView = binding.rankingRecyclerView;
        adapter = new RankingPadelAdapter(getContext());
        recyclerView.setAdapter(adapter);

        // Implementacion de recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Utilizamos la librería retrofit para cargar los datos de la API
        // Creamos un builder, le pasamos la url base y utilizamos Gson para trabajar el Json
        retrofit = new Retrofit.Builder()
                .baseUrl("https://6394b46b86829c49e824fc4b.mockapi.io/api/padel/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        // Llamada al metodo que se encarga de realizar las peticiones al servidor y validar las respuestas
        obtenerDatos();

        return root;
    }

    private void obtenerDatos() {
        // Declaramos la interfaz
        RankingApiService service = retrofit.create(RankingApiService.class);

        // Llamada al metodo que recibe las respuestas de la APIService
        Call<List<Ranking>> rankingRespuestaCall = service.obtenerListaRanking();

        rankingRespuestaCall.enqueue(new Callback<List<Ranking>>() {
            @Override
            public void onResponse(Call<List<Ranking>> call, Response<List<Ranking>> response) {
                if (response.isSuccessful()){
                    // Cuando recibimos los datos se los mandamos al adaptador por medio de un metodo
                    adapter.adicionarRanking(response.body());

                }else{
                    Log.i("RANKING", "onResponse: "+response.errorBody());
                }
            }

            // En caso de que falle, escribimos el error en el log
            @Override
            public void onFailure(Call<List<Ranking>> call, Throwable t) {
                Log.i("RANKING", "onFailure: "+t.getMessage());
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}