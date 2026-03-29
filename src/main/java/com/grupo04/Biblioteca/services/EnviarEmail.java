package com.grupo04.Biblioteca.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EnviarEmail {

    @Async
    public void enviarEmail(String dsEmail, String nm_bibliotecario) {
        try {
            Thread.sleep(3000);

            System.out.println("E-mail enviado para: " + dsEmail);
            System.out.println("Bibliotecário: " + nm_bibliotecario);

        } catch (InterruptedException e) {
            System.out.println("Erro ao enviar o e-mail");
        }
    }
}