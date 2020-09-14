package com.example.michele.votazione.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.michele.votazione.R;
import com.example.michele.votazione.adapters.AdapterListConcorsiOrganizzatore;
import com.example.michele.votazione.adapters.AdapterListConcorsiApertiOrganizzatore;
import com.example.michele.votazione.adapters.AdapterListConcorsiVotazioneOrganizzatore;
import com.example.michele.votazione.adapters.AdapterListGiudici;
import com.example.michele.votazione.connessione.AvviaConcorsoSoloPubblicoAsync;
import com.example.michele.votazione.connessione.InserisciGiuriaAsync;
import com.example.michele.votazione.connessione.NonAvviatoConcorsoSoloPubblicoAsync;
import com.example.michele.votazione.connessione.VisualizzaConcorsiApertiOrganizzatoreAsync;
import com.example.michele.votazione.connessione.VisualizzaConcorsiOrganizzatoreAsync;
import com.example.michele.votazione.connessione.VisualizzaConcorsiVotazioneOrganizzatoreAsync;
import com.example.michele.votazione.entity.Concorsi;
import com.example.michele.votazione.entity.Concorso;
import com.example.michele.votazione.entity.Giudice;
import com.example.michele.votazione.entity.Giudici;
import com.example.michele.votazione.entity.Organizzatore;
import com.example.michele.votazione.entity.Progetto;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Michele on 03/03/2020.
 */

