package com.example.mybank.model;

import java.util.Date;

public class History {
    private String username;
    private String start_mutasi;
    private String end_mutasi;

    public History(String username, String start_mutasi, String end_mutasi) {
        this.username = username;
        this.start_mutasi = start_mutasi;
        this.end_mutasi = end_mutasi;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getStart_mutasi() {
        return start_mutasi;
    }
    public void setStart_mutasi(String start_mutasi) {
        this.start_mutasi = start_mutasi;
    }

    public String getEnd_mutasi() {
        return end_mutasi;
    }
    public void setEnd_mutasi(String end_mutasi) {
        this.end_mutasi = end_mutasi;
    }
}
