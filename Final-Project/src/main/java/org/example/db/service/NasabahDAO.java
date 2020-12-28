package org.example.db.service;

import com.google.gson.Gson;
import org.example.db.models.*;

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
        nb.setLoginStatus("false");
        entityManager.persist(nb);

        Transaksi tr = new Transaksi();
        tr.setTipe_transaksi("Uang Masuk");
        tr.setKode_transaksi("200");
        tr.setTrans_money(nb.getSaldo());
        tr.setId_nasabah(nb.getId_nasabah());
        tr.setUsername(nb.getUsername());
        tr.setRekening_tujuan("Milik Sendiri");
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
        String select = "SELECT id_nasabah FROM Nasabah WHERE username=:username AND loginStatus=:loginStatus";
        Query q = entityManager.createQuery(select);
        q.setParameter("username", nbStr);
        q.setParameter("loginStatus", "true");

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

    public int userCheckId(String transaksi) {
        Transaksi tr = new Gson().fromJson(transaksi, Transaksi.class);
        String select = "SELECT id_nasabah FROM Nasabah WHERE username=:username AND loginStatus=:loginStatus";
        Query q = entityManager.createQuery(select);
        q.setParameter("username", tr.getUsername());
        q.setParameter("loginStatus", "true");

        if (q.getResultList().size() != 0) {
            return (int) q.getResultList().get(0);
        } else {
            return q.getResultList().size();
        }
    }

    public int userCheckIdMutasi(String mutasi) {
        Mutasi mt = new  Gson().fromJson(mutasi, Mutasi.class);
        String select = "SELECT id_nasabah FROM Nasabah WHERE username=:username AND loginStatus=:loginStatus";
        Query q = entityManager.createQuery(select);
        q.setParameter("username", mt.getUsername());
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

        String select = "SELECT no_rek FROM DummyBank WHERE no_rek=:no_rek";
        Query q = entityManager.createQuery(select);
        q.setParameter("no_rek", tr.getRekening_tujuan());

        if (q.getResultList().size() != 0) {
            tr.setTipe_transaksi("Transfer Uang");
            entityManager.persist(tr);

            Nasabah nb = entityManager.find(Nasabah.class, tr.getId_nasabah());
            nb.setSaldo(nb.getSaldo()-tr.getTrans_money());
            entityManager.merge(nb);

            Nasabah nbTax = entityManager.find(Nasabah.class, tr.getId_nasabah());
            nbTax.setSaldo(nbTax.getSaldo() - 6500);
            entityManager.merge(nbTax);

            Transaksi tax = new Transaksi();
            tax.setTipe_transaksi("Biaya Admin");
            tax.setKode_transaksi("200");
            tax.setTrans_money(6500);
            tax.setRekening_tujuan("Milik sendiri");
            tax.setId_nasabah(tr.getId_nasabah());
            tax.setUsername(tr.getUsername());
            entityManager.persist(tax);
        }
    }

    public List<History> getMutasi(String mutasi) {
        Mutasi mt = new Gson().fromJson(mutasi, Mutasi.class);
        String select = "SELECT NEW org.example.db.models.History(" +
                "n.username, " +
//                "d.nama_pemilik, " +
                "t.rekening_tujuan, t.transaction_date, t.tipe_transaksi, t.trans_money) " +
                "FROM Nasabah AS n " +
                "INNER JOIN Transaksi AS t ON n.id_nasabah=t.id_nasabah " +
//                "INNER JOIN DummyBank AS d ON t.rekening_tujuan=d.no_rek " +
                "WHERE (t.transaction_date BETWEEN :begin_date AND :end_date) AND n.id_nasabah=:id_nasabah ";
        Query q = entityManager.createQuery(select, History.class);
        q.setParameter("id_nasabah", mt.getId_nasabah());
        q.setParameter("begin_date", mt.getStart_mutasi());
        q.setParameter("end_date", mt.getEnd_mutasi());
        return q.getResultList();
    }
}
