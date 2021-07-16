package com.giros.service;

public enum ErrorsEnum {

	HSM000("HSM000", "Error no controlado recibiendo comando HSM EE0801" ),
	HSM001("HSM001", "Error no controlado generando comando HSM EE0801" ),
	HSM002("HSM002", "Error no controlado generando HSM" ),
	
	HSM003("HSM003", "Error no controlado recibiendo comando HSM EE0800"),
	HSM004("HSM004", "Error no controlado generando comando HSM EE0800"),
	
	HSM005("HSM005", "Error no controlado generando comando HSM EE0600"),
	HSM006("HSM006", "Error no controlado recibiendo comando HSM EE0600"),
	
	HSM007("HSM007", "Error al conectarse al HSM host desconocido o inalcanzable"),
	HSM008("HSM008", "Error no controlado al conectarse al HSM"),
	HSM009("HSM009", "Error transmitiendo/recibiendo comando HSM");
	
	private String code;
	private String desc;
	

	ErrorsEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}