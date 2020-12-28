package org.example.db.models;

import java.util.Date;

public class History {
    private String username;
    private String rekening_tujuan;
    private String transaction_date;
    private String tipe_transaksi;
    private int trans_money;

    public History(String username, String rekening_tujuan, String transaction_date, String tipe_transaksi, int trans_money) {
        this.username = username;
        this.rekening_tujuan = rekening_tujuan;
        this.transaction_date = transaction_date;
        this.tipe_transaksi = tipe_transaksi;
        this.trans_money = trans_money;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRekening_tujuan() {
        return rekening_tujuan;
    }

    public void setRekening_tujuan(String rekening_tujuan) {
        this.rekening_tujuan = rekening_tujuan;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getTipe_transaksi() {
        return tipe_transaksi;
    }

    public void setTipe_transaksi(String tipe_transaksi) {
        this.tipe_transaksi = tipe_transaksi;
    }

    public int getTrans_money() {
        return trans_money;
    }

    public void setTrans_money(int trans_money) {
        this.trans_money = trans_money;
    }
}
