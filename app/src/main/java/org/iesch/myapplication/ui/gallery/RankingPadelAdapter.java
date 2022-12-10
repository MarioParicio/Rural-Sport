package org.iesch.myapplication.ui.gallery;

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

    private List<Ranking> rankingList;
    private Context context;

    public RankingPadelAdapter(Context context) {
        this.context = context;
        this.rankingList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RankingPadelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.padel_ranking_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingPadelAdapter.ViewHolder holder, int position) {
        Ranking ranking = rankingList.get(position);

        holder.nombreTextView.setText(ranking.getName());
        holder.apellidoTextView.setText(ranking.getSurname());
        holder.rankingTextView.setText(ranking.getRanking());

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
        // Con este metodo actualizaremos el recyclerView en la pantalla
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private ImageView nacionalidadImageView;
        private TextView nombreTextView;
        private TextView apellidoTextView;
        private TextView rankingTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.padel_ranking_item_image_ImageView);
            nombreTextView = itemView.findViewById(R.id.padel_ranking_item_name_TexView);
            apellidoTextView = itemView.findViewById(R.id.padel_ranking_item_surname_TexView);
            nacionalidadImageView = itemView.findViewById(R.id.padel_ranking_item_nationality_ImageView);
            rankingTextView = itemView.findViewById(R.id.padel_ranking_item_ranking_TexView);

        }
    }
}
