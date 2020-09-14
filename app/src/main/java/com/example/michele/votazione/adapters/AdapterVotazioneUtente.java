package com.example.michele.votazione.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.michele.votazione.R;
import com.example.michele.votazione.VotazioneUtente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michele on 07/04/2020.
 */

public class AdapterVotazioneUtente extends android.support.v4.view.PagerAdapter {

    private List<ModelVotazione> models;
    private LayoutInflater layoutInflater;
    private Context context;
    private int valore=0;
    TextView nomeProgetto;



    public AdapterVotazioneUtente(List<ModelVotazione> models, Context context) {
        this.models = models;
        this.context = context;
    }



    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view=null;
        String[] tipologie= models.get(0).getTipologia().toString().split("-");
        valore=tipologie.length;
        tipologie[0]="Giudizio globale";
        view = layoutInflater.inflate(R.layout.item1_giuria, container, false);
        visualizzazione1(view, tipologie);
        nomeProgetto = view.findViewById(R.id.nomeProgetto);
        nomeProgetto.setText(models.get(position).getNomeProgetto());
        container.addView(view, 0);
        return view;
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    public int getSize(){
        return valore;
    }


    private void visualizzazione1(View view,String[] tipologie){
        ArrayList<SeekBar> listaSeekBar=new ArrayList<SeekBar>();
        creazioneSeekBar(1,view,listaSeekBar);

        final ArrayList<TextView> listaTextView=new ArrayList<TextView>();
        creazioneTextView(1,view,listaTextView,tipologie);
        selezionaSeekBar(1,listaSeekBar,listaTextView,tipologie);


        ArrayList<Button> listaButton0=new ArrayList<Button>();
        creazione0(6,view,listaButton0);
        selezionaCasella0(6,listaSeekBar.get(0),listaButton0);
    }


    private void creazioneSeekBar(int n, View view, ArrayList<SeekBar> listaSeekBar) {
        SeekBar seekBari = null;
        for (int i = 0; i < n; i++) {
            String strFlag = "@id/" + "seekBar" + i;
            seekBari = (SeekBar) view.findViewById(view.getResources().getIdentifier(strFlag, null, context.getPackageName()));
            listaSeekBar.add(seekBari);
        }
    }

    private void creazioneTextView(int n, View view, ArrayList<TextView> listaTextView, String[]tipologie) {
        TextView textViewi = null;
        for (int i = 0; i < n; i++) {
            String strFlag = "@id/" + "testoSeekBar" + i;
            textViewi = (TextView) view.findViewById(view.getResources().getIdentifier(strFlag, null, context.getPackageName()));
            textViewi.setText(tipologie[i]+" voto: "+0);
            listaTextView.add(textViewi);
        }
    }


