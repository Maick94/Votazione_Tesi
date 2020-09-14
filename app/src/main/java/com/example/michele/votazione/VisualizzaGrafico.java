package com.example.michele.votazione;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.michele.votazione.Random.UniqueRandom;
import com.example.michele.votazione.connessione.AvviaVotazioneGiuriaAsync;
import com.example.michele.votazione.connessione.VisualizzaProgettiAsync;
import com.example.michele.votazione.entity.Progetti;
import com.example.michele.votazione.entity.Progetto;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static com.github.mikephil.charting.utils.ColorTemplate.*;

public class VisualizzaGrafico extends AppCompatActivity {

    private String idConcorso;
    private String resultGson;

    private BarChart m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_grafico);
        idConcorso= (String) getIntent().getSerializableExtra("IdConcorso");


        Progetto p=new Progetto(idConcorso);

        String progetto = new Gson().toJson(p, Progetto.class);

        Progetti progetti=null;
        try {
            resultGson = new VisualizzaProgettiAsync(VisualizzaGrafico.this).execute(progetto).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        progetti = new Gson().fromJson(resultGson, Progetti.class);

        int n=progetti.getProgettoList().size();
        List<BarEntry> barEntries = new ArrayList<>();

        if(progetti.getProgettoList().get(0).getTipoVotazione().equals("solo giuria")) {
            int a;
            for (int i = 0; i < n; i++) {
                a = Integer.parseInt(progetti.getProgettoList().get(i).getVotoGiuria());
                barEntries.add(new BarEntry(i, a));
            }
        }else if(progetti.getProgettoList().get(0).getTipoVotazione().equals("solo pubblico")){
            int a;
            for (int i = 0; i < n; i++) {
                a = Integer.parseInt(progetti.getProgettoList().get(i).getVotoPubblico());
                barEntries.add(new BarEntry(i, a));
            }
        }else{
            String[] percentuali= progetti.getProgettoList().get(0).getTipoVotazione().split("-") ;
            int giuria=Integer.parseInt(percentuali[0]);
            int pubblico=Integer.parseInt(percentuali[1]);
            float a;
            float b;
            float c;
            for (int i = 0; i < n; i++) {
                a = Float.parseFloat(progetti.getProgettoList().get(i).getVotoGiuria());
                b = Float.parseFloat(progetti.getProgettoList().get(i).getVotoPubblico());
                a= a * giuria / 100;
                b= b * pubblico / 100;
                c = a+b;
                barEntries.add(new BarEntry(i, c));
            }
        }
        m = (BarChart) findViewById(R.id.barchart);



        BarDataSet barDataSet = new BarDataSet(barEntries, "Progetti");


        ArrayList<Integer> colors = new ArrayList<Integer>();  //colori random
        UniqueRandom random = new UniqueRandom();
        UniqueRandom random1 = new UniqueRandom();
        UniqueRandom random2 = new UniqueRandom();
        for(int i=0;i<n;i++) {
            colors.add( Color.argb(255, random.nextInt(256), random1.nextInt(256), random2.nextInt(256)));
        }
        barDataSet.setColors(colors);



        //faccio un if sulla sul numero di barre se e maggiore di un certo numero la setto a qlc
        float dimensioneTesto;
        if (n>=20)
            dimensioneTesto=5;
        else
            dimensioneTesto=10;
        barDataSet.setValueTextSize(dimensioneTesto);   //testo su barra


        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f);
        m.animateY(4000);
        m.invalidate();

        m.getDescription().setEnabled(false);
        m.setData(barData);




        Legend l = m.getLegend();
        l.setFormSize(18f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);

        l.setTextSize(18f);
        l.setTextColor(Color.BLACK);
        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis



        List<LegendEntry> entries = new ArrayList<>();

        for (int i = 0; i < progetti.getProgettoList().size(); i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = colors.get(i);
            entry.label = progetti.getProgettoList().get(i).getNome();
            entries.add(entry);
        }
        l.setCustom(entries);

        m.getLegend().setWordWrapEnabled(true);





    }
}
