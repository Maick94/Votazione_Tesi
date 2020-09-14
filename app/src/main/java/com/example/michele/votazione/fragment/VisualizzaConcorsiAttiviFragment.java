package com.example.michele.votazione.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.michele.votazione.R;
import com.example.michele.votazione.adapters.AdapterListConcorsiAperti;
import com.example.michele.votazione.connessione.VisualizzaConcorsiAttiviAsync;
import com.example.michele.votazione.entity.Concorsi;
import com.example.michele.votazione.entity.Concorso;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

/**
 * Created by Michele on 03/03/2020.
 */

public class VisualizzaConcorsiAttiviFragment extends android.support.v4.app.Fragment {
    private static View view;
    private String resultGson;
    private ListView concorsi_list;
    private int numeroConcorsi;
    private String[] nomeConcorso;
    private String[] descrizioneConcorso;
    private String[] tipoVotazioneConcorso;
    private String[] idConcorso;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_visualizza_concorsi_attivi, container, false);
        concorsi_list = (ListView) view.findViewById(R.id.listaConcorsiAttivi);
        Concorsi concorsi=null;
        try {
            resultGson = new VisualizzaConcorsiAttiviAsync(container.getContext()).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        concorsi = new Gson().fromJson(resultGson, Concorsi.class);
        if(concorsi.getConcorsoList()!=null){
            numeroConcorsi = concorsi.getConcorsoList().size();
            nomeConcorso= new String[numeroConcorsi];
            descrizioneConcorso= new String[numeroConcorsi];
            tipoVotazioneConcorso= new String[numeroConcorsi];
            idConcorso= new String[numeroConcorsi];
            for(int i = 0; i < numeroConcorsi; i++) {
                nomeConcorso[i] = concorsi.getConcorsoList().get(i).getNome();
                descrizioneConcorso[i] = concorsi.getConcorsoList().get(i).getDescrizione();
                tipoVotazioneConcorso[i] = concorsi.getConcorsoList().get(i).getTipoVotazione();
                idConcorso[i] = concorsi.getConcorsoList().get(i).getId();

            }
            concorsi_list.setAdapter(new AdapterListConcorsiAperti(container.getContext(), nomeConcorso, descrizioneConcorso, tipoVotazioneConcorso, idConcorso));
        }












        return view;
    }
}
