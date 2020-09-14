package com.example.michele.votazione.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.michele.votazione.R;

import java.util.List;

/**
 * Created by Michele on 28/03/2020.
 */

public class AdapterListTipologia  extends BaseAdapter {

    Activity activity;
    List<TipologiaModel> tipologie;
    LayoutInflater inflater;

    //short to create constructer using command+n for mac & Alt+Insert for window


    public AdapterListTipologia(Activity activity) {
        this.activity = activity;
    }

    public AdapterListTipologia(Activity activity, List<TipologiaModel> tipologie) {
        this.activity   = activity;
        this.tipologie      = tipologie;
        inflater        = activity.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return tipologie.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        if (view == null){

            view = inflater.inflate(R.layout.custom_list_tipologia, viewGroup, false);

            holder = new ViewHolder();

            holder.tvUserName = (TextView)view.findViewById(R.id.tv_user_name);
            holder.ivCheckBox = (ImageView) view.findViewById(R.id.iv_check_box);

            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();

        TipologiaModel model = tipologie.get(i);

        holder.tvUserName.setText(model.getTipoTipologia());

        if (model.isSelected())
            holder.ivCheckBox.setBackgroundResource(R.drawable.checked);

        else
            holder.ivCheckBox.setBackgroundResource(R.drawable.check);

        return view;

    }

    public void updateRecords(List<TipologiaModel> tipologie){
        this.tipologie = tipologie;

        notifyDataSetChanged();
    }

    class ViewHolder{

        TextView tvUserName;
        ImageView ivCheckBox;

    }
}