public class GestisciConcorsoFragment extends android.support.v4.app.Fragment {
    private static View view;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String resultGson;
    private Context context;
    private TextView testo1Txt;
    private ListView concorsiList;
    private TextView testo2Txt;
    private ListView concorsiVotazioneList;
    private TextView testo3Txt;
    private ListView concorsiApertiList;
    private TextView testo4Txt;
    private EditText emailEdit;
    private ListView giudiciList;
    private Button inserisciEmailButton;
    private Button gestioneGiuriaButton;
    private Button sceltaConcorsoButton;
    private Concorsi concorsi=null;
    private Giudici giudici=null;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gestisci_concorso, container, false);
        context = getActivity();
        sharedPreferences = context.getSharedPreferences("sessioneOrganizzatore", context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        findViewByid();
        generaListViewConcorsiOrganizzatore(container);
        generaListViewConcorsiAperti(container);
        generaListViewConcorsiVotazione(container);
        return view;
    }

    private void findViewByid(){
        testo1Txt= (TextView) view.findViewById(R.id.testo1);
        concorsiList = (ListView) view.findViewById(R.id.listaConcorsiOrganizzatore);
        testo2Txt= (TextView) view.findViewById(R.id.testo2);
        concorsiVotazioneList = (ListView) view.findViewById(R.id.listaConcorsiOrganizzatoreVotazione);
        testo3Txt= (TextView) view.findViewById(R.id.testo3);
        concorsiApertiList = (ListView) view.findViewById(R.id.listaConcorsiOrganizzatoreAperti);
        testo4Txt= (TextView) view.findViewById(R.id.testo4);
        emailEdit = (EditText) view.findViewById(R.id.email);
        giudiciList = (ListView) view.findViewById(R.id.listaGiudici);
        inserisciEmailButton = (Button) view.findViewById(R.id.conferma1);
        gestioneGiuriaButton = (Button) view.findViewById(R.id.gestisciGiuria);
        sceltaConcorsoButton = (Button) view.findViewById(R.id.apriConcorso);

    }

    private void generaListViewConcorsiOrganizzatore(final ViewGroup container){
        int numeroConcorsi;
        String[] nomeConcorsi;
        String username = sharedPreferences.getAll().get("username").toString();
        Organizzatore organizzatore = new Organizzatore(username);
        //stringa contenente l'oggetto Gson da inviare al server
        String visualizza = new Gson().toJson(organizzatore, Organizzatore.class);
        try {
            resultGson = new VisualizzaConcorsiOrganizzatoreAsync(container.getContext()).execute(visualizza).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        concorsi = new Gson().fromJson(resultGson, Concorsi.class);
        if(concorsi.getConcorsoList()!=null){
            numeroConcorsi = concorsi.getConcorsoList().size();
            nomeConcorsi = new String[numeroConcorsi];
            for (int i = 0; i < numeroConcorsi; i++) {   //ok
                nomeConcorsi[i] = concorsi.getConcorsoList().get(i).getNome();
            }
            concorsiList.setAdapter(new AdapterListConcorsiOrganizzatore(container.getContext(), nomeConcorsi));
            //ascoltatore lista
            concorsiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = parent.getPositionForView(view);
                    Concorso c;
                    c= concorsi.getConcorsoList().get(pos);
                    frame1Gone();
                    frame2Visible();
                    inserimentoGiuria();
                    sceltaConcorso(container, c);
                    gestioneGiuria(container,c);
                }
            }); //fine ascoltatore lista

        }
    }

    private void frame1Gone(){
        testo1Txt.setVisibility(view.GONE);
        testo2Txt.setVisibility(view.GONE);
        testo3Txt.setVisibility(view.GONE);
        concorsiList.setVisibility(view.GONE);
        concorsiApertiList.setVisibility(view.GONE);
        concorsiVotazioneList.setVisibility(view.GONE);
    }

    private void frame1Visibile(){
        testo1Txt.setVisibility(view.VISIBLE);
        testo2Txt.setVisibility(view.VISIBLE);
        testo3Txt.setVisibility(view.VISIBLE);
        concorsiList.setVisibility(view.VISIBLE);
        concorsiApertiList.setVisibility(view.VISIBLE);
        concorsiVotazioneList.setVisibility(view.VISIBLE);
    }


    private void frame2Visible(){
        testo4Txt.setVisibility(view.VISIBLE);
        emailEdit.setVisibility(view.VISIBLE);
        giudiciList.setVisibility(view.VISIBLE);
        inserisciEmailButton.setVisibility(view.VISIBLE);
        gestioneGiuriaButton.setVisibility(view.INVISIBLE);
        sceltaConcorsoButton.setVisibility(view.VISIBLE);
    }

    private void frame2Gone(){
        testo4Txt.setVisibility(view.GONE);
        emailEdit.setVisibility(view.GONE);
        giudiciList.setVisibility(view.GONE);
        inserisciEmailButton.setVisibility(view.GONE);
        gestioneGiuriaButton.setVisibility(view.GONE);
        sceltaConcorsoButton.setVisibility(view.GONE);
    }

    private void sceltaConcorso(final ViewGroup container, final Concorso c){
        sceltaConcorsoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.dialog, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setCancelable(false);   //freccia
                final Button  rispostaPositivaButton= (Button) dialogView.findViewById(R.id.rispostaPositiva);
                rispostaPositivaButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tipo="solo pubblico";
                        String tipologiaVotoGiuria="-2"; //giuria non ci sta
                        Progetto progetto = new Progetto(c.getId(),tipo,tipologiaVotoGiuria);
                        //stringa contenente l'oggetto Gson da inviare al server
                        String inserimento = new Gson().toJson(progetto, Progetto.class);
                        try {
                            resultGson = new AvviaConcorsoSoloPubblicoAsync(container.getContext()).execute(inserimento).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        alertDialog.dismiss();
                    }
                });

                final Button  rispostaNegativaButton= (Button) dialogView.findViewById(R.id.rispostaNegativa);
                rispostaNegativaButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tipo="solo pubblico";
                        String tipologiaVotoGiuria="-2"; //giuria non esiste
                        Progetto progetto = new Progetto(c.getId(),tipo,tipologiaVotoGiuria);
                        //stringa contenente l'oggetto Gson da inviare al server
                        String inserimento = new Gson().toJson(progetto, Progetto.class);
                        try {
                            resultGson = new NonAvviatoConcorsoSoloPubblicoAsync(container.getContext()).execute(inserimento).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
    }

    private void inserimentoGiuria(){
        final ArrayList<Giudice> List= new ArrayList<>();
        final int[] numeroGiudici = new int[1];
        final String[][] email = new String[1][1];
        inserisciEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sceltaConcorsoButton.setVisibility(View.GONE);
                testo4Txt.setText("Una volta inseriti tutti i componeti della giuria premi il pulsante 'GESTISCI GIURIA' per completare l'inserimento");
                Giudice g;
                if (isEmailValid(emailEdit.getText().toString())){
                    gestioneGiuriaButton.setVisibility(View.VISIBLE);
                    g = new Giudice(emailEdit.getText().toString());
                    List.add(g);
                    giudici = new Giudici(List);
                    numeroGiudici[0] = giudici.getGiudiceList().size();
                    email[0] = new String[numeroGiudici[0]];
                    for (int i = 0; i < numeroGiudici[0]; i++)
                        email[0][i] = giudici.getGiudiceList().get(i).getEmail();
                    giudiciList.setAdapter(new AdapterListGiudici(context, email[0]));
                }else{
                    Toast.makeText(context, "Indirizzo email non valido", Toast.LENGTH_SHORT).show();
                }
                emailEdit.setText("");
                chiudiVirtualKeyboard();
            }
        });
    }

    private void gestioneGiuria(final ViewGroup container, final Concorso c){
        gestioneGiuriaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String g="";
                for (int i = 0; i < giudici.getGiudiceList().size(); i++) {
                    if(g.equals(""))
                        g=giudici.getGiudiceList().get(i).getEmail();
                    else
                        g=g+"-"+giudici.getGiudiceList().get(i).getEmail();
                }
                Concorso concorso = new Concorso(c.getId(),g);
                //stringa contenente l'oggetto Gson da inviare al server
                String inserimento = new Gson().toJson(concorso, Concorso.class);
                try {
                    resultGson = new InserisciGiuriaAsync(container.getContext(),c.getId()).execute(inserimento).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private void generaListViewConcorsiVotazione(final ViewGroup container){
        int numeroConcorsiVotazione;
        String[] nomeConcorsiVotazione;
        String[] idConcorsiVotazione;
        String username = sharedPreferences.getAll().get("username").toString();
        Organizzatore organizzatore = new Organizzatore(username);
        //stringa contenente l'oggetto Gson da inviare al server
        String visualizza = new Gson().toJson(organizzatore, Organizzatore.class);
        Concorsi concorsiVotazione=null;
        try {
            resultGson = new VisualizzaConcorsiVotazioneOrganizzatoreAsync(container.getContext()).execute(visualizza).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        concorsiVotazione = new Gson().fromJson(resultGson, Concorsi.class);
        if(concorsiVotazione.getConcorsoList()!=null){
            numeroConcorsiVotazione = concorsiVotazione.getConcorsoList().size();
            nomeConcorsiVotazione = new String[numeroConcorsiVotazione];
            idConcorsiVotazione = new String[numeroConcorsiVotazione];
            for (int i = 0; i < numeroConcorsiVotazione; i++) {
                nomeConcorsiVotazione[i] = concorsiVotazione.getConcorsoList().get(i).getNome();
                idConcorsiVotazione[i] = concorsiVotazione.getConcorsoList().get(i).getId();
        }
            concorsiVotazioneList.setAdapter(new AdapterListConcorsiVotazioneOrganizzatore(container.getContext(), nomeConcorsiVotazione,idConcorsiVotazione));
        }

    }

    private void generaListViewConcorsiAperti(final ViewGroup container){
        int numeroConcorsiAperti;
        String[] nomeConcorsiAperti;
        String[] idConcorsiAperti;
        String username = sharedPreferences.getAll().get("username").toString();
        Organizzatore organizzatore = new Organizzatore(username);
        //stringa contenente l'oggetto Gson da inviare al server
        String visualizza = new Gson().toJson(organizzatore, Organizzatore.class);
        Concorsi concorsiAperti=null;
        try {
            resultGson = new VisualizzaConcorsiApertiOrganizzatoreAsync(container.getContext()).execute(visualizza).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        concorsiAperti = new Gson().fromJson(resultGson, Concorsi.class);
        if(concorsiAperti.getConcorsoList()!=null){
            numeroConcorsiAperti = concorsiAperti.getConcorsoList().size();
            nomeConcorsiAperti = new String[numeroConcorsiAperti];
            idConcorsiAperti = new String[numeroConcorsiAperti];
            for (int i = 0; i < numeroConcorsiAperti; i++) {
                nomeConcorsiAperti[i] = concorsiAperti.getConcorsoList().get(i).getNome();
                idConcorsiAperti[i] = concorsiAperti.getConcorsoList().get(i).getId();
            }
            concorsiApertiList.setAdapter(new AdapterListConcorsiApertiOrganizzatore(container.getContext(), nomeConcorsiAperti, idConcorsiAperti));
        }

    }

    private void chiudiVirtualKeyboard(){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if(matcher.matches())
            return true;
        else
            return false;
    }
}