package com.example.michele.votazione.entity;

/**
 * Created by Michele on 06/04/2020.
 */

public class Votazione {
    private String id;
    private String idConcorso;
    private String idProgetto;
    private String tipoVotazione;
    private String punteggio;

    public Votazione(String id, String idConcorso, String idProgetto, String tipoVotazione, String punteggio) {
        this.id = id;
        this.idConcorso = idConcorso;
        this.idProgetto = idProgetto;
        this.tipoVotazione = tipoVotazione;
        this.punteggio = punteggio;
    }

    public Votazione(String idConcorso, String idProgetto, String tipoVotazione, String punteggio) {
        this.idConcorso = idConcorso;
        this.idProgetto = idProgetto;
        this.tipoVotazione = tipoVotazione;
        this.punteggio = punteggio;
    }

    public Votazione(String idProgetto, String tipoVotazione, String punteggio) {
        this.idProgetto = idProgetto;
        this.tipoVotazione = tipoVotazione;
        this.punteggio = punteggio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdConcorso() {
        return idConcorso;
    }

    public void setIdConcorso(String idConcorso) {
        this.idConcorso = idConcorso;
    }

    public String getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(String idProgetto) {
        this.idProgetto = idProgetto;
    }

    public String getTipoVotazione() {
        return tipoVotazione;
    }

    public void setTipoVotazione(String tipoVotazione) {
        this.tipoVotazione = tipoVotazione;
    }

    public String getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(String punteggio) {
        this.punteggio = punteggio;
    }
}
