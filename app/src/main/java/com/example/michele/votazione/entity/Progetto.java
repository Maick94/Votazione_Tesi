package com.example.michele.votazione.entity;

import java.io.Serializable;

/**
 * Created by Michele on 20/03/2020.
 */

public class Progetto  implements Serializable {

    private String id;
    private String nome;
    private String votoPubblico;
    private String votoGiuria;
    private String idConcorso;
    private String tipoVotazione;
    private String idTipologiaVotoGiuria;
    private String tipologiaGiuria;

    public Progetto(String id, String nome, String votoPubblico, String votoGiuria, String idConcorso, String tipoVotazione, String idTipologiaVotoGiuria, String tipologiaGiuria) {
        this.id = id;
        this.nome = nome;
        this.votoPubblico = votoPubblico;
        this.votoGiuria = votoGiuria;
        this.idConcorso = idConcorso;
        this.tipoVotazione = tipoVotazione;
        this.idTipologiaVotoGiuria = idTipologiaVotoGiuria;
        this.tipologiaGiuria = tipologiaGiuria;
    }



    public Progetto(String nome) {
        this.nome = nome;
    }


    public Progetto(String idConcorso, String tipoVotazione, String tipologiaGiuria) {
        this.idConcorso = idConcorso;
        this.tipoVotazione = tipoVotazione;
        this.tipologiaGiuria = tipologiaGiuria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVotoPubblico() {
        return votoPubblico;
    }

    public void setVotoPubblico(String votoPubblico) {
        this.votoPubblico = votoPubblico;
    }

    public String getVotoGiuria() {
        return votoGiuria;
    }

    public void setVotoGiuria(String votoGiuria) {
        this.votoGiuria = votoGiuria;
    }

    public String getIdConcorso() {
        return idConcorso;
    }

    public void setIdConcorso(String idConcorso) {
        this.idConcorso = idConcorso;
    }

    public String getTipoVotazione() {
        return tipoVotazione;
    }

    public void setTipoVotazione(String tipoVotazione) {
        this.tipoVotazione = tipoVotazione;
    }

    public String getIdTipologiaVotoGiuria() {
        return idTipologiaVotoGiuria;
    }

    public void setIdTipologiaVotoGiuria(String idTipologiaVotoGiuria) {
        this.idTipologiaVotoGiuria = idTipologiaVotoGiuria;
    }

    public String getTipologiaGiuria() {
        return tipologiaGiuria;
    }

    public void setTipologiaGiuria(String tipologiaGiuria) {
        this.tipologiaGiuria = tipologiaGiuria;
    }
}
