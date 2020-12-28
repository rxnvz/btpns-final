package org.example.restapi.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.util.concurrent.TimeoutException;

public class APISend {
    public void newNasabah(String nbStr) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection con = factory.newConnection();
             Channel channel = con.createChannel()) {
            channel.queueDeclare("newNasabah", false, false, false, null);
            channel.basicPublish("", "newNasabah", null, nbStr.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] SEND REGISTER: '" + nbStr + "'");
        }
    }

    public void doLogin(String loginStr) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection con = factory.newConnection();
             Channel channel = con.createChannel()) {
            channel.queueDeclare("doLogin", false, false, false, null);
            channel.basicPublish("", "doLogin", null, loginStr.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] SEND LOGIN: '" + loginStr + "'");
        }
    }

    public void doLogout(String logoutStr) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection con = factory.newConnection();
             Channel channel = con.createChannel()) {
            channel.queueDeclare("doLogout", false, false, false, null);
            channel.basicPublish("", "doLogout", null, logoutStr.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] SEND LOGOUT '" + logoutStr + "'");
        }
    }

    public void getSaldo(String username) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection con = factory.newConnection();
             Channel channel = con.createChannel()) {
            channel.queueDeclare("getSaldo", false, false, false, null);
            channel.basicPublish("", "getSaldo", null, username.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] SEND GET DATA SALDO '" + username + "'");
        }
    }

    public void doTransfer(String tfStr) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection con = factory.newConnection();
             Channel channel = con.createChannel()) {
            channel.queueDeclare("doTransfer", false, false, false, null);
            channel.basicPublish("", "doTransfer", null, tfStr.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] SEND TRANSFER: '" + tfStr + "'");
        }
    }

    public void getMutasi(String mutasi) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection con = factory.newConnection();
             Channel channel = con.createChannel()) {
            channel.queueDeclare("getMutasi", false, false, false, null);
            channel.basicPublish("", "getMutasi", null, mutasi.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] SEND MUTASI: '" + mutasi + "'");
        }
    }

}
