package com.example.michele.votazione.entity;

import java.io.Serializable;

/**
 * Created by Michele on 16/04/2020.
 */

public class Utente implements Serializable{
    private String imei;
    private String idConcorso;

    public Utente(String imei, String idConcorso) {
        this.imei = imei;
        this.idConcorso = idConcorso;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getIdConcorso() {
        return idConcorso;
    }

    public void setIdConcorso(String idConcorso) {
        this.idConcorso = idConcorso;
    }
}
