package com.prueba.training.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.training.model.Usuario;
import com.prueba.training.service.EncryptService;
import com.prueba.training.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class UsuarioController {

	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EncryptService encryptService;
	
	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> listarUsuarios(){
		return new ResponseEntity<List<Usuario>>(usuarioService.listar(), HttpStatus.OK);
	}
	
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> buscarId(@PathVariable Integer id){
		Usuario usuario = usuarioService.buscarId(id);
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@PostMapping("/usuarios")
	public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario, BindingResult result){
		
		
		Map<String, Object> response =  new HashMap<String, Object>();
		
		if (result.hasErrors()) {
			List<String> errors =  result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField()  + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
					
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		String hashPass =  encryptService.encryptPassword(usuario.getContrasena());
		usuario.setContrasena(hashPass);
		
		return new ResponseEntity<Usuario>(usuarioService.registrarUsuario(usuario), HttpStatus.CREATED);
	}
	
	
	@PutMapping("/usuarios/{id}")
	public ResponseEntity<?> actualizar(@Valid @RequestBody Usuario usuario, @PathVariable Integer id, BindingResult result){
		
		Map<String, Object> response =  new HashMap<String, Object>();

		if (result.hasErrors()) {
			List<String> errors =  result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField()  + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
					
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		Usuario usuarioActual =  usuarioService.buscarId(id);
		usuarioActual.setNombres(usuario.getNombres());
		usuarioActual.setApellidos(usuario.getApellidos());
		usuarioActual.setCelular(usuario.getCelular());
		String hashPass =  encryptService.encryptPassword(usuario.getContrasena());
		usuarioActual.setContrasena(hashPass);
		
		return new ResponseEntity<Usuario>(usuarioService.registrarUsuario(usuarioActual), HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer id){
		Map<String, Object> respuesta =  new HashMap<String, Object>();
		usuarioService.eliminarUsuario(id);
		respuesta.put("mensaje", "La eliminación ha sido existosa");
		return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
	}
	
}
