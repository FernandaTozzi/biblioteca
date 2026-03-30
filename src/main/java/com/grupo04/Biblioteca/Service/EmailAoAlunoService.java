package com.grupo04.Biblioteca.Service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailAoAlunoService implements EmailService {

    @Async
    @Override
    public void enviarEmail(String email) throws Exception{
        System.out.println("Enviando email de boas-vindas para o seguinte endereço: " + email);
        Thread.sleep(2000);
        System.out.println("Email enviado com sucesso!");
    }
}
