package org.example.db.service;

import com.google.gson.Gson;
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
        tr.setTrans_money(nb.getSaldo());
        tr.setId_nasabah(nb.getId_nasabah());
        entityManager.persist(tr);
    }

//    public Nasabah findUser(String nbString) {
//        try {
//            Nasabah nb = new Gson().fromJson(nbString, Nasabah.class);
//            query = entityManager.createQuery("SELECT n FROM Nasabah n WHERE n.username='" + nb.getUsername() + "'", Nasabah.class).getSingleResult();
//        } catch (Exception e) {
//            System.out.println("ERROR find user " + e);
//        }
//        return query;
//    }
//
//    public List<Nasabah> getData() {
//        return entityManager.createQuery("SELECT saldo FROM Nasabah", Nasabah.class).getResultList();
//    }

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
}
