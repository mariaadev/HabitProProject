package com.example.habitproproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DiasAdapter extends RecyclerView.Adapter<DiasAdapter.DiaViewHolder> {

    private List<Dia> listaDias;

    public DiasAdapter(List<Dia> listaDias) {
        this.listaDias = listaDias;
    }

    @NonNull
    @Override
    public DiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dia, parent, false);
        return new DiaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaViewHolder holder, int position) {
        Dia dia = listaDias.get(position);
        holder.tvNombreAbreviado.setText(dia.getNombreAbreviado());
        holder.tvNumeroDia.setText(String.valueOf(dia.getNumeroDia()));
    }

    @Override
    public int getItemCount() {
        return listaDias.size();
    }

    static class DiaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreAbreviado, tvNumeroDia;

        public DiaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreAbreviado = itemView.findViewById(R.id.tvNombreAbreviado);
            tvNumeroDia = itemView.findViewById(R.id.tvNumeroDia);
        }
    }
}
