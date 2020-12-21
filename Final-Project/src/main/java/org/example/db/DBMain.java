package org.example.db;

import org.example.db.rabbitmq.DBReceive;

public class DBMain {
    public static DBReceive receive = new DBReceive();

    public static void main(String[] args) {
        try{
            System.out.println(" [*] Waiting for messages..");
            receive.newNasabah();
            receive.doLogin();
            receive.doLogout();
            receive.getSaldo();
        }catch (Exception e){
            System.out.println("Error DatabaseMain = " + e);
        }
    }
}
