package com.example.michele.votazione.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michele.votazione.GestioneGiuria;
import com.example.michele.votazione.HomeOrganizzatore;
import com.example.michele.votazione.MainActivity;
import com.example.michele.votazione.R;
import com.example.michele.votazione.VotazioneGiuria;
import com.example.michele.votazione.VotazioneUtente;
import com.example.michele.votazione.connessione.AvviaConcorsoAsync;
import com.example.michele.votazione.connessione.ControllaUtente2Async;
import com.example.michele.votazione.connessione.ControllaUtenteAsync;
import com.example.michele.votazione.connessione.InserimentoVotazioneAsync;
import com.example.michele.votazione.connessione.NonAvviaConcorsoAsync;
import com.example.michele.votazione.connessione.VisualizzaProgettiAsync;
import com.example.michele.votazione.entity.Progetti;
import com.example.michele.votazione.entity.Progetto;
import com.example.michele.votazione.entity.Utente;
import com.example.michele.votazione.entity.Votazione;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

/**
 * Created by Michele on 31/03/2020.
 */

public class AdapterListConcorsiAperti extends ArrayAdapter {
    private Context context;
    private String resultGson;
    private String[] nomeConcorso;
    private String[] descrizioneConcorso;
    private String[] tipoVotazioneConcorso;
    private String[] idConcorso;

    private int numeroProgetti;
    private String[] nomeProgetto;

    private static final int CODICE = 101;
    private String IMEINumber;

    public AdapterListConcorsiAperti(Context context, String[] nomeConcorso, String[] descrizioneConcorso,String[] tipoVotazioneConcorso, String[] idConcorso) {
        super(context, R.layout.custom_list_concorsi_aperti, nomeConcorso);
        this.context = context;
        this.nomeConcorso = nomeConcorso;
        this.descrizioneConcorso = descrizioneConcorso;
        this.tipoVotazioneConcorso = tipoVotazioneConcorso;
        this.idConcorso = idConcorso;
    }

    @Override
    public View getView(final int position, View convertView,ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_list_concorsi_aperti, parent, false);

        /*if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)==PackageManager.PERMISSION_GRANTED)
            Toast.makeText(context, "mocc a mammt", Toast.LENGTH_LONG).show();*/

        TextView nome = (TextView) rowView.findViewById(R.id.nome);
        nome.setText(nomeConcorso[position]);
        TextView descrizione = (TextView) rowView.findViewById(R.id.descrizione);
        descrizione.setText(descrizioneConcorso[position]);
        TextView tipoVotazione = (TextView) rowView.findViewById(R.id.tipoVotazione);
        Button votazionePubbico = (Button) rowView.findViewById(R.id.button2);
        Button votazioneGiuria = (Button) rowView.findViewById(R.id.button3);
        if(!tipoVotazioneConcorso[position].equals("solo giuria")&&!tipoVotazioneConcorso[position].equals("solo pubblico"))
                                                                                   tipoVotazione.setText("giuria e pubblico");
        else {
            tipoVotazione.setText(tipoVotazioneConcorso[position]);
            if (tipoVotazioneConcorso[position].equals("solo giuria"))
                votazionePubbico.setVisibility(rowView.GONE);
            else
                votazioneGiuria.setVisibility(rowView.GONE);
        }



        Button visualizzaProgetti = (Button) rowView.findViewById(R.id.button1);
        visualizzaProgetti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Progetto p=new Progetto(idConcorso[position]);

                String progetto = new Gson().toJson(p, Progetto.class);

                Progetti progetti=null;
                try {
                    resultGson = new VisualizzaProgettiAsync(context).execute(progetto).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                progetti = new Gson().fromJson(resultGson, Progetti.class);





                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.dialog_visualizza_progetti, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                TextView  testo1= (TextView) dialogView.findViewById(R.id.testo1);
                testo1.setText(testo1.getText()+" "+nomeConcorso[position]);
                ListView listaProgetti= (ListView)dialogView.findViewById(R.id.listaProgetti);

                numeroProgetti = progetti.getProgettoList().size();
                nomeProgetto= new String[numeroProgetti];


                for(int i = 0; i < numeroProgetti; i++)
                    nomeProgetto[i] = progetti.getProgettoList().get(i).getNome();
                listaProgetti.setAdapter(new AdapterListProgettiDialog(getContext(), nomeProgetto));

                Button  chiudiButton= (Button) dialogView.findViewById(R.id.button1);
                chiudiButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();



            }
        });


        votazionePubbico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View dialogView = inflater.inflate(R.layout.dialog, null);
                    dialogBuilder.setView(dialogView);
                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.setCancelable(false);   //freccia indietro non scompare nn so se metterlo
                    final Button rispostaPositivaButton = (Button) dialogView.findViewById(R.id.rispostaPositiva);
                    rispostaPositivaButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        /*permessi per leggere l'imei del cellulare*/
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, CODICE);
                            }
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    IMEINumber = telephonyManager.getDeviceId();
                    String s = idConcorso[position];
                    Utente u=new Utente(IMEINumber,s);
                    String utente = new Gson().toJson(u, Utente.class);
                    try {
                      resultGson = new ControllaUtenteAsync(context,s,IMEINumber).execute(utente).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });




        votazioneGiuria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View dialogView = inflater.inflate(R.layout.dialog_permessi, null);
                    dialogBuilder.setView(dialogView);
                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.setCancelable(false);   //freccia indietro non scompare nn so se metterlo
                    final Button rispostaPositivaButton = (Button) dialogView.findViewById(R.id.rispostaPositiva);
                    rispostaPositivaButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                             /*permessi per leggere l'imei del cellulare*/
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, CODICE);
                            }
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    IMEINumber = telephonyManager.getDeviceId();
                    String s = idConcorso[position];
                    Utente u=new Utente(IMEINumber,s);
                    String utente = new Gson().toJson(u, Utente.class);
                    try {
                        resultGson = new ControllaUtente2Async(context,s,IMEINumber).execute(utente).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return rowView;
    }

}

