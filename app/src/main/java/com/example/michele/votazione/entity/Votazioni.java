package com.example.michele.votazione.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Michele on 06/04/2020.
 */

public class Votazioni implements Serializable {
    private List<Votazione> votazioneList;

    public Votazioni(List<Votazione> votazioneList) {
        this.votazioneList = votazioneList;
    }

    public List<Votazione> getVotazioneList() {
        return votazioneList;
    }

    public void setVotazioneList(List<Votazione> votazioneList) {
        this.votazioneList = votazioneList;
    }

}
