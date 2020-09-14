package com.example.michele.votazione.adapters;

/**
 * Created by Michele on 04/04/2020.
 */

public class ModelVotazione {


    private String nomeProgetto;
    private String tipologia;


    public ModelVotazione(String nomeProgetto, String tipologia) {
        this.nomeProgetto = nomeProgetto;
        this.tipologia = tipologia;
    }

    public String getNomeProgetto() {
        return nomeProgetto;
    }

    public void setNomeProgetto(String nomeProgetto) {
        this.nomeProgetto = nomeProgetto;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }
}