package com.prueba.training.service;

import java.util.List;

import com.prueba.training.model.Usuario;

public interface UsuarioService {

	public Usuario registrarUsuario(Usuario usuario);
	
	public Usuario buscarId(Integer id);
	
	public List<Usuario> listar();
	
	public void eliminarUsuario(Integer id);
}
