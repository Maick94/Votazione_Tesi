package com.example.michele.votazione.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.michele.votazione.R;

/**
 * Created by Michele on 06/03/2020.
 */

public class AdapterListGiudici extends ArrayAdapter {
    private  Context context;
    private  String[] email;

    public AdapterListGiudici(Context context,String[] email) {
        super(context, R.layout.custom_list_giudici,email);
        this.context = context;
        this.email=email;

    }

    @Override
    public View getView( int position, View convertView,  ViewGroup parent) {
         LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         View rowView = inflater.inflate(R.layout.custom_list_giudici, parent, false);
         TextView n = (TextView) rowView.findViewById(R.id.textViewName);
        n.setText(email[position]);
        return rowView;
    }


}
