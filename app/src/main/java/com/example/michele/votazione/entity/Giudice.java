package com.example.michele.votazione.entity;

import java.io.Serializable;

/**
 * Created by Michele on 06/03/2020.
 */

public class Giudice implements Serializable {  //questa classe va modificate vedere come fare
    private String email;
    private String password;
    private int idConcorso;

    public Giudice(String email, String password, int idConcorso) {
        this.email = email;
        this.password = password;
        this.idConcorso = idConcorso;
    }

    public Giudice(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Giudice(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdConcorso() {
        return idConcorso;
    }

    public void setIdConcorso(int idConcorso) {
        this.idConcorso = idConcorso;
    }
}

