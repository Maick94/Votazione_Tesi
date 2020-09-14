package com.example.michele.votazione.entity;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Michele on 20/03/2020.
 */

public class Concorsi implements Serializable {
    private List<Concorso> concorsoList;

    public Concorsi(List<Concorso> concorsoList) {
        this.concorsoList = concorsoList;
    }

    public List<Concorso> getConcorsoList() {
        return concorsoList;
    }

    public void setConcorsoList(List<Concorso> concorsoList) {
        this.concorsoList = concorsoList;
    }

}
