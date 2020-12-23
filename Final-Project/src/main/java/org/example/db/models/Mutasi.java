package org.example.db.models;

import java.util.Date;

public class Mutasi {
    private int id_nasabah;
    private Date start_mutasi;
    private Date end_mutasi;

    public int getId_nasabah() {
        return id_nasabah;
    }
    public void setId_nasabah(int id_nasabah) {
        this.id_nasabah = id_nasabah;
    }

    public Date getStart_mutasi() {
        return start_mutasi;
    }
    public void setStart_mutasi(Date start_mutasi) {
        this.start_mutasi = start_mutasi;
    }

    public Date getEnd_mutasi() {
        return end_mutasi;
    }
    public void setEnd_mutasi(Date end_mutasi) {
        this.end_mutasi = end_mutasi;
    }
}
