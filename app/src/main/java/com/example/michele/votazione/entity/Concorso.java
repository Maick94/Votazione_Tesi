package com.example.michele.votazione.entity;

import java.io.Serializable;

/**
 * Created by Michele on 20/03/2020.
 */

public class Concorso implements Serializable {
        private String id;            //qua vedere se int o altro forse automatico sul server quindi costruttore no vediamo
        private String nome;
        private String descrizione;
        private String username;
        private String progetti;
        private String giudici;
        private String votazioneAttivata;
        private String tipoVotazione;
        private String chiuso;


    public Concorso(String id, String nome, String descrizione, String username, String progetti, String giudici, String votazioneAttivata, String tipoVotazione, String chiuso) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.username = username;
        this.progetti = progetti;
        this.giudici = giudici;
        this.votazioneAttivata = votazioneAttivata;
        this.tipoVotazione = tipoVotazione;
        this.chiuso = chiuso;
    }

    //oggetto InserisciConcorso1Async
    public Concorso(String nome, String descrizione, String username, String progetti) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.username = username;
        this.progetti = progetti;
    }

    public Concorso(String id, String giudici) {
        this.id = id;
        this.giudici = giudici;
    }

    public Concorso(String id) {
        this.id = id;
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProgetti() {
        return progetti;
    }

    public void setProgetti(String progetti) {
        this.progetti = progetti;
    }

    public String getGiudici() {
        return giudici;
    }

    public void setGiudici(String giudici) {
        this.giudici = giudici;
    }

    public String getVotazioneAttivata() {
        return votazioneAttivata;
    }

    public void setVotazioneAttivata(String votazioneAttivata) {
        this.votazioneAttivata = votazioneAttivata;
    }

    public String getTipoVotazione() {
        return tipoVotazione;
    }

    public void setTipoVotazione(String tipoVotazione) {
        this.tipoVotazione = tipoVotazione;
    }

    public String getChiuso() {
        return chiuso;
    }

    public void setChiuso(String chiuso) {
        this.chiuso = chiuso;
    }
}
