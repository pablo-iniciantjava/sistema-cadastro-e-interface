package org.example.projetojava.usuarios.repository;

import org.example.projetojava.usuarios.model.UsuariosDevaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosDevacesRepository extends JpaRepository<UsuariosDevaces, Integer> {}
