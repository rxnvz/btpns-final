package org.example.dummy.controller;

import com.google.gson.Gson;
import org.example.db.models.DummyBank;
import org.example.dummy.rabbitmq.DummyReceive;
import org.example.dummy.rabbitmq.DummySend;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dum")
public class DummyAPI {
    DummySend send = new DummySend();
    DummyReceive recv = new DummyReceive();

    @PostMapping("/register")
    public ResponseEntity<?> newDummy (@RequestBody DummyBank db) {
        try {
            send.newPemilik(new Gson().toJson(db));
        } catch (Exception e) {
            System.out.println("ERROR REGISTER NEW DUMMY: " + e);
        }
        return new ResponseEntity<>("Registrasi berhasil!", HttpStatus.OK);
    }

    @GetMapping("/saldo/{rekening}")
    public ResponseEntity<?> checkSaldo(@PathVariable("rekening") String rekening) {
        try {
            send.getSaldo(rekening);
            String response = recv.recvSaldoFromDB();
            while (response == null || response.equals("")) {
                Thread.sleep(500);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR CHECK SALDO DUMMY: " + e);
        }
        return new ResponseEntity<>(recv.getSaldoDummy(), HttpStatus.OK);
    }
}
