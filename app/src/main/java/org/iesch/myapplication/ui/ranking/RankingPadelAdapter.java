package org.iesch.myapplication.ui.ranking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.iesch.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class RankingPadelAdapter extends RecyclerView.Adapter<RankingPadelAdapter.ViewHolder> {

    // Declaramos los elementos necesarios
    private List<Ranking> rankingList;
    private Context context;

    // Constructor donde definimos el contexto y una nueva lista de ranking
    public RankingPadelAdapter(Context context) {
        this.context = context;
        this.rankingList = new ArrayList<>();
    }

    // Metodos necesarios al extender de RecyclerView.Adapter
    @NonNull
    @Override
    public RankingPadelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.padel_ranking_item, parent, false);
        return new ViewHolder(view);
    }

    // Este método se ejecuta por cada objeto que devuelve la API
    @Override
    public void onBindViewHolder(@NonNull RankingPadelAdapter.ViewHolder holder, int position) {
        // Asignamos al objeto ranking la posición del elemento dentro del conjunto de datos del adaptador.
        Ranking ranking = rankingList.get(position);


        // Asignamos los datos al objeto
        holder.nombreTextView.setText(ranking.getName());
        holder.apellidoTextView.setText(ranking.getSurname());
        holder.rankingTextView.setText(ranking.getRanking());

        // Hacemos uso de la librería Glide para rescatar las imagenes de la API
        Glide.with(context)
                .load(ranking.getImageUrl())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

        Glide.with(context)
                .load(ranking.getNationalityUrl())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.nacionalidadImageView);

    }

    @Override
    public int getItemCount() {
        return rankingList.size();
    }

    public void adicionarRanking(List<Ranking> listaRanking) {
        this.rankingList.addAll(listaRanking);
        // Actualizamos el recyclerView en la pantalla
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        // Creamos los elementos de la interfaz
        private ImageView imageView;
        private ImageView nacionalidadImageView;
        private TextView nombreTextView;
        private TextView apellidoTextView;
        private TextView rankingTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Relacionamos cada elemento de la interfaz
            imageView = itemView.findViewById(R.id.padel_ranking_item_image_ImageView);
            nombreTextView = itemView.findViewById(R.id.padel_ranking_item_name_TexView);
            apellidoTextView = itemView.findViewById(R.id.padel_ranking_item_surname_TexView);
            nacionalidadImageView = itemView.findViewById(R.id.padel_ranking_item_nationality_ImageView);
            rankingTextView = itemView.findViewById(R.id.padel_ranking_item_ranking_TexView);

        }
    }
}
