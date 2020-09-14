package com.example.michele.votazione.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.michele.votazione.R;

/**
 * Created by Michele on 20/03/2020.
 */

public class AdapterListProgetti extends ArrayAdapter {
    private  Context context;
    private  String[] nomeProgetto;

    public AdapterListProgetti(Context context,String[] nomeProgetto) {
        super(context, R.layout.custom_list_progetti,nomeProgetto);
        this.context = context;
        this.nomeProgetto=nomeProgetto;

    }

    @Override
    public View getView( int position, View convertView,  ViewGroup parent) {
         LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         View rowView = inflater.inflate(R.layout.custom_list_progetti, parent, false);
         TextView n = (TextView) rowView.findViewById(R.id.textViewName);
        n.setText(nomeProgetto[position]);
        return rowView;
    }


}
