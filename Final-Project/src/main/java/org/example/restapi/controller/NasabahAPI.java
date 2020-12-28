package org.example.restapi.controller;

import com.google.gson.Gson;
import org.example.db.models.Mutasi;
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
public class NasabahAPI {

    APISend send = new APISend();
    public final APIReceive recv = new APIReceive();

    @PostMapping("/register")
    public ResponseEntity<?> newNasabah (@RequestBody Nasabah nb) {
        try {
            send.newNasabah(new Gson().toJson(nb));
            JSONObject object = new JSONObject();
            object.put("response",200);
            object.put("status","Success");
            object.put("message","Berhasil menambah data ke database");
            return new ResponseEntity<>(object, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR REGISTER: " + e);
            JSONObject object = new JSONObject();
            object.put("response",400);
            object.put("status","Error");
            object.put("message","Error tambah data");
            return new ResponseEntity<>(object, HttpStatus.OK);
        }
    }

    @PutMapping("/login")
    public ResponseEntity<?> doLogin (@RequestBody Nasabah nb) {
        try {
            send.doLogin(new Gson().toJson(nb));
            Thread.sleep(2300);
            return new ResponseEntity<>(recv.loginAPI(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR LOGIN: " + e);
            JSONObject object = new JSONObject();
            object.put("response",400);
            object.put("status","Error");
            object.put("message","Error Login, Please Check Username or Password!!!");
            return new ResponseEntity<>(object, HttpStatus.OK);
        }
    }

    @PutMapping("/logout/{username}")
    public ResponseEntity<?> doLogout(@PathVariable("username") String nb) {
        try {
            send.doLogout(nb);
            Thread.sleep(2300);
            return new ResponseEntity<>(recv.logoutAPI(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR LOGOUT: " + e);
            JSONObject object = new JSONObject();
            object.put("response",400);
            object.put("status","Error");
            object.put("message","Error Logout");
            return new ResponseEntity<>(object, HttpStatus.OK);
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
            System.out.println("ERROR GET SALDO: " + e);
            JSONObject object = new JSONObject();
            object.put("response",400);
            object.put("status","Error");
            object.put("message","Error Cek Saldo");
            return new ResponseEntity<>(object, HttpStatus.OK);
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> doTransfer (@RequestBody Transaksi tr) {
        try {
            send.doTransfer(new Gson().toJson(tr));
            JSONObject object = new JSONObject();
            object.put("response",200);
            object.put("status","Success");
            object.put("message","Berhasil melakukan transfer");
            return new ResponseEntity<>(object, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR TRANSFER: " + e);
            JSONObject object = new JSONObject();
            object.put("response",400);
            object.put("status","Error");
            object.put("message","Error Transfer");
            return new ResponseEntity<>(object, HttpStatus.OK);
        }
    }

    @PostMapping("/mutasi")
    public ResponseEntity<?> getMutasi(@RequestBody Mutasi mt) {
        try {
            send.getMutasi(new Gson().toJson(mt));
            String resp = recv.mutasiFromDB();
            while (resp == null || resp.equals("")) {
                Thread.sleep(500);
            }
            System.out.println("isi response: " + resp);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR GET MUTASI: " + e);
            return new ResponseEntity<>("Data tidak ditemukan.", HttpStatus.NOT_FOUND);
        }
    }
}
