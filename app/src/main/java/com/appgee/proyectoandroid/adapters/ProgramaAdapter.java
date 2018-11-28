package com.appgee.proyectoandroid.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.appgee.proyectoandroid.R;
import com.appgee.proyectoandroid.activities.PonenciaDetalleActivity;
import com.appgee.proyectoandroid.activities.PonenciaEvaluacionActivity;
import com.appgee.proyectoandroid.models.Ponencia;

import java.util.List;

public class ProgramaAdapter extends RecyclerView.Adapter<ProgramaAdapter.PonenciaViewHolder> implements View.OnClickListener {
    private List<Ponencia> ponencias;
    private RecyclerView recyclerView;

    public ProgramaAdapter(List<Ponencia> ponencias) {
        this.ponencias = ponencias;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public PonenciaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.ponencia, viewGroup, false);
        view.setOnClickListener(this);

        return new PonenciaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PonenciaViewHolder viewHolder, final int i) {
        final Ponencia ponencia = ponencias.get(i);

        viewHolder.tvTitulo.setText(ponencia.getTitulo());
        viewHolder.tvNombrePonente.setText(ponencia.getNombrePonente());
        viewHolder.tvFecha.setText(ponencia.getFecha());
        viewHolder.tvHora.setText(ponencia.getHora());
        viewHolder.tvLugar.setText(ponencia.getLugar());
        viewHolder.tvModalidad.setText(ponencia.getModalidad());

        viewHolder.btnPonenciaCalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), PonenciaEvaluacionActivity.class);

            Ponencia ponencia = ponencias.get(i);
            intent.putExtra("ponencia", ponencia);
            view.getContext().startActivity(intent);
            }
        });
        
        viewHolder.btnPonenciaAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Toast.makeText(view.getContext(), "This is just a demo!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return ponencias.size();
    }

    @Override
    public void onClick(View view) {
        int posicion = recyclerView.getChildLayoutPosition(view);
        Ponencia ponencia = ponencias.get(posicion);

        Intent intent = new Intent(view.getContext(), PonenciaDetalleActivity.class);
        intent.putExtra("ponencia", ponencia);
        view.getContext().startActivity(intent);
    }

    class PonenciaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo;
        TextView tvNombrePonente;
        TextView tvFecha;
        TextView tvHora;
        TextView tvLugar;
        TextView tvModalidad;
        Button btnPonenciaAgendar;
        Button btnPonenciaCalificar;

        public PonenciaViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitulo = itemView.findViewById(R.id.tvPonenciaTitulo);
            tvNombrePonente = itemView.findViewById(R.id.tvPonenciaNombrePonente);
            tvFecha = itemView.findViewById(R.id.tvFechaPonencia);
            tvHora = itemView.findViewById(R.id.tvHoraPonencia);
            tvLugar = itemView.findViewById(R.id.tvLugarPonencia);
            tvModalidad = itemView.findViewById(R.id.tvPonenciaModalidad);
            btnPonenciaAgendar = itemView.findViewById(R.id.botonPonenciaAgendar);
            btnPonenciaCalificar = itemView.findViewById(R.id.botonPonenciaCalificar);
        }
    }
}
