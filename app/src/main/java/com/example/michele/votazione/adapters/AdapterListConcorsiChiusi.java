package com.example.michele.votazione.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.michele.votazione.R;
import com.example.michele.votazione.VotazioneGiuria;
import com.example.michele.votazione.VotazioneUtente;
import com.example.michele.votazione.connessione.VisualizzaProgettiAsync;
import com.example.michele.votazione.entity.Progetti;
import com.example.michele.votazione.entity.Progetto;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

/**
 * Created by Michele on 09/04/2020.
 */

public class AdapterListConcorsiChiusi extends ArrayAdapter {
    private Context context;
    private String[] nomeConcorso;


    public AdapterListConcorsiChiusi(Context context, String[] nomeConcorso) {
        super(context, R.layout.custom_list_concorsi_aperti, nomeConcorso);
        this.context = context;
        this.nomeConcorso = nomeConcorso;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_list_concorsi_chiusi, parent, false);
        TextView nome = (TextView) rowView.findViewById(R.id.textViewName);
        nome.setText(nomeConcorso[position]);
        return rowView;
    }

}
