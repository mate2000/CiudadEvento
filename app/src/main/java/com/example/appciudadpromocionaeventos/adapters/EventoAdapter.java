package com.example.appciudadpromocionaeventos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appciudadpromocionaeventos.Models.Evento;
import com.example.appciudadpromocionaeventos.R;

import java.util.ArrayList;
import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.ViewHolder> {

    private int resource;
    private ArrayList<Evento> eventosList;

    public EventoAdapter (ArrayList<Evento> eventosList, int resource) {
        this.eventosList = eventosList;
        this.resource = resource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int index) {
        Evento evento = eventosList.get(index);
        holder.textViewEvento.setText(evento.getNombre());
    }

    @Override
    public int getItemCount() {
        return eventosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewEvento;
        public View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.textViewEvento = (TextView) view.findViewById(R.id.txtEventosSecretaria);
        }
    }
}
