package org.example.restapi.rabbitmq;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.example.db.models.History;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class APIReceive {
    protected String message = "";
    private String saldoResponse = "";
    private String loginResponse = "";
    private String logoutResponse = "";
    private String mutasiResponse = "";
    private boolean success=false;

    // --------------------------------- GETTER SETTER ---------------------------------
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

    public String getMutasiResponse() {
        return mutasiResponse;
    }
    public void setMutasiResponse(String mutasiResponse) {
        this.mutasiResponse = mutasiResponse;
    }




    // --------------------------------- BALIKAN MUTASI ---------------------------------
    public String mutasiFromDB() throws IOException, TimeoutException {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                this.message = message;
                success = true;
            };
            channel.basicConsume("sendMutasi", true, deliverCallback, consumerTag -> { });
            while (!success){
                TimeUnit.MILLISECONDS.sleep(10);
            }
            if (!this.message.equals("0")) {
                JSONObject object = new JSONObject();
                object.put("response", 200);
                object.put("status", "Success");
                object.put("message", "Berhasil ambil data");
                object.put("data", new JsonParser().parse(this.getMessage()));
                mutasiResponse = object.toJSONString();
                success = false;
            } else {
                JSONObject object = new JSONObject();
                object.put("response", 400);
                object.put("status", "Error");
                object.put("message", "Anda Tidak memiliki Akses untuk cek mutasi, Mohon Login Terlebih Dahulu.");
                object.put("data", null);
                mutasiResponse = object.toJSONString();
                success = false;
            }
        } catch (Exception e) {
            System.out.println("Exception get mutasi Res: " + e);
        }
        System.out.println("Isi mutasi: " + this.getMutasiResponse());
        return this.getMutasiResponse();
    }

    // --------------------------------- BALIKAN GET SALDO ---------------------------------
    public String getSaldo() throws IOException, TimeoutException {
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
            channel.basicConsume("messageFromSaldo", true, deliverCallback, consumerTag -> {
            });
            while (!success){
                TimeUnit.MILLISECONDS.sleep(10);
            }
            if (!this.message.equals("0")) {
                JSONObject object = new JSONObject();
                object.put("response", 200);
                object.put("status", "Success");
                object.put("message", this.message);
//                object.put("Saldo", this.message);
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

            channel.queueDeclare("loginFromDB", false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Data Received '" + message + "'");
                loginResponse = response(message);
            };
            channel.basicConsume("loginFromDB", true, deliverCallback, consumerTag -> { });

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
    public String logoutAPI() throws IOException, TimeoutException {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare("logoutFromDB", false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Data Received '" + message + "'");
                logoutResponse = responseLogout(message);
            };
            channel.basicConsume("logoutFromDB", true, deliverCallback, consumerTag -> { });

        } catch (Exception e) {
            System.out.println("ERROR DATA LOGOUT APIRECEIVE: " + e);
        }
        while (logoutResponse.equals("")) {
            try {
                Thread.sleep(0);
            } catch (Exception e) {
                System.out.println("ERROR DATA Thread sleep logout: " + e);
            }
        }
        return logoutResponse;
    }
    public String responseLogout(String message) {
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
                object.put("message", "Kamu sudah keluar dari aplikasi");
                logoutResponse = object.toJSONString();
            }
        } catch (Exception e) {
            System.out.println("ERROR DATA LOGIN APIRecv: " + e);
        }
        return logoutResponse;
    }
}
