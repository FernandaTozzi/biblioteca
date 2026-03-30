package com.grupo04.Biblioteca.scheduler;

import com.grupo04.Biblioteca.Service.BibliotecarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AniversarioScheduler {

    @Autowired
    private BibliotecarioService service;

    @Scheduled(cron = "0 0 8 * * ?")
    public void executar() {
        service.verificarAniversarios();
    }
}