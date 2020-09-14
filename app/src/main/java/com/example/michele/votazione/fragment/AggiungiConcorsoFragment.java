package com.example.michele.votazione.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.michele.votazione.R;
import com.example.michele.votazione.adapters.AdapterListProgetti;
import com.example.michele.votazione.connessione.InserisciConcorso1Async;
import com.example.michele.votazione.entity.Concorso;
import com.example.michele.votazione.entity.Progetti;
import com.example.michele.votazione.entity.Progetto;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Michele on 03/03/2020.
 */

public class AggiungiConcorsoFragment extends android.support.v4.app.Fragment {
    private static View view;
    private Context context;
    EditText nomeEdit;
    EditText descrizioneEdit;
    EditText progettoEdit;
    ListView progetti_list;
    Button inserisciButton;
    Button creaButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aggiungi_concorso, container, false);
        context = getActivity();
        findViewByid();
        sharedPreferences = context.getSharedPreferences("sessioneOrganizzatore", context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        final ArrayList<Progetto> List= new ArrayList<>();
        final Progetti[] progetti = new Progetti[1];
        final int[] numeroProgetti = new int[1];
        final String[][] nomeProgetti = new String[1][1];
        inserisciButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Progetto p;
                p = new Progetto(progettoEdit.getText().toString());
                progettoEdit.setText("");
                chiudiVirtualKeyboard();
                List.add(p);
                progetti[0] = new Progetti(List);
                numeroProgetti[0] = progetti[0].getProgettoList().size();
                nomeProgetti[0] = new String[numeroProgetti[0]];
                for (int i = 0; i < numeroProgetti[0]; i++) {
                    nomeProgetti[0][i] = progetti[0].getProgettoList().get(i).getNome();
                }
                progetti_list.setAdapter(new AdapterListProgetti(context, nomeProgetti[0]));
            }
        });

        creaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = sharedPreferences.getAll().get("username").toString();
                String p="";
                for (int i = 0; i < numeroProgetti[0]; i++) {
                    if(p.equals(""))
                           p=progetti[0].getProgettoList().get(i).getNome();
                    else
                        p=p+"-"+progetti[0].getProgettoList().get(i).getNome();
                }
                Concorso concorso=new Concorso(nomeEdit.getText().toString(),descrizioneEdit.getText().toString(),username,p);
                String insersciConcorso= new Gson().toJson(concorso,Concorso.class);
                new InserisciConcorso1Async(context).execute(insersciConcorso);
            }
        });
        return view;
    }

    private void findViewByid(){
        nomeEdit = (EditText) view.findViewById(R.id.nome);
        descrizioneEdit = (EditText) view.findViewById(R.id.descrizione);
        progettoEdit = (EditText) view.findViewById(R.id.progetto);
        progetti_list = (ListView) view.findViewById(R.id.listaGiudici);
        inserisciButton = (Button) view.findViewById(R.id.conferma1);
        creaButton = (Button) view.findViewById(R.id.invia);
    }

    private void chiudiVirtualKeyboard(){
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}
