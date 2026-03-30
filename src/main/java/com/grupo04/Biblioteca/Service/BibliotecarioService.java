package com.grupo04.Biblioteca.Service;

import com.grupo04.Biblioteca.models.BibliotecarioModel;
import com.grupo04.Biblioteca.repository.BibliotecarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDate;
import java.util.List;

@Service
public class BibliotecarioService {

    @Autowired
    private BibliotecarioRepository repository;

    @Autowired
    private JavaMailSender mailSender;

    public void verificarAniversarios() {
        List<BibliotecarioModel> lista = repository.findAll();
        LocalDate hoje = LocalDate.now();

        for (BibliotecarioModel b : lista) {
            if (b.getDtNascimento() != null) {

                LocalDate nascimento = b.getDtNascimento().toLocalDate();

                if (nascimento.getDayOfMonth() == hoje.getDayOfMonth() &&
                    nascimento.getMonth() == hoje.getMonth()) {

                    enviarEmail(
                        b.getDsEmail(),
                        "Feliz aniversário 🎉",
                        "Parabéns, " + b.getNmBibliotecario() + "!"
                    );
                }
            }
        }
    }

    @Async
    public void enviarEmail(String para, String assunto, String mensagem) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(para);
        mail.setSubject(assunto);
        mail.setText(mensagem);

        mailSender.send(mail);
    }
}