package com.example.michele.votazione.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Michele on 20/03/2020.
 */

public class Progetti implements Serializable {
    private List<Progetto> progettoList;

    public Progetti(List<Progetto> progettoList) {
        this.progettoList = progettoList;
    }

    public List<Progetto> getProgettoList() {
        return progettoList;
    }

    public void setProgettoList(List<Progetto> progettoList) {
        this.progettoList = progettoList;
    }

}
