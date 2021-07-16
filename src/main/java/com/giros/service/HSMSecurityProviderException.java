package com.giros.service;

public class HSMSecurityProviderException extends Exception {

	private static final long serialVersionUID = 1L;

	private ErrorsEnum error;

	public HSMSecurityProviderException() {
		super();
	}

	public HSMSecurityProviderException(Throwable ex) {
		super(ex);
	}

	public HSMSecurityProviderException(ErrorsEnum error, Throwable ex) {
		super(ex);
		this.setError(error);
	}

	public HSMSecurityProviderException(ErrorsEnum error) {
		this.setError(error);
	}

	public ErrorsEnum getError() {
		return error;
	}

	public void setError(ErrorsEnum error) {
		this.error = error;
	}

}
