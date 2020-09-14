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

public class AdapterListConcorsiOrganizzatore extends ArrayAdapter {
    private  Context context;
    private  String[] nomeConcorso;

    public AdapterListConcorsiOrganizzatore(Context context, String[] nomeConcorso) {
        super(context, R.layout.custom_list_concorsi_organizzatore,nomeConcorso);
        this.context = context;
        this.nomeConcorso=nomeConcorso;

    }

    @Override
    public View getView( int position, View convertView,  ViewGroup parent) {
         LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         View rowView = inflater.inflate(R.layout.custom_list_concorsi_organizzatore, parent, false);
         TextView n = (TextView) rowView.findViewById(R.id.textViewName);
        n.setText(nomeConcorso[position]);
        return rowView;
    }


}