    private void selezionaSeekBar(final int n, final ArrayList<SeekBar> listaSeekBar, final ArrayList<TextView> listaTextView, final String[] tipologie) {
        for(int i = 0; i < listaSeekBar.size(); i++){

            final int index=i;
            listaSeekBar.get(index).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
                    listaTextView.get(index).setText(tipologie[index] + " voto: " + value);
                    VotazioneUtente.avvalora(value,index,listaSeekBar.size());
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });




        }

    }




    private void creazione0(int n, View view,ArrayList<Button> ListaButton) {
        Button bi = null;
        for (int i = 0; i < n; i++) {
            String strFlag = "@id/" + "btn" + i;
            bi = (Button) view.findViewById(view.getResources().getIdentifier(strFlag, null, context.getPackageName()));
            ListaButton.add(bi);
        }
    }

    private void selezionaCasella0(final int n, final SeekBar seekBar,ArrayList<Button> ListaButton) {
        for(int i = 0; i < ListaButton.size(); i++){
            final int index=i;
            ListaButton.get(index).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    seekBar.setProgress(index);
                }
            });
        }
    }

    private void creazione1(int n, View view,ArrayList<Button> ListaButton) {
        Button bi = null;
        for (int i = 0; i < n; i++) {
            String strFlag = "@id/" + "btn1" + i;
            bi = (Button) view.findViewById(view.getResources().getIdentifier(strFlag, null, context.getPackageName()));
            ListaButton.add(bi);
        }
    }

    private void selezionaCasella1(final int n, final SeekBar seekBar,ArrayList<Button> ListaButton) {
        for(int i = 0; i < ListaButton.size(); i++){
            final int index=i;
            ListaButton.get(index).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    seekBar.setProgress(index);

                }
            });
        }
    }

    private void creazione2(int n, View view,ArrayList<Button> ListaButton) {
        Button bi = null;
        for (int i = 0; i < n; i++) {
            String strFlag = "@id/" + "btn2" + i;
            bi = (Button) view.findViewById(view.getResources().getIdentifier(strFlag, null, context.getPackageName()));
            ListaButton.add(bi);
        }
    }

    private void selezionaCasella2(final int n, final SeekBar seekBar,ArrayList<Button> ListaButton) {
        for(int i = 0; i < ListaButton.size(); i++){
            final int index=i;
            ListaButton.get(index).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    seekBar.setProgress(index);
                }
            });
        }
    }

    private void creazione3(int n, View view,ArrayList<Button> ListaButton) {
        Button bi = null;
        for (int i = 0; i < n; i++) {
            String strFlag = "@id/" + "btn3" + i;
            bi = (Button) view.findViewById(view.getResources().getIdentifier(strFlag, null, context.getPackageName()));
            ListaButton.add(bi);
        }
    }

    private void selezionaCasella3(final int n, final SeekBar seekBar,ArrayList<Button> ListaButton) {
        for(int i = 0; i < ListaButton.size(); i++){
            final int index=i;
            ListaButton.get(index).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    seekBar.setProgress(index);
                }
            });
        }
    }

    private void creazione4(int n, View view,ArrayList<Button> ListaButton) {
        Button bi = null;
        for (int i = 0; i < n; i++) {
            String strFlag = "@id/" + "btn4" + i;
            bi = (Button) view.findViewById(view.getResources().getIdentifier(strFlag, null, context.getPackageName()));
            ListaButton.add(bi);
        }
    }

    private void selezionaCasella4(final int n, final SeekBar seekBar,ArrayList<Button> ListaButton) {
        for(int i = 0; i < ListaButton.size(); i++){
            final int index=i;
            ListaButton.get(index).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    seekBar.setProgress(index);
                }
            });
        }
    }

    private void creazione5(int n, View view,ArrayList<Button> ListaButton) {
        Button bi = null;
        for (int i = 0; i < n; i++) {
            String strFlag = "@id/" + "btn5" + i;
            bi = (Button) view.findViewById(view.getResources().getIdentifier(strFlag, null, context.getPackageName()));
            ListaButton.add(bi);
        }
    }

    private void selezionaCasella5(final int n, final SeekBar seekBar,ArrayList<Button> ListaButton) {
        for(int i = 0; i < ListaButton.size(); i++){
            final int index=i;
            ListaButton.get(index).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    seekBar.setProgress(index);
                }
            });
        }
    }

    private void creazione6(int n, View view,ArrayList<Button> ListaButton) {
        Button bi = null;
        for (int i = 0; i < n; i++) {
            String strFlag = "@id/" + "btn6" + i;
            bi = (Button) view.findViewById(view.getResources().getIdentifier(strFlag, null, context.getPackageName()));
            ListaButton.add(bi);
        }
    }

    private void selezionaCasella6(final int n, final SeekBar seekBar,ArrayList<Button> ListaButton) {
        for(int i = 0; i < ListaButton.size(); i++){
            final int index=i;
            ListaButton.get(index).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    seekBar.setProgress(index);
                }
            });
        }
    }




}

