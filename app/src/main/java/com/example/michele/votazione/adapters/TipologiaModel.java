package com.example.michele.votazione.adapters;

/**
 * Created by Michele on 28/03/2020.
 */

public class TipologiaModel {

    boolean isSelected;
    String userName;

    //now create constructor and getter setter method using shortcut like command+n for mac & Alt+Insert for window.


    public TipologiaModel(boolean isSelected, String userName) {
        this.isSelected = isSelected;
        this.userName = userName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTipoTipologia() {
        return userName;
    }

    public void setTipoTipologia(String userName) {
        this.userName = userName;
    }
}