package org.example.db.models;

import java.util.Date;

public class Mutasi {
    private String username;
    private int id_nasabah;
    private String start_mutasi;
    private String end_mutasi;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }



    public int getId_nasabah() {
        return id_nasabah;
    }
    public void setId_nasabah(int id_nasabah) {
        this.id_nasabah = id_nasabah;
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
