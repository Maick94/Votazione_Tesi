package com.example.michele.votazione.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.michele.votazione.R;
import com.example.michele.votazione.connessione.AvviaConcorso2Async;
import com.example.michele.votazione.connessione.ChiudiConcorsoAsync;
import com.example.michele.votazione.entity.Concorso;
import com.example.michele.votazione.entity.Votazioni;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

/**
 * Created by Michele on 30/03/2020.
 */

public class AdapterListConcorsiVotazioneOrganizzatore extends ArrayAdapter {
    private  Context context;
    private  String[] nomeConcorso;
    private  String[] idConcorso;
    private String resultGson;



    public AdapterListConcorsiVotazioneOrganizzatore(Context context, String[] nomeConcorso, String[] idConcorso) {
        super(context, R.layout.custom_list_concorsi_votazione_organizzatore, nomeConcorso);  //questo e importente nome pietaza altrimenti non esce la lista
        this.context = context;
        this.nomeConcorso = nomeConcorso;
        this.idConcorso=idConcorso;
    }

    @Override
    public View getView(final int position, View convertView,  ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_list_concorsi_votazione_organizzatore, parent, false);
        TextView nome = (TextView) rowView.findViewById(R.id.textViewName);
        nome.setText(nomeConcorso[position]);

        Button apriVotazione = (Button) rowView.findViewById(R.id.buttonApriVotazione);
        apriVotazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Concorso c=new Concorso(idConcorso[position]);

                //stringa contenente l'oggetto Gson da inviare al server
                String concorso = new Gson().toJson(c, Concorso.class);
                try {
                    resultGson = new AvviaConcorso2Async(context).execute(concorso).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
        });
        return rowView;
    }

}

