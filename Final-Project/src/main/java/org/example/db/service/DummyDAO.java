package org.example.db.service;

import com.google.gson.Gson;
import org.example.db.models.DummyBank;
import org.example.db.models.Transaksi;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class DummyDAO {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public DummyDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityTransaction = this.entityManager.getTransaction();
    }

    public void regisDummy (String dmString) {
        DummyBank db = new Gson().fromJson(dmString, DummyBank.class);
        db.setSaldo_dummy(3000000);
        entityManager.persist(db);
    }

    public int getSaldo(String nb) {
        String query = "SELECT saldo_dummy FROM DummyBank WHERE username=:username";
        Query q = entityManager.createQuery(query);
        q.setParameter("username", nb);
        System.out.println("Isi res: " + q.getResultList().get(0));
        if (q.getResultList().size()!=0){
            return Integer.parseInt(String.valueOf(q.getResultList().get(0)));
        } else {
            return q.getResultList().size();
        }
    }

    public int checkUser(String username) {
        String select = "SELECT id_nasabah FROM DummyBank WHERE username=:username AND loginStatus=:loginStatus";
        Query q = entityManager.createQuery(select);
        q.setParameter("username", username);
        q.setParameter("loginStatus", "true");

        if (q.getResultList().size() != 0) {
            return (int) q.getResultList().get(0);
        } else {
            return q.getResultList().size();
        }
    }

    public String login(String nbStr) {
        DummyBank nb = new Gson().fromJson(nbStr, DummyBank.class);
        String select = "SELECT no_rek FROM DummyBank WHERE username=:username AND password=:password";
        Query query = entityManager.createQuery(select);
        query.setParameter("username", nb.getUsername());
        query.setParameter("password", nb.getPassword());
        if (query.getResultList().size() != 0) {
            return (String) query.getResultList().get(0);
        } else {
            return String.valueOf(query.getResultList().size());
        }
    }

    public String logout(String username) {
        String select = "SELECT no_rek FROM DummyBank WHERE username=:username AND loginStatus=:loginStatus";
        Query q = entityManager.createQuery(select);
        q.setParameter("username", username);
        q.setParameter("loginStatus", "true");

        if (q.getResultList().size() != 0) {
            return (String) q.getResultList().get(0);
        } else {
            return String.valueOf(q.getResultList().size());
        }
    }

    public int updateStatus(DummyBank nb, String stats) {
        DummyBank nbh = entityManager.find(DummyBank.class, nb.getNo_rek());
        nbh.setLoginStatus(stats);
        entityManager.merge(nbh);
        return 1;
    }

    public void transfered(String trString) {
        Transaksi tr = new Gson().fromJson(trString, Transaksi.class);

        if (tr.getKode_transaksi().equals("014")) {
            DummyBank db = entityManager.find(DummyBank.class, tr.getRekening_tujuan());
            if (db != null) {
                db.setSaldo_dummy(db.getSaldo_dummy() + tr.getTrans_money());
                entityManager.merge(db);
                System.out.println("Ini dalem bank BCA");
            }
            System.out.println("Rekening tidak ditemukan");
        } else if (tr.getKode_transaksi().equals("008")) {
            DummyBank db = entityManager.find(DummyBank.class, tr.getRekening_tujuan());
            if (db != null) {
                db.setSaldo_dummy(db.getSaldo_dummy() + tr.getTrans_money());
                entityManager.merge(db);
                System.out.println("Ini dalem bank Mandiri");
            }
            System.out.println("Rekening tidak ditemukan");
        } else {
            System.out.println("Bank tidak ditemukan");
        }

    }
}
