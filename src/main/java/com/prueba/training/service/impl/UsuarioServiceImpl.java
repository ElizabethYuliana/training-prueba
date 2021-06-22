package com.prueba.training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.training.model.Usuario;
import com.prueba.training.repository.UsuarioRepository;
import com.prueba.training.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public Usuario registrarUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public Usuario buscarId(Integer id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	@Override
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}

	@Override
	public void eliminarUsuario(Integer id) {
		usuarioRepository.deleteById(id);
	}

}
