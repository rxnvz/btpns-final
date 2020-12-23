package org.example.dummy.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class DummySend {
    public void newPemilik(String dmStr) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection con = factory.newConnection();
             Channel channel = con.createChannel()) {
            channel.queueDeclare("newDummy", false, false, false, null);
            channel.basicPublish("", "newDummy", null, dmStr.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] SEND REGISTER: '" + dmStr + "'");
        }
    }

    public void getSaldo(String username) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection con = factory.newConnection();
             Channel channel = con.createChannel()) {
            channel.queueDeclare("checkSaldo", false, false, false, null);
            channel.basicPublish("", "checkSaldo", null, username.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] SEND CHECK SALDO DUMMY '" + username + "'");
        }
    }
}
