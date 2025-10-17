package org.example.projetojava.controller;

import org.example.projetojava.usuarios.model.UsuariosDevaces;
import org.example.projetojava.usuarios.service.UsuariosDevacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosDevacesController {

    @Autowired
    private UsuariosDevacesService service;

    // GET - Retorna todos os usuários (senha não aparece)
    @GetMapping
    public List<UsuariosDevaces> listarTodos() {
        return service.listarTodos();
    }

    // GET - Retorna um usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuariosDevaces> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - Cria um novo usuário
    @PostMapping
    public ResponseEntity<UsuariosDevaces> criar(@RequestBody UsuariosDevaces usuario) {
        UsuariosDevaces criado = service.salvar(usuario);
        return ResponseEntity.ok(criado);
    }

    // PUT - Atualiza um usuário existente
    @PutMapping("/{id}")
    public ResponseEntity<UsuariosDevaces> atualizar(@PathVariable Integer id, @RequestBody UsuariosDevaces usuarioAtualizado) {
        return service.buscarPorId(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setNome(usuarioAtualizado.getNome());
                    usuarioExistente.setEmail(usuarioAtualizado.getEmail());
                    usuarioExistente.setSenha(usuarioAtualizado.getSenha());
                    usuarioExistente.setCargo(usuarioAtualizado.getCargo());
                    usuarioExistente.setAtivo(usuarioAtualizado.getAtivo());
                    UsuariosDevaces atualizado = service.salvar(usuarioExistente);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - Remove um usuário pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        return service.buscarPorId(id)
                .map(usuario -> {
                    service.deletar(id);
                    return ResponseEntity.ok("Usuário deletado com sucesso!");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
