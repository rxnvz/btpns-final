package org.example.dummy.rabbitmq;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.example.db.models.Transaksi;
import org.example.db.service.DummyDAO;
import org.example.db.service.NasabahDAO;
import org.json.simple.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DummyReceive {
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private EntityManager entityManager;
    private DummyDAO dummyDAO;
    String saldoDummy;
    protected String message = "";
    private String saldoResponse = "";
    private String loginResponse = "";
    private String logoutResponse = "";
    private boolean success=false;

    // --------------------------------- CONNECTION ---------------------------------
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





    // --------------------------------- GETTER SETTER ---------------------------------
    public void setSaldoDummy(String saldoDummy) {
        this.saldoDummy = saldoDummy;
    }
    public String getSaldoDummy() {
        return this.saldoDummy;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getSaldoResponse() {
        return saldoResponse;
    }
    public void setSaldoResponse(String saldoResponse) {
        this.saldoResponse = saldoResponse;
    }





    // --------------------------------- BALIKAN SALDO ---------------------------------
    public String recvSaldoFromDB() throws IOException, TimeoutException {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received Saldo '" + message + "'");
                this.message = message;
                this.success=true;
            };
            channel.basicConsume("checkDummy", true, deliverCallback, consumerTag -> {
            });
            while (!success){
                TimeUnit.MILLISECONDS.sleep(10);
            }
            if (!this.message.equals("0")) {
                JSONObject object = new JSONObject();
                object.put("response", 200);
                object.put("status", "Success");
                object.put("message", this.message);
                saldoResponse = object.toJSONString();
                success = false;
            } else {
                JSONObject object = new JSONObject();
                object.put("response", 400);
                object.put("status", "Error");
                object.put("message", "Anda Tidak memiliki Akses untuk cek saldo, Mohon Login Terlebih Dahulu.");
                saldoResponse = object.toJSONString();
                success = false;
            }
        } catch (Exception e) {
            System.out.println("Exception login Res: " + e);
        }
        System.out.println("isi saldo response: " + this.getSaldoResponse());
        return this.getSaldoResponse();

    }

    // --------------------------------- BALIKAN LOGIN ---------------------------------
    public String loginAPI() throws IOException, TimeoutException {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare("loginFromDBD", false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Data Received '" + message + "'");
                loginResponse = response(message);
            };
            channel.basicConsume("loginFromDBD", true, deliverCallback, consumerTag -> { });

        } catch (Exception e) {
            System.out.println("ERROR DATA LOGIN APIRecv: " + e);
        }
        while (loginResponse.equals("")) {
            try {
                Thread.sleep(0);
            } catch (Exception e) {
                System.out.println("ERROR DATA Thread sleep: " + e);
            }
        }
        return loginResponse;
    }
    public String response(String message) {
        String loginResponse = "";
        try {
            if (!message.equals("0")) {
                JSONObject object = new JSONObject();
                object.put("response", 200);
                object.put("status", "Success");
                object.put("message", "Success Login");
                loginResponse = object.toJSONString();
            } else {
                JSONObject object = new JSONObject();
                object.put("response", 400);
                object.put("status", "Error");
                object.put("message", "Gagal Login. Mohon dicek kembali username dan password anda");
                loginResponse = object.toJSONString();
            }
        } catch (Exception e) {
            System.out.println("ERROR DATA LOGIN APIRecv: " + e);
        }
        return loginResponse;
    }

    // --------------------------------- BALIKAN LOGOUT ---------------------------------
    public String logoutDummy() throws IOException, TimeoutException {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare("logoutFromDBD", false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Data Received '" + message + "'");
                logoutResponse = logoutRes(message);
            };
            channel.basicConsume("logoutFromDBD", true, deliverCallback, consumerTag -> { });

        } catch (Exception e) {
            System.out.println("ERROR DATA LOGOUT APIRecv: " + e);
        }
        while (logoutResponse.equals("")) {
            try {
                Thread.sleep(0);
            } catch (Exception e) {
                System.out.println("ERROR DATA Thread sleep: " + e);
            }
        }
        return logoutResponse;
    }
    public String logoutRes(String message) {
        String logoutResponse = "";
        try {
            if (!message.equals("0")) {
                JSONObject object = new JSONObject();
                object.put("response", 200);
                object.put("status", "Success");
                object.put("message", "Success Logout");
                logoutResponse = object.toJSONString();
            } else {
                JSONObject object = new JSONObject();
                object.put("response", 400);
                object.put("status", "Error");
                object.put("message", "Gagal Logout:(");
                logoutResponse = object.toJSONString();
            }
        } catch (Exception e) {
            System.out.println("ERROR DATA LOGIN APIRecv: " + e);
        }
        return logoutResponse;
    }

    // --------------------------------- TERIMA UANG ---------------------------------
    public void transferedMoney() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("transferDummy", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String nbString = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received Transfer'" + nbString + "'");
                Transaksi tr = new Gson().fromJson(nbString, Transaksi.class);
                conD();
                dummyDAO.transfered(nbString);
                com();
            };
            channel.basicConsume("transferDummy", true, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            System.out.println("ERROR REGISTRASI NASABAH = " + e);
        }
    }
}
