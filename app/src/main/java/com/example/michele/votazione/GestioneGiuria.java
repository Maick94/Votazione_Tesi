package com.example.michele.votazione;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.michele.votazione.adapters.AdapterListTipologia;
import com.example.michele.votazione.adapters.TipologiaModel;
import com.example.michele.votazione.connessione.AvviaConcorsoAsync;
import com.example.michele.votazione.connessione.AvviaConcorsoSoloPubblicoAsync;
import com.example.michele.votazione.connessione.LoginGiudiceAsync;
import com.example.michele.votazione.connessione.NonAvviaConcorsoAsync;
import com.example.michele.votazione.connessione.NonAvviatoConcorsoSoloPubblicoAsync;
import com.example.michele.votazione.entity.Giudice;
import com.example.michele.votazione.entity.Progetto;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GestioneGiuria extends AppCompatActivity {
    private Switch tipologiaVotazione;
    private Switch tipoVotazione;
    private TextView testoSeekBar;
    private SeekBar seekBar;
    private TableLayout tableLayout;
    private TextView testo1Txt;
    private TextView testo2Txt;
    private ListView tipologiaVotiList;
    private Button sceltaConcorsoButton;
    private ArrayList<String> temp=new ArrayList<String>();
    private String id;
    private String tipoTipologia="";
    private String tipo="";
    private ArrayList<Button> ListaButton=new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestione_giuria);
        id= (String) getIntent().getSerializableExtra("Id");
        findViewById();
        gestioneSwitchtipologiaVotazione();
        gestioneSwitchtipoVotazione();
        sceltaConcorso();
    }
    
    private void findViewById(){
        tipologiaVotazione = (Switch) findViewById(R.id.switch1);
        tipoVotazione = (Switch) findViewById(R.id.switch2);
        testoSeekBar = (TextView) findViewById(R.id.testoSeekBar);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        testo2Txt= (TextView) findViewById(R.id.testo3);
        testo1Txt= (TextView) findViewById(R.id.testo1);
        tipologiaVotiList= (ListView) findViewById(R.id.listaTipologiaVoti);
        sceltaConcorsoButton= (Button) findViewById(R.id.button1);
    }

    private void inserimentoTipologiaVoti(){
        final List<TipologiaModel> tipologia = new ArrayList<>();
        tipologia.add(new TipologiaModel(false, "Grafica"));
        tipologia.add(new TipologiaModel(false, "Interazione con l utente"));
        tipologia.add(new TipologiaModel(false, "Implementazione"));
        tipologia.add(new TipologiaModel(false, "Difficoltà"));
        tipologia.add(new TipologiaModel(false, "Idea"));
        tipologia.add(new TipologiaModel(false, "Pertinenza agli obiettivi"));
        tipologia.add(new TipologiaModel(false, "Innovatività"));
        final AdapterListTipologia adapter = new AdapterListTipologia(this, tipologia);
        tipologiaVotiList.setAdapter(adapter);
        tipologiaVotiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TipologiaModel model = tipologia.get(i);
                if (model.isSelected()) {
                    model.setSelected(false);
                    temp.remove(model.getTipoTipologia().toString());
                }else {
                    model.setSelected(true);
                    temp.add(model.getTipoTipologia().toString());
                }
                tipologia.set(i, model);
                adapter.updateRecords(tipologia);
            }
        });
    }

    private void inserimentoPercentualeVoto(){
        creazione(11);  //non va messo qua vedere dove rendere invisibili il seekbar e la tabella top
        selezionaCasella(11,seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int giuria, boolean fromUser) {
                int pubblico=100-giuria;
                testoSeekBar.setText("Giuria " + giuria + "%        Pubblico "+pubblico+"%");
                tipo=giuria+"-"+pubblico;
                if(seekBar.getProgress()==0)
                              seekBar.setProgress(1);
                if(seekBar.getProgress()==100)
                         seekBar.setProgress(99);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void creazione(int n) {
        Button bi = null;
        for (int i = 0; i < n; i++) {
            String strFlag = "@id/" + "btn" + i;
            bi = (Button) findViewById(getResources().getIdentifier(strFlag, null, getPackageName()));
            ListaButton.add(bi);
        }
    }

    //ascoltatore button
    private void selezionaCasella(final int n, final SeekBar seekBar) {
        for(int i = 1; i < ListaButton.size()-1; i++){
            final int index=i;
            ListaButton.get(index).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    seekBar.setProgress(index*10);
                }
            });
        }
    }

    private void gestioneSwitchtipologiaVotazione(){
        tipologiaVotazione.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tipologiaVotazione.setText("Scegli una o più tipologie di voto");
                    tipologiaVotiList.setVisibility(View.VISIBLE);
                    inserimentoTipologiaVoti();
                } else {
                    tipologiaVotazione.setText("Nessuna tipologia scelta (i voti andranno da 1 a 10 per ogni progetto)");
                    tipologiaVotiList.setVisibility(View.GONE);
                }
            }
        });
    }

    private void gestioneSwitchtipoVotazione(){
        tipoVotazione.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tipoVotazione.setText("Il pubblico può votare");
                    seekBar.setVisibility(View.VISIBLE);
                    testo2Txt.setVisibility(View.VISIBLE);
                    testoSeekBar.setVisibility(View.VISIBLE);
                    tableLayout.setVisibility(View.VISIBLE);
                    inserimentoPercentualeVoto();
                } else {
                    tipoVotazione.setText("Il pubblico non può votare");
                    seekBar.setVisibility(View.GONE);
                    testo2Txt.setVisibility(View.GONE);
                    testoSeekBar.setVisibility(View.GONE);
                    tableLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void sceltaConcorso(){
        sceltaConcorsoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(GestioneGiuria.this);
                LayoutInflater inflater = (LayoutInflater) GestioneGiuria.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.dialog, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setCancelable(false);   //freccia indietro non scompare nn so se metterlo
                final Button  rispostaPositivaButton= (Button) dialogView.findViewById(R.id.rispostaPositiva);
                rispostaPositivaButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!tipoVotazione.isChecked())
                            tipo="solo giuria";
                        if(temp.size()==0)
                            tipoTipologia="no";
                        if(!tipologiaVotazione.isChecked())
                            tipoTipologia="no";
                        else {
                            for (int i = 0; i < temp.size(); i++)
                                if (tipoTipologia.equals(""))
                                    tipoTipologia = temp.get(i).toString();
                                else
                                    tipoTipologia = tipoTipologia + "-" + temp.get(i).toString();
                        }
                        Progetto progetto = new Progetto(id,tipo,tipoTipologia);
                        String inserimento = new Gson().toJson(progetto, Progetto.class);
                        new AvviaConcorsoAsync(GestioneGiuria.this).execute(inserimento);
                        alertDialog.dismiss();
                    }
                });
                final Button  rispostaNegativaButton= (Button) dialogView.findViewById(R.id.rispostaNegativa);
                rispostaNegativaButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!tipoVotazione.isChecked())
                            tipo="solo giuria";
                        if(temp.size()==0)
                            tipoTipologia="no";
                        if(!tipologiaVotazione.isChecked()) {
                            tipoTipologia = "no";
                        }else {
                            for (int i = 0; i < temp.size(); i++)
                                if (tipoTipologia.equals(""))
                                    tipoTipologia = temp.get(i).toString();
                                else
                                    tipoTipologia = tipoTipologia + "-" + temp.get(i).toString();
                        }
                            Progetto progetto = new Progetto(id, tipo, tipoTipologia);
                            String inserimento = new Gson().toJson(progetto, Progetto.class);
                            new NonAvviaConcorsoAsync(GestioneGiuria.this).execute(inserimento);
                            alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
    }
}
