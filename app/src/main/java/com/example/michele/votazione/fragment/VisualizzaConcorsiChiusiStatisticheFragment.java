package com.example.michele.votazione.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.michele.votazione.R;
import com.example.michele.votazione.RegistrazioneOrganizzatore;
import com.example.michele.votazione.VisualizzaGrafico;
import com.example.michele.votazione.adapters.AdapterListConcorsiAperti;
import com.example.michele.votazione.adapters.AdapterListConcorsiChiusi;
import com.example.michele.votazione.connessione.VisualizzaConcorsiAttiviAsync;
import com.example.michele.votazione.connessione.VisualizzaConcorsiChiusiAsync;
import com.example.michele.votazione.entity.Concorsi;
import com.example.michele.votazione.entity.Concorso;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

/**
 * Created by Michele on 03/03/2020.
 */

public class VisualizzaConcorsiChiusiStatisticheFragment extends android.support.v4.app.Fragment {
    private static View view;
    private String resultGson;
    private Context context;

    private ListView concorsi_list;

    private int numeroConcorsi;
    private String[] nomeConcorso;
    private String idConcorso;
    private Concorsi concorsi=null;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_visualizza_concorsi_chiusi_statistiche, container, false);

        context=getActivity();
        concorsi_list = (ListView) view.findViewById(R.id.listaConcorsiChiusi);

        try {
            resultGson = new VisualizzaConcorsiChiusiAsync(container.getContext()).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        concorsi = new Gson().fromJson(resultGson, Concorsi.class);
        if(concorsi.getConcorsoList()==null){
            /*non ci sono concorsi attivi */
            /*.....*/
        }else{
            numeroConcorsi = concorsi.getConcorsoList().size();
            nomeConcorso= new String[numeroConcorsi];


            for(int i = 0; i < numeroConcorsi; i++)
                nomeConcorso[i] = concorsi.getConcorsoList().get(i).getNome();
            concorsi_list.setAdapter(new AdapterListConcorsiChiusi(container.getContext(), nomeConcorso));
        }

        concorsi_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos = parent.getPositionForView(view);
                idConcorso = concorsi.getConcorsoList().get(pos).getId();
                Intent intent = new Intent(context, VisualizzaGrafico.class);
                intent.putExtra("IdConcorso", (Serializable) idConcorso);
                context.startActivity(intent);
            }
        }); //fine ascoltatore lista
        return view;
    }
}
