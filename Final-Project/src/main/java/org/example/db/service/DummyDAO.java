package org.example.db.service;

import com.google.gson.Gson;
import org.example.db.models.DummyBank;
import org.example.db.models.Transaksi;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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

    public List<DummyBank> checkSaldoDummy(String rekening) {
        return entityManager.createQuery("SELECT d FROM DummyBank d WHERE d.no_rek='" + rekening + "'", DummyBank.class).getResultList();
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
