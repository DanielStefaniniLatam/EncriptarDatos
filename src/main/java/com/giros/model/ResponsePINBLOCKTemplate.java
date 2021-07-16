package com.giros.model;

import com.giros.service.ErrorsEnum;
import com.giros.service.HSMSecurityProviderException;

public class ResponsePINBLOCKTemplate extends HeaderTemplate {
	/**
	 * Command Length
	 */
	private int commandLength;
	private String responseCode;

	/**
	 * PINBLOCK en formato ISO-FORMATO-0
	 */
	private String PINBLOCK;

	/**
	 * Metodo encargado de formtear la repsuesta recibida del HSM
	 * 
	 * @param commandResponse
	 * @throws HSMSecurityProviderException
	 */
	public void parseResponse(String commandResponse)
			throws HSMSecurityProviderException {
		try {
			String response = commandResponse.toUpperCase();
			super.setHeader(response);
			this.commandLength = Integer
					.parseInt(response.substring(8, 12), 16) * 2;
			this.responseCode = response.substring(18, 20);
			if (this.responseCode.equals("00")) {
				this.PINBLOCK = response
						.substring(20, response.trim().length());
			}

		} catch (Exception e) {
			HSMSecurityProviderException ex = new HSMSecurityProviderException(
					e);
			ex.setError(ErrorsEnum.HSM006);
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

	/**
	 * @return the pINBLOCK
	 */
	public String getPINBLOCK() {
		return PINBLOCK;
	}

	/**
	 * @param pINBLOCK
	 *            the pINBLOCK to set
	 */
	public void setPINBLOCK(String pINBLOCK) {
		PINBLOCK = pINBLOCK;
	}

}
