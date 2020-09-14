package com.example.michele.votazione.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Michele on 28/03/2020.
 */

public class Tipologie implements Serializable {
    private List<Tipologia> tipologiaList;

    public Tipologie(List<Tipologia> tipologiaList) {
        this.tipologiaList = tipologiaList;
    }

    public List<Tipologia> getTipologiaList() {
        return tipologiaList;
    }

    public void setTipologiaList(List<Tipologia> tipologiaList) {
        this.tipologiaList = tipologiaList;
    }

}
