package org.example.db.models;

import javax.persistence.*;

@Entity
@Table(name = "dummy")
public class DummyBank {
    @Id
    @Column(name = "no_rek")
    private String no_rek;

    @Column(name = "nama_pemilik")
    private String nama_pemilik;

    @Column(name = "kode_bank")
    private String kode_bank;

    @Column(name = "nama_bank")
    private String nama_bank;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "saldo_dummy")
    private int saldo_dummy;

    @Column(name = "loginStatus")
    private String loginStatus;

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

    public int getSaldo_dummy() {
        return saldo_dummy;
    }
    public void setSaldo_dummy(int saldo_dummy) {
        this.saldo_dummy = saldo_dummy;
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

    public String getLoginStatus() {
        return loginStatus;
    }
    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }
}
