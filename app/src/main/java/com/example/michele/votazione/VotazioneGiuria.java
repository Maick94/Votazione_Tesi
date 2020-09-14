package com.example.michele.votazione;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.michele.votazione.adapters.AdapterVotazioneGiuria;
import com.example.michele.votazione.adapters.CustomViewPagerVotazione;
import com.example.michele.votazione.adapters.ModelVotazione;
import com.example.michele.votazione.connessione.AvviaVotazioneGiuriaAsync;
import com.example.michele.votazione.connessione.InserimentoUtenteAsync;
import com.example.michele.votazione.connessione.InserimentoVotazioneAsync;
import com.example.michele.votazione.entity.Progetti;
import com.example.michele.votazione.entity.Progetto;
import com.example.michele.votazione.entity.Utente;
import com.example.michele.votazione.entity.Votazione;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class VotazioneGiuria extends AppCompatActivity {
    private String resultGson;
    private String idConcorso;
    private String imei;
    private String[] nomeProgetto;
    private String[] tipologie;
    private CustomViewPagerVotazione viewPager;
    private AdapterVotazioneGiuria adapter;
    private List<ModelVotazione> models;
    private Integer[] colors = null;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private Button button;
    int voto=0;
    private int index=0;
    static int flag=-1;
    static int[] dati;
    Progetti progetti=null;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votazione_giuria);
        idConcorso= (String) getIntent().getSerializableExtra("IdConcorso");
        imei= (String) getIntent().getSerializableExtra("imei");
        Progetto p=new Progetto(idConcorso);
        String progetto = new Gson().toJson(p, Progetto.class);
        try {
            resultGson = new AvviaVotazioneGiuriaAsync(VotazioneGiuria.this).execute(progetto).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        progetti = new Gson().fromJson(resultGson, Progetti.class);
        final int numeroProgetti = progetti.getProgettoList().size();
        nomeProgetto= new String[numeroProgetti];
        tipologie= new String[numeroProgetti];
        models = new ArrayList<>();
        for(int i = 0; i < numeroProgetti; i++) {
            nomeProgetto[i] = progetti.getProgettoList().get(i).getNome();
            tipologie[i] = progetti.getProgettoList().get(i).getTipologiaGiuria();
            models.add(new ModelVotazione(nomeProgetto[i],tipologie[i]));
        }
        adapter = new AdapterVotazioneGiuria(models, this);
        viewPager = (CustomViewPagerVotazione) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(90, 0, 90, 0);                    //dimensione imporatnte
        viewPager.setPagingEnabled(false);
        Integer[] colors_temp = {
                getResources().getColor(R.color.color0),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3)
        };
        colors = colors_temp;
        button = (Button) findViewById(R.id.button1);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n=adapter.getSize();
                if(flag==-1){
                    avvalora(0,0,n);
                }
                voto=0;
                for(int k=0;k<n;k++){
                   if (dati[k]==-1)
                               dati[k]=0;
                   voto=dati[k]+voto;
                   dati[k]=-1;
                }
                Votazione votazione=new Votazione(progetti.getProgettoList().get(index).getIdConcorso(), progetti.getProgettoList().get(index).getId(), "Giuria",""+voto);
                index++;
                String insV = new Gson().toJson(votazione, Votazione.class);
                new InserimentoVotazioneAsync(VotazioneGiuria.this).execute(insV);
                if(viewPager.getCurrentItem() == models.size()-1) {
                    Utente u=new Utente(imei,idConcorso);
                    String insU = new Gson().toJson(u, Utente.class);
                    new InserimentoUtenteAsync(VotazioneGiuria.this).execute(insU);
                    button.setText("Fine Votazione");
                    Intent intent = new Intent(VotazioneGiuria.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);

            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (adapter.getCount() -1)) {
                    viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position%4], colors[(position+1)%4]));
                }
            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static void avvalora(int value,int pos, int n){
        if (flag==-1) {
            dati = new int[n];
            for (int i = 0; i < n; i++)
                dati[i] = -1;
            flag=0;
        }
            dati[pos] = value;

    }
}
