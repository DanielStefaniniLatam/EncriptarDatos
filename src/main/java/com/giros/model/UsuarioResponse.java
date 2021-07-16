package com.giros.model;

public class UsuarioResponse {
	
	private String numeroDocumento;
	
	private String numeroTarjeta;

	public UsuarioResponse() {

	}

	public UsuarioResponse(String numeroDocumento, String numeroTarjeta) {
		super();
		this.numeroDocumento = numeroDocumento;
		this.numeroTarjeta = numeroTarjeta;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}
	
}
