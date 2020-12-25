package com.example.mybank.model;

public class Transfer {
    private String username;
    private String kode_transaksi;
    private String rekening_tujuan;
    private int trans_money;

    public Transfer(String username, String kode_transaksi, String rekening_tujuan, int trans_money) {
        this.username = username;
        this.kode_transaksi = kode_transaksi;
        this.rekening_tujuan = rekening_tujuan;
        this.trans_money = trans_money;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getKode_transaksi() {
        return kode_transaksi;
    }
    public void setKode_transaksi(String kode_transaksi) {
        this.kode_transaksi = kode_transaksi;
    }

    public String getRekening_tujuan() {
        return rekening_tujuan;
    }
    public void setRekening_tujuan(String rekening_tujuan) {
        this.rekening_tujuan = rekening_tujuan;
    }

    public int getTrans_money() {
        return trans_money;
    }
    public void setTrans_money(int trans_money) {
        this.trans_money = trans_money;
    }
}
