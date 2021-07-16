package com.giros.model;

import com.giros.service.ErrorsEnum;
import com.giros.service.HSMSecurityProviderException;

public class ResponseEncryptTemplate extends HeaderTemplate {
	/**
	 * Command Length
	 */
	private int commandLength;
	private String responseCode;
	private String outputChainingValue = "00000000000000";
	protected static final String hexNull = "\\x00";

	private String data;
	private int dataLength;

	/**
	 * Metodo encardado de formatear la respuesta recibida del HSM
	 * 
	 * @param commandResponse
	 *            cadena de datos cifrada recibida por el HSM
	 * @throws HSMSecurityProviderException 
	 */
	public void parseResponse(String commandResponse) throws HSMSecurityProviderException {

		try {
			String response = commandResponse.toUpperCase();
			super.setHeader(response);
			this.commandLength = Integer
					.parseInt(response.substring(8, 12), 16) * 2;
			this.responseCode = response.substring(18, 20);
			if (this.responseCode.equals("00")) {

				this.outputChainingValue = response.substring(20, 36);
				this.dataLength = Integer.parseInt(response.substring(36, 38),
						16) * 2;
				this.data = response.substring(38, response.trim().length());

			}
		} catch (Exception e) {
			HSMSecurityProviderException ex = new HSMSecurityProviderException(
					e);
			ex.setError(ErrorsEnum.HSM003);
			throw ex;
		}

	}

	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode
	 *            the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getOutputChainingValue() {
		return outputChainingValue;
	}

	public void setOutputChainingValue(String outputChainingValue) {
		this.outputChainingValue = outputChainingValue;
	}

}
