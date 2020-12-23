package org.example.db.rabbitmq;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.example.db.models.DummyBank;
import org.example.db.models.Mutasi;
import org.example.db.models.Nasabah;
import org.example.db.models.Transaksi;
import org.example.db.service.DummyDAO;
import org.example.db.service.NasabahDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class DBReceive {
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private EntityManager entityManager;
    private NasabahDAO naDao;
    private DummyDAO dummyDAO;
    private Boolean loginStatus = false;
    List<Nasabah> nbMutasi;

    @Autowired
    private DBSend send = new DBSend();

    public void con() {
        this.entityManager = Persistence
                .createEntityManagerFactory("user-unit")
                .createEntityManager();
        naDao = new NasabahDAO(entityManager);
        try {
            entityManager.getTransaction().begin();
        } catch (IllegalStateException e) {
            entityManager.getTransaction().rollback();
        }
    }
    public void conD() {
        this.entityManager = Persistence
                .createEntityManagerFactory("user-unit")
                .createEntityManager();
        dummyDAO = new DummyDAO(entityManager);
        try {
            entityManager.getTransaction().begin();
        } catch (IllegalStateException e) {
            entityManager.getTransaction().rollback();
        }
    }
    public void com() {
        try {
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (IllegalStateException e) {
            entityManager.getTransaction().rollback();
        }
    }
    public void connectRabbitMQ() throws IOException, TimeoutException {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
    }

    // ---------------------------- DB RECEIVE FOR NASABAH CLASS ----------------------------
    public void newNasabah() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("newNasabah", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String nbString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + nbString + "'");
                con();
                naDao.regisNB(nbString);
                com();
            };
            channel.basicConsume("newNasabah", true, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            System.out.println("ERROR REGISTRASI NASABAH = " + e);
        }
    }
    public void doLogin() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("doLogin", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String loginStr = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + loginStr + "'");
                con();
                int res = naDao.login(loginStr);
                if (res != 0) {
                    Nasabah nb = new Nasabah();
                    nb.setId_nasabah(res);
                    naDao.updateStatus(nb, "true");
                }
                try {
                    send.sendLogintoAPI(String.valueOf(res));
                } catch (Exception e) {
                    System.out.println("ERROR SEND TO API DATA LOGIN: " + e);
                }
                com();
            };
            channel.basicConsume("doLogin", true, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            System.out.println("ERROR LOGIN = " + e);
        }
    }
    public void getSaldo() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("getSaldo", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String username = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + username + "'");
                con();
                int res = naDao.checkUser(username);
                String isi_saldo = "";
                if (res != 0){
                    Nasabah nb = new Nasabah();
                    nb.setId_nasabah(res);
                    System.out.println("Cek Saldo Status: "+res);
                    int saldo = naDao.getSaldo(nb);
                    System.out.println("Isi saldo: " + saldo);
                    isi_saldo = String.valueOf(saldo);
                } else {
                    isi_saldo="0";
                }
                try {
                    send.sendSaldoToAPI(isi_saldo);
                } catch (Exception e) {
                    System.out.println("ERROR SEND TO API GET SALDO: " + e);
                }
                com();
            };
            channel.basicConsume("getSaldo", true, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            System.out.println("ERROR GET SALDO = " + e);
        }
    }
//    public void getMutasi() {
//        try {
//            connectRabbitMQ();
//            channel = connection.createChannel();
//            channel.queueDeclare("getMutasi", false, false, false, null);
//            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//                String mutasi = new String(delivery.getBody(), StandardCharsets.UTF_8);
//                System.out.println(" [x] Received '" + mutasi + "'");
//                con();
//                int res = naDao.userCheckIdMutasi(mutasi);
//                if (res != 0){
//                    nbMutasi = naDao.getMutasi(mutasi);
//                } else {
//                    nbMutasi.isEmpty();
//                }
//                try {
//                    send.sendMutasiToAPI(new Gson().toJson(nbMutasi));
//                } catch (Exception e) {
//                    System.out.println("ERROR SEND TO API GET SALDO: " + e);
//                }
//                com();
//            };
//            channel.basicConsume("getMutasi", true, deliverCallback, consumerTag -> { });
//        } catch (Exception e) {
//            System.out.println("ERROR GET SALDO = " + e);
//        }
//    }
    public void doLogout() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("doLogout", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String logoutStr = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + logoutStr + "'");
                con();
                int res = naDao.logout(logoutStr);
                if (res!=0) {
                    Nasabah nb = new Nasabah();
                    nb.setId_nasabah(res);
                    naDao.updateStatus(nb, "false");
                }
                try {
                    send.sendLogouttoAPI(String.valueOf(res));
                } catch (Exception e) {
                    System.out.println("ERROR SEND TO API DATA LOGIN: " + e);
                }
                com();
            };
            channel.basicConsume("doLogout", true, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            System.out.println("ERROR LOGOUT = " + e);
        }
    }

    // ---------------------------- DB RECEIVE FOR DUMMYBANK CLASS ----------------------------
    public void newRekening() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("newDummy", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String dbString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received New Dummy: '" + dbString + "'");
                conD();
                dummyDAO.regisDummy(dbString);
                com();
            };
            channel.basicConsume("newDummy", true, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            System.out.println("ERROR REGISTRASI DUMMY = " + e);
        }
    }
    public void checkSaldoDummy() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("checkSaldo", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String rekening = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received Cek Saldo Dummy: '" + rekening + "'");
                conD();
                try {
                    List<DummyBank> dumdum = dummyDAO.checkSaldoDummy(rekening);
                    send.sendToDummy(new Gson().toJson(dumdum));
                } catch ( Exception e ) {
                    System.out.println("Error send Check Saldo Dummy = " + e);
                }
                com();
            };
            channel.basicConsume("checkSaldo", true, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            System.out.println("Error checkSaldoDummy= " + e);
        }
    }

    // ---------------------------- DB RECEIVE FOR TRANSFER ----------------------------

    public void doTransfer() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("doTransfer", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String nbString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received Transfer'" + nbString + "'");
                con();
                int res = naDao.userCheckId(nbString);
                if (res != 0) {
                    naDao.doTransfer(nbString);
                    com();
                    conD();
                    dummyDAO.transfered(nbString);
                }
                com();
            };
            channel.basicConsume("doTransfer", true, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            System.out.println("ERROR REGISTRASI NASABAH = " + e);
        }
    }
}
