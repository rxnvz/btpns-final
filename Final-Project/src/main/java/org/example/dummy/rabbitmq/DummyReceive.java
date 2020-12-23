package org.example.dummy.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DummyReceive {
    String saldoDummy;
    private boolean success=false;

    public void recvSaldoFromDB() throws IOException, TimeoutException {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare("checkDummy", false, false, false, null);
            System.out.println(" [*] Waiting for messages from database");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                this.saldoDummy = message;
                this.success=true;
            };
            channel.basicConsume("checkDummy", true, deliverCallback, consumerTag -> { });
            while (!success){
                TimeUnit.MILLISECONDS.sleep(10);
            }
            success = false;
        } catch (Exception e) {
            System.out.println("Exception recv saldo: " + e);
        }

    }

    public String getSaldoDummy() {
        return this.saldoDummy;
    }
}
