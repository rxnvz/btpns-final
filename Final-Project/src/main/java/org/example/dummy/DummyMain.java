package org.example.dummy;

import org.example.dummy.rabbitmq.DummyReceive;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DummyMain {
    public static DummyReceive recv = new DummyReceive();

    public static void main(String[] args) {
        SpringApplication.run(DummyMain.class, args);
        try {
            recv.transferedMoney();
        } catch (Exception e) {
            System.out.println("Error DatabaseMain = " + e);
        }
    }
}
