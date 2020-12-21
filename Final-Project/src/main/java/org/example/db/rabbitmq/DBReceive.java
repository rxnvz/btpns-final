package org.example.db.rabbitmq;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.apache.coyote.Response;
import org.example.db.models.Nasabah;
import org.example.db.service.NasabahDAO;
import org.example.db.service.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class DBReceive {
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private EntityManager entityManager;
    private NasabahDAO naDao;
    private final List<Session> session = new ArrayList<>();
    private Boolean loginStatus = false;

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
//                Nasabah user = new Gson().fromJson(loginStr, Nasabah.class);
//                naDao.findUser(loginStr);
//                for (Session obj: session) {
//                    if (obj.getUsername().equals(user.getUsername()) && obj.getPassword().equals(user.getPassword())) {
//                        loginStatus = true;
//                        break;
//                    }
//                }
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

    public void doLogout() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("doLogout", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String logoutStr = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + logoutStr + "'");
                con();
//                Nasabah user = new Gson().fromJson(logoutStr, Nasabah.class);
//                naDao.findUser(logoutStr);
//                boolean loginStatus = true;
//                for (Session obj: session) {
//                    if (obj.getUsername().equals(user.getEmail())) {
//                        loginStatus = false;
//                        session.clear();
//                        break;
//                    }
//                }
                int res = naDao.logout(logoutStr);
                if (res!=0) {
                    Nasabah nb = new Nasabah();
                    nb.setId_nasabah(res);
                    naDao.updateStatus(nb, "false");
                }
                com();
            };
            channel.basicConsume("doLogout", true, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            System.out.println("ERROR LOGOUT = " + e);
        }
    }
}
