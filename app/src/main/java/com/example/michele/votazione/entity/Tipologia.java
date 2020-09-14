package com.example.michele.votazione.entity;

/**
 * Created by Michele on 28/03/2020.
 */

public class Tipologia {
    private int id;
    private String tipoTipologia;

    public Tipologia(int id, String tipoTipologia) {
        this.id = id;
        this.tipoTipologia = tipoTipologia;
    }

    public Tipologia(String tipoTipologia) {
        this.tipoTipologia = tipoTipologia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoTipologia() {
        return tipoTipologia;
    }

    public void setTipoTipologia(String tipoTipologia) {
        this.tipoTipologia = tipoTipologia;
    }
}
