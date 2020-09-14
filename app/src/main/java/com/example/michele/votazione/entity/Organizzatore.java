package com.example.michele.votazione.entity;

import java.io.Serializable;

/**
 * Created by Michele on 22/03/2020.
 */

public class Organizzatore implements Serializable {
    private String username;
    private String password;

    public Organizzatore(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Organizzatore(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
