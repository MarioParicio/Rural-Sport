package org.iesch.myapplication.ui.gallery;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RankingApiService {

    @GET("padelRanking")
    Call<List<Ranking>> obtenerListaRanking();
}
