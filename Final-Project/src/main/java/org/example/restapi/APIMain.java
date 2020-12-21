package org.example.restapi;

import org.example.restapi.rabbitmq.APIReceive;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class APIMain {
    public static void main(String[] args) {
        SpringApplication.run(APIMain.class, args);

//        APIReceive recv = new APIReceive();
//        try {
//            recv.receiveFromDB();
//            recv.getSaldo();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }
}
