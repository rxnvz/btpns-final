package org.example.db.service;

import com.google.gson.Gson;
import org.example.db.models.Mutasi;
import org.example.db.models.Nasabah;
import org.example.db.models.Transaksi;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.security.SecureRandom;
import java.util.List;

public class NasabahDAO {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    Nasabah query;

    public NasabahDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityTransaction = this.entityManager.getTransaction();
    }

    public void regisNB(String nbString) {
        Nasabah nb = new Gson().fromJson(nbString, Nasabah.class);
        nb.setSaldo(5000000);
        entityManager.persist(nb);

        Transaksi tr = new Transaksi();
        tr.setTipe_transaksi("Uang Masuk");
        tr.setKode_transaksi("200");
        tr.setTrans_money(nb.getSaldo());
        tr.setId_nasabah(nb.getId_nasabah());
        entityManager.persist(tr);
    }

    public int login(String nbStr) {
        Nasabah nb = new Gson().fromJson(nbStr, Nasabah.class);
        String select = "SELECT id_nasabah FROM Nasabah WHERE username=:username AND password=:password";
        Query query = entityManager.createQuery(select);
        query.setParameter("username", nb.getUsername());
        query.setParameter("password", nb.getPassword());
        if (query.getResultList().size() != 0) {
            return (int)query.getResultList().get(0);
        } else {
            return query.getResultList().size();
        }
    }

    public int updateStatus(Nasabah nb, String stats) {
        Nasabah nbh = entityManager.find(Nasabah.class, nb.getId_nasabah());
        nbh.setLoginStatus(stats);
        entityManager.merge(nbh);
        return 1;
    }

    public int logout(String nbStr) {
        Nasabah nb = new Gson().fromJson(nbStr, Nasabah.class);
        String select = "SELECT id_nasabah FROM Nasabah WHERE username=:username";
        Query q = entityManager.createQuery(select);
        q.setParameter("username", nb.getUsername());

        if (q.getResultList().size() != 0) {
            return (int)q.getResultList().get(0);
        } else {
            return q.getResultList().size();
        }
    }

    public int checkUser(String username) {
        String select = "SELECT id_nasabah FROM Nasabah WHERE username=:username AND loginStatus=:loginStatus";
        Query q = entityManager.createQuery(select);
        q.setParameter("username", username);
        q.setParameter("loginStatus", "true");

        if (q.getResultList().size() != 0) {
            return (int) q.getResultList().get(0);
        } else {
            return q.getResultList().size();
        }
    }

    public int userCheckId(String trStr) {
        Transaksi tr = new Gson().fromJson(trStr, Transaksi.class);
        String select = "SELECT id_nasabah FROM Nasabah WHERE id_nasabah=:id_nasabah AND loginStatus=:loginStatus";
        Query q = entityManager.createQuery(select);
        q.setParameter("id_nasabah", tr.getId_nasabah());
        q.setParameter("loginStatus", "true");

        if (q.getResultList().size() != 0) {
            return (int) q.getResultList().get(0);
        } else {
            return q.getResultList().size();
        }
    }

    public int userCheckIdMutasi(String mutasi) {
        Mutasi mt = new  Gson().fromJson(mutasi, Mutasi.class);
        String select = "SELECT id_nasabah FROM Nasabah WHERE id_nasabah=:id_nasabah AND loginStatus=:loginStatus";
        Query q = entityManager.createQuery(select);
        q.setParameter("id_nasabah", mt.getId_nasabah());
        q.setParameter("loginStatus", "true");

        if (q.getResultList().size() != 0) {
            return (int) q.getResultList().get(0);
        } else {
            return q.getResultList().size();
        }
    }

    public int getSaldo(Nasabah nb) {
        String query = "SELECT saldo FROM Nasabah WHERE id_nasabah=:id_nasabah";
        Query q = entityManager.createQuery(query);
        q.setParameter("id_nasabah", nb.getId_nasabah());
        if (q.getResultList().size()!=0){
            return (int)q.getResultList().get(0);
        } else {
            return q.getResultList().size();
        }
    }

    public void doTransfer(String trString) {
        Transaksi tr = new Gson().fromJson(trString, Transaksi.class);
        tr.setTipe_transaksi("Transfer Uang");
        entityManager.persist(tr);

        Nasabah nb = entityManager.find(Nasabah.class, tr.getId_nasabah());
        nb.setSaldo(nb.getSaldo()-tr.getTrans_money());
        entityManager.merge(nb);

        Nasabah nbTax = entityManager.find(Nasabah.class, tr.getId_nasabah());
        nbTax.setSaldo(nbTax.getSaldo() - 6500);

        Transaksi tax = new Transaksi();
        tax.setTipe_transaksi("Biaya Admin");
        tax.setKode_transaksi("200");
        tax.setTrans_money(6500);
        tax.setId_nasabah(tr.getId_nasabah());
        entityManager.persist(tax);
    }

//    public List<Nasabah> getMutasi(String mutasi) {
//        Mutasi mt = new Gson().fromJson(mutasi, Mutasi.class);
//        String select = "SELECT " +
//                "n.id_nasabah, n.nama_lengkap, n.email, n.username, n.password, " +
//                "n.gender, n.no_ktp, n.birth_date, n.no_telp, n.alamat, n.saldo, n.loginStatus, " +
//                "t.kode_transaksi, t.rekening_tujuan, t.transaction_date, t.tipe_transaksi, t.trans_money " +
//                "FROM nasabah AS n " +
//                "INNER JOIN transaksi AS t " +
//                "ON n.id_nasabah=:id_nasabah AND t.id_nasabah=:id_nasabah " +
//                "WHERE (t.transaction_date BETWEEN :begin_date AND :end_date) ";
//        Query q = entityManager.createQuery(select);
//        q.setParameter("id_nasabah", mt.getId_nasabah());
//        q.setParameter("begin_date", mt.getStart_mutasi());
//        q.setParameter("end_date", mt.getEnd_mutasi());
//        return q.getResultList();
//    }
}
