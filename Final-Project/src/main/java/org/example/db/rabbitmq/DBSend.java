package org.example.db.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class DBSend {
    public void sendSaldoToAPI (String message) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("messageFromSaldo", false, false, false, null);
//            String message = "Assalamualaikum";
            channel.basicPublish("", "messageFromSaldo", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            System.out.println("Gagal mengirim Saldo ke RestApi.." + e);
        }
    }

    public void sendLogintoAPI (String message) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("loginFromDB", false, false, false, null);
//            String message = "Assalamualaikum";
            channel.basicPublish("", "loginFromDB", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            System.out.println("Gagal mengirim Data Login ke RestApi.." + e);
        }
    }
}
