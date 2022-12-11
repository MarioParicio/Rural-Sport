package org.iesch.myapplication.ui.ranking;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RankingApiService {

    // Recogemos en el get el trozo de cadena que falta para completar la url
    @GET("padelRanking")

    // Invocación de un metodo de Retrofit que envía una solicitud a un servidor web y envía una respuesta
    Call<List<Ranking>> obtenerListaRanking();
}
