package com.prueba.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.training.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

}
