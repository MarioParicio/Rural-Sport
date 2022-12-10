package org.iesch.myapplication.ui.gallery;

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

    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private RankingPadelAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRankingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.rankingRecyclerView;
        adapter = new RankingPadelAdapter(getContext());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://6394b46b86829c49e824fc4b.mockapi.io/api/padel/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        obtenerDatos();

        return root;
    }

    private void obtenerDatos() {
        RankingApiService service = retrofit.create(RankingApiService.class);

        Call<List<Ranking>> rankingRespuestaCall = service.obtenerListaRanking();

        rankingRespuestaCall.enqueue(new Callback<List<Ranking>>() {
            @Override
            public void onResponse(Call<List<Ranking>> call, Response<List<Ranking>> response) {
                if (response.isSuccessful()){
                    // Cuando recibimos los datos ya no los mostramos por consola
                    // se los mandamos al adaptador por medio de un metodo
                    adapter.adicionarRanking(response.body());

                }else{
                    Log.i("RANKING", "onResponse: "+response.errorBody());
                }
            }

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