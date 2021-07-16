package com.giros.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giros.configuration.BloqueoTarjetaEncrypt;
import com.giros.model.Usuario;
import com.giros.model.UsuarioResponse;

@RestController
@RequestMapping("/api/v1")
public class EncriptarDatosController {
	
	
	@PostMapping("/encriptar")
	public ResponseEntity<UsuarioResponse> postEncriptarDatos(@RequestBody Usuario usuario) throws Exception{
		
		UsuarioResponse usuarioResponse = new UsuarioResponse();
		
		String numeroDocumentoEncriptado = BloqueoTarjetaEncrypt.encrypt(usuario.getNumeroDocumento());
		usuarioResponse.setNumeroDocumento(numeroDocumentoEncriptado.toUpperCase());
		String numeroTarjetaEncriptado = BloqueoTarjetaEncrypt.encrypt(usuario.getNumeroTarjeta());
		usuarioResponse.setNumeroTarjeta(numeroTarjetaEncriptado.toUpperCase());
		
		return new ResponseEntity<UsuarioResponse>(usuarioResponse, HttpStatus.OK);
	}
	
}
