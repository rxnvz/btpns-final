package org.example.db.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "transaksi")
public class Transaksi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_transaksi")
    private int id_transaksi;

    @Column(name = "id_nasabah")
    private int id_nasabah;

    @Column(name = "kode_transaksi")
    private String kode_transaksi;

    @Column(name = "rekening_tujuan")
    private String rekening_tujuan;

    @CreationTimestamp
    @Column(name = "transaction_date", insertable = false, updatable = false, nullable = false)
    private Date transaction_date;

    @Column(name = "tipe_transaksi")
    private String tipe_transaksi;

    @Column(name = "trans_money")
    private int trans_money;

    public Transaksi() {
    }

    // -------------------- SETTER & GETTER ID TRANSACTION --------------------
    public int getId_transaksi() {
        return id_transaksi;
    }
    public void setId_transaksi(int id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    // -------------------- SETTER & GETTER ID NASABAH --------------------
    public int getId_nasabah() {
        return id_nasabah;
    }
    public void setId_nasabah(int id_nasabah) {
        this.id_nasabah = id_nasabah;
    }

    // -------------------- SETTER & GETTER KODE TRANSAKSI --------------------
    public String getKode_transaksi() {
        return kode_transaksi;
    }
    public void setKode_transaksi(String kode_transaksi) {
        this.kode_transaksi = kode_transaksi;
    }

    // -------------------- SETTER & GETTER REKENING TUJUAN --------------------
    public String getRekening_tujuan() {
        return rekening_tujuan;
    }
    public void setRekening_tujuan(String rekening_tujuan) {
        this.rekening_tujuan = rekening_tujuan;
    }

    // -------------------- SETTER & GETTER TRANSACTION DATE --------------------
    public Date getTransaction_date() {
        return transaction_date;
    }
    @PrePersist
    public void setTransaction_date() {
        this.transaction_date = new Date();
    }

    // -------------------- SETTER & GETTER TRANSACTION TYPE --------------------
    public String getTipe_transaksi() {
        return tipe_transaksi;
    }
    public void setTipe_transaksi(String tipe_transaksi) {
        this.tipe_transaksi = tipe_transaksi;
    }

    // -------------------- SETTER & GETTER TRANSFERED MONEY --------------------
    public int getTrans_money() {
        return trans_money;
    }
    public void setTrans_money(int trans_money) {
        this.trans_money = trans_money;
    }

}

