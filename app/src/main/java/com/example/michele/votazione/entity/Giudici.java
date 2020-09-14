package com.example.michele.votazione.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Michele on 06/03/2020.
 */

public class Giudici implements Serializable {
    private List<Giudice> giudiceList;

    public Giudici(List<Giudice> giudiceList) {
        this.giudiceList = giudiceList;
    }

    public List<Giudice> getGiudiceList() {
        return giudiceList;
    }

    public void setGiudiceList(List<Giudice> giudiceList) {
        this.giudiceList = giudiceList;
    }

}
