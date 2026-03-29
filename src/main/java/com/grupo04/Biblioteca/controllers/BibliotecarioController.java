package com.grupo04.Biblioteca.controllers;

import com.grupo04.Biblioteca.dto.BibliotecarioDTO;
import com.grupo04.Biblioteca.models.BibliotecarioModel;
import com.grupo04.Biblioteca.repository.BibliotecarioRepository;
import com.grupo04.Biblioteca.security.SenhaCriptografada;
import com.grupo04.Biblioteca.services.EnviarEmail;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/bibliotecarios")
@Tag(name = "Bibliotecario")
public class BibliotecarioController {
    @Autowired
    public BibliotecarioRepository repository;

    @Autowired
    private EnviarEmail enviarEmail;


    @GetMapping
    public ResponseEntity<List<BibliotecarioDTO>> findAll() {
        List<BibliotecarioModel> list = repository.findAll();
        List<BibliotecarioDTO> listDTO = new ArrayList<>();

        list.forEach(bibliotecarioModel -> {
            listDTO.add(new BibliotecarioDTO(bibliotecarioModel.getCdBibliotecario(),
                    bibliotecarioModel.getNmBibliotecario(),
                    bibliotecarioModel.getDtNascimento(),
                    bibliotecarioModel.getTpSexo()));
        });
        return ResponseEntity.ok(listDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            BibliotecarioModel bibliotecario = repository.findById(id).orElseThrow(() -> {
                throw new InvalidParameterException("Bibliotecario inexistente!");
            });
            return ResponseEntity.ok(new BibliotecarioDTO(bibliotecario.getCdBibliotecario(),
                    bibliotecario.getNmBibliotecario(),
                    bibliotecario.getDtNascimento(),
                    bibliotecario.getTpSexo()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity save(@RequestBody BibliotecarioModel novoBiblitoecario) {
        try {
            novoBiblitoecario.setCdSenha(
                    SenhaCriptografada.hash(novoBiblitoecario.getCdSenha())
            );
            BibliotecarioModel salvo = repository.save(novoBiblitoecario);
            enviarEmail.enviarEmail(
                    salvo.getDsEmail(),
                    salvo.getNmBibliotecario()
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity update(@RequestBody BibliotecarioModel bibliotecario) {
        try {
            BibliotecarioModel bibliotecarioAtual = repository.findById(bibliotecario.getCdBibliotecario()).orElseThrow(() -> {
                throw new InvalidParameterException("Bibliotecario inexistente!");
            });

            bibliotecarioAtual.setNmBibliotecario(bibliotecario.getNmBibliotecario());
            bibliotecarioAtual.setCdSenha(SenhaCriptografada.hash(bibliotecario.getCdSenha()));
            bibliotecarioAtual.setTpSexo(bibliotecario.getTpSexo());
            bibliotecarioAtual.setDtNascimento(bibliotecario.getDtNascimento());
            repository.save(bibliotecarioAtual);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/{atributo}/{novo}")
    public ResponseEntity patch(@PathVariable Long id, @PathVariable String atributo, @PathVariable String novo) {

        BibliotecarioModel bibliotecario;
        try {
            bibliotecario = repository.findById(id).orElseThrow(() -> {
                throw new InvalidParameterException("Bibliotecario inexistente!");
            });
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        try {
            switch (atributo) {
                case "nome":
                    bibliotecario.setNmBibliotecario(novo);
                    break;
                case "senha":
                    bibliotecario.setCdSenha(SenhaCriptografada.hash(novo));
                    break;
                case "sexo":
                    bibliotecario.setTpSexo(novo.charAt(0));
                    break;
                case "email":
                    bibliotecario.setDsEmail(novo);
                    break;
                default:
                    throw new InvalidParameterException("Atributo inexistente!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        repository.save(bibliotecario);

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
