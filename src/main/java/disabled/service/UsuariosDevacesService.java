package org.example.projetojava.usuarios.service;

import org.example.projetojava.usuarios.model.UsuariosDevaces;
import org.example.projetojava.usuarios.repository.UsuariosDevacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosDevacesService {

    @Autowired
    private UsuariosDevacesRepository repository;

    public List<UsuariosDevaces> listarTodos() {
        return repository.findAll();
    }

    public Optional<UsuariosDevaces> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    public UsuariosDevaces salvar(UsuariosDevaces usuario) {
        return repository.save(usuario);
    }

    public void deletar(Integer id) {
        repository.deleteById(id);
    }
}
