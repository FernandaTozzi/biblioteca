package com.grupo04.Biblioteca.controllers;

import com.grupo04.Biblioteca.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // ainda sem integração
        return ResponseEntity.ok("Login recebido com sucesso");
    }
}