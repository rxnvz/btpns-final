package org.example.db.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "nasabah")
public class Nasabah {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_nasabah")
    private int id_nasabah;

    @Column(name = "nama_lengkap")
    private String nama_lengkap;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private String gender;

    @Column(name = "no_ktp")
    private String no_ktp;

    @Column(name = "birth_date")
    private String birth_date;

    @Column(name = "no_telp")
    private String no_telp;

    @Column(name = "alamat")
    private String alamat;

    @Column(name = "saldo")
    private int saldo;

    @Column(name = "loginStatus")
    private String loginStatus;

//    @OneToMany(mappedBy = "nasabah", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<Transaksi> transactions = new ArrayList<>();

    public Nasabah(String nama_lengkap, String email, String username, String password, String gender, String no_ktp, String birth_date, String no_telp, String alamat) {
        this.nama_lengkap = nama_lengkap;
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.no_ktp = no_ktp;
        this.birth_date = birth_date;
        this.no_telp = no_telp;
        this.alamat = alamat;
        this.saldo = 5000000;
    }

    public Nasabah(){}

    // -------------------- SETTER & GETTER ID --------------------
    public int getId_nasabah() {
        return id_nasabah;
    }
    public void setId_nasabah(int id_nasabah) {
        this.id_nasabah = id_nasabah;
    }

    // -------------------- SETTER & GETTER NAME --------------------
    public String getNama_lengkap() {
        return nama_lengkap;
    }
    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    // -------------------- SETTER & GETTER EMAIL --------------------
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // -------------------- SETTER & GETTER USERNAME --------------------
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    // -------------------- SETTER & GETTER PASSWORD --------------------
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // -------------------- SETTER & GETTER GENDER --------------------
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    // -------------------- SETTER & GETTER KTP --------------------
    public String getNo_ktp() {
        return no_ktp;
    }
    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    // -------------------- SETTER & GETTER BIRTH --------------------
    public String getBirth_date() {
        return birth_date;
    }
    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    // -------------------- SETTER & GETTER PHONE --------------------
    public String getNo_telp() {
        return no_telp;
    }
    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    // -------------------- SETTER & GETTER ALAMAT --------------------
    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    // -------------------- SETTER & GETTER SALDO --------------------
    public int getSaldo() {
        return saldo;
    }
    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    // -------------------- SETTER & GETTER LOGIN STATUS --------------------
    public String getLoginStatus() {
        return loginStatus;
    }
    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }
}
