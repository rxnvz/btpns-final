package org.example.dummy.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DummyReceive {
    String saldoDummy;
    protected String message = "";
    private String saldoResponse = "";
    private String loginResponse = "";
    private boolean success=false;

    public void setSaldoDummy(String saldoDummy) {
        this.saldoDummy = saldoDummy;
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

    public String getSaldoDummy() {
        return this.saldoDummy;
    }
}
