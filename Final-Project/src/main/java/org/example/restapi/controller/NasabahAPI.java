package org.example.restapi.controller;

import com.google.gson.Gson;
import org.example.db.models.Nasabah;
import org.example.db.models.Transaksi;
import org.example.restapi.rabbitmq.APIReceive;
import org.example.restapi.rabbitmq.APISend;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;

@RestController
@RequestMapping("/api")
public class NasabahAPI {

    APISend send = new APISend();
    public final APIReceive recv = new APIReceive();

    @PostMapping("/register")
    public ResponseEntity<?> newNasabah (@RequestBody Nasabah nb) {
        try {
            send.newNasabah(new Gson().toJson(nb));
        } catch (Exception e) {
            System.out.println("ERROR REGISTER: " + e);
        }
        return new ResponseEntity<>("Registrasi berhasil! Silahkan login", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> doLogin (@RequestBody Nasabah nb) {
        try {
            send.doLogin(new Gson().toJson(nb));
            Thread.sleep(2300);
            return new ResponseEntity<>(recv.loginAPI(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR LOGIN: " + e);
            return new ResponseEntity<>("Data tidak ditemukan. Cek kembali username dan password atau silahkan mendaftar.", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/logout")
    public ResponseEntity<?> doLogout(@RequestBody Nasabah nb) {
        try {
            send.doLogout(new Gson().toJson(nb));
            return new ResponseEntity<>("Berhasil keluar! Silahkan masuk kembali", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR LOGOUT: " + e);
            return new ResponseEntity<>("Anda sudah keluar.", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/home/{username}")
    public ResponseEntity<?> getData(@PathVariable("username") String username) {
        try {
            send.getSaldo(username);
            String response = recv.getSaldo();
            while (response == null || response.equals("")) {
                Thread.sleep(500);
            }
            System.out.println("isi response: " + response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR GET DATA: " + e);
            return new ResponseEntity<>("Data tidak ditemukan.", HttpStatus.NOT_FOUND);
        }

    }
}
