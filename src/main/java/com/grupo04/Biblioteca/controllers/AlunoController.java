package com.grupo04.Biblioteca.controllers;

import com.grupo04.Biblioteca.Service.EmailAoAlunoService;
import com.grupo04.Biblioteca.Service.EmailService;
import com.grupo04.Biblioteca.models.AlunoModel;
import com.grupo04.Biblioteca.repository.AlunoRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping(value = "/alunos")
@Tag(name = "Aluno")
public class AlunoController {

    @Autowired
    public AlunoRepository repository;
    @Autowired
    public EmailService emailService;

    @GetMapping
    public ResponseEntity<List<AlunoModel>> findAll() {
        List<AlunoModel> list = repository.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            AlunoModel aluno = repository.findById(id).orElseThrow(() -> {
                throw new InvalidParameterException("Aluno inexistente!");
            });
            return ResponseEntity.ok(aluno);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@Qualifier("EmailAoAlunoService") @RequestBody AlunoModel novoAluno) {
        try {
            repository.save(novoAluno);
            emailService.enviarEmail(novoAluno.getDs_email());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody AlunoModel aluno) {
        try {
            AlunoModel alunoAtual = repository.findById(aluno.getCd_aluno()).orElseThrow(() -> {
                throw new InvalidParameterException("Aluno inexistente!");
            });

            alunoAtual.setNm_aluno(aluno.getNm_aluno());
            alunoAtual.setDt_nascimento(aluno.getDt_nascimento());
            alunoAtual.setTp_sexo(aluno.getTp_sexo());
            alunoAtual.setDs_email(aluno.getDs_email());

            repository.save(alunoAtual);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/{atributo}/{novo}")
    public ResponseEntity<?> patch(@PathVariable Long id, @PathVariable String atributo, @PathVariable String novo) {
        AlunoModel aluno;
        try {
            aluno = repository.findById(id).orElseThrow(() -> {
                throw new InvalidParameterException("Aluno inexistente!");
            });
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        try {
            switch (atributo) {
                case "nome":
                    aluno.setNm_aluno(novo);
                    break;

                case "nascimento":
                    aluno.setDt_nascimento(java.time.LocalDateTime.parse(novo));
                    break;

                case "sexo":
                    aluno.setTp_sexo(novo.charAt(0));
                    break;

                case "email":
                    aluno.setDs_email(novo);
                    break;

                default:
                    throw new InvalidParameterException("Atributo inexistente!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        repository.save(aluno);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}