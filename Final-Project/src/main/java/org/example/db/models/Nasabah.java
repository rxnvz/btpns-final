package org.example.db.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "nasabah")
public class Nasabah {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_nasabah")
    private int id_nasabah;

    @Column(name = "nama_lengkap")
    private String nama;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private String gender;

    @Column(name = "no_ktp")
    private String ktp;

    @Column(name = "birth_date")
    private String tgl_lahir;

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

    public Nasabah(String nama, String email, String username, String password, String gender, String ktp, String tgl_lahir, String no_telp, String alamat) {
        this.nama = nama;
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.ktp = ktp;
        this.tgl_lahir = tgl_lahir;
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
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
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
    public String getKtp() {
        return ktp;
    }
    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    // -------------------- SETTER & GETTER BIRTH --------------------
    public String getTgl_lahir() {
        return tgl_lahir;
    }
    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
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
