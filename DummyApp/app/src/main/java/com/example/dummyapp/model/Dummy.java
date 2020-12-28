package com.example.dummyapp.model;

public class Dummy {
    private String no_rek;
    private String nama_pemilik;
    private String kode_bank;
    private String nama_bank;
    private String username;
    private String password;
    private int saldo_dummy;
    private String loginStatus;

    public Dummy(String no_rek, String nama_pemilik, String kode_bank, String nama_bank, String username, String password, int saldo_dummy, String loginStatus) {
        this.no_rek = no_rek;
        this.nama_pemilik = nama_pemilik;
        this.kode_bank = kode_bank;
        this.nama_bank = nama_bank;
        this.username = username;
        this.password = password;
        this.saldo_dummy = saldo_dummy;
        this.loginStatus = loginStatus;
    }

    public String getNo_rek() {
        return no_rek;
    }

    public void setNo_rek(String no_rek) {
        this.no_rek = no_rek;
    }

    public String getNama_pemilik() {
        return nama_pemilik;
    }

    public void setNama_pemilik(String nama_pemilik) {
        this.nama_pemilik = nama_pemilik;
    }

    public String getKode_bank() {
        return kode_bank;
    }

    public void setKode_bank(String kode_bank) {
        this.kode_bank = kode_bank;
    }

    public String getNama_bank() {
        return nama_bank;
    }

    public void setNama_bank(String nama_bank) {
        this.nama_bank = nama_bank;
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

    public int getSaldo_dummy() {
        return saldo_dummy;
    }

    public void setSaldo_dummy(int saldo_dummy) {
        this.saldo_dummy = saldo_dummy;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }
}
