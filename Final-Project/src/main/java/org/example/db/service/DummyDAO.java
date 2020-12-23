package org.example.db.service;

import com.google.gson.Gson;
import org.example.db.models.DummyBank;
import org.example.db.models.Nasabah;
import org.example.db.models.Transaksi;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

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

    public int checkSaldoDummy(String rekening) {
        return entityManager.createQuery("SELECT saldo_dummy FROM DummyBank WHERE no_rek='" + rekening + "'", DummyBank.class).getFirstResult();
    }

    public int getSaldo(String nb) {
        String query = "SELECT saldo_dummy FROM DummyBank WHERE no_rek=:no_rek";
        Query q = entityManager.createQuery(query);
        q.setParameter("no_rek", nb);
        if (q.getResultList().size()!=0){
            return (int)q.getResultList().get(0);
        } else {
            return q.getResultList().size();
        }
    }

    public int login(String nbStr) {
        DummyBank nb = new Gson().fromJson(nbStr, DummyBank.class);
        String select = "SELECT no_rek FROM Nasabah WHERE username=:username AND password=:password";
        Query query = entityManager.createQuery(select);
        query.setParameter("username", nb.getUsername());
        query.setParameter("password", nb.getPassword());
        if (query.getResultList().size() != 0) {
            return (int)query.getResultList().get(0);
        } else {
            return query.getResultList().size();
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
        DummyBank db = entityManager.find(DummyBank.class, tr.getRekening_tujuan());
//        DummyBank db = new DummyBank();
        if (tr.getKode_transaksi().equals("014")) {
            db.setSaldo_dummy(db.getSaldo_dummy() + tr.getTrans_money());
            System.out.println("Ini dalem bank BCA");
        } else if (tr.getKode_transaksi().equals("008")) {
            db.setSaldo_dummy(db.getSaldo_dummy() + tr.getTrans_money());
            System.out.println("Ini dalem bank Mandiri");
        } else {
            System.out.println("Ga ketemu plz");
        }
        entityManager.merge(db);
    }
}
