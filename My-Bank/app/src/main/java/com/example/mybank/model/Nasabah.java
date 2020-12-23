package com.example.mybank.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Nasabah {

    private String nama_lengkap;
    private String email;
    private String username;
    private String password;
    private String gender;
    private String no_ktp;
    private String birth_date;
    private String no_telp;
    private String alamat;

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
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }
    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNo_ktp() {
        return no_ktp;
    }
    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    public String getBirth_date() {
        return birth_date;
    }
    public void setBirth_date(String no_ktp) {
        this.birth_date = no_ktp;
    }

    public String getNo_telp() {
        return no_telp;
    }
    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
