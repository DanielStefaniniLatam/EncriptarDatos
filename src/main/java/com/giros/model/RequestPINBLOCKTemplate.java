package com.giros.model;

import com.giros.service.ErrorsEnum;
import com.giros.service.HSMSecurityProviderException;

public class RequestPINBLOCKTemplate extends HeaderTemplate {

	/**
	 * PIN Length
	 */
	private String PINLength = "04";

	/**
	 * PIN size
	 */
	private String PINSize = "02";

	/**
	 * Clear PIN
	 */
	private String clearPIN;

	/**
	 * Credit Card Number
	 */
	private String creditCardNumber;

	/**
	 * Personal Account Number
	 */
	private String personalAccountNumber;

	/**
	 * PIN Protect Key Format
	 */
	private String PINProtectKeyFormat;

	/**
	 * PIN Protect Key Index
	 */
	private String PINProtectKeyIndex;

	/**
	 * Command Length
	 */
	private String commandLength;

	private String subCommand;

	/**
	 * Metodo encargado de formatear las variables en la estructura requerida
	 * por el comando EE0600 del HSM
	 * 
	 * @return comando a enviar al HSM
	 * @throws HSMSecurityProviderException 
	 */
	public String getCommand() throws HSMSecurityProviderException {

		try {

			super.setFunctionCode("EE0600");
			this.personalAccountNumber = this.creditCardNumber.substring(3, 15);
			this.subCommand = super.functionCode + super.functionModifier
					+ this.PINLength + this.PINSize + this.clearPIN
					+ this.personalAccountNumber + this.PINProtectKeyFormat
					+ this.PINProtectKeyIndex;
			this.commandLength = "0000"
					+ Integer.toHexString(subCommand.length() / 2);
			this.commandLength = this.commandLength.substring(
					this.commandLength.length() - 4,
					this.commandLength.length());
			return (super.getHeader() + this.commandLength + subCommand)
					.toUpperCase();

		} catch (Exception e) {
			HSMSecurityProviderException ex = new HSMSecurityProviderException(
					e);
			ex.setError(ErrorsEnum.HSM005);
			throw ex;
		}
	}

	/**
	 * @return the pINLength
	 */
	public String getPINLength() {
		return PINLength;
	}

	/**
	 * @param pINLength
	 *            the pINLength to set
	 */
	public void setPINLength(String pINLength) {
		PINLength = pINLength;
	}

	/**
	 * @return the pINSize
	 */
	public String getPINSize() {
		return PINSize;
	}

	/**
	 * @param pINSize
	 *            the pINSize to set
	 */
	public void setPINSize(String pINSize) {
		PINSize = pINSize;
	}

	/**
	 * @return the clearPIN
	 */
	public String getClearPIN() {
		return clearPIN;
	}

	/**
	 * @param clearPIN
	 *            the clearPIN to set
	 */
	public void setClearPIN(String clearPIN) {
		this.clearPIN = clearPIN;
	}

	/**
	 * @return the creditCardNumber
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	/**
	 * @param creditCardNumber
	 *            the creditCardNumber to set
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	/**
	 * @return the pINProtectKeyFormat
	 */
	public String getPINProtectKeyFormat() {
		return PINProtectKeyFormat;
	}

	/**
	 * @param pINProtectKeyFormat
	 *            the pINProtectKeyFormat to set
	 */
	public void setPINProtectKeyFormat(String pINProtectKeyFormat) {
		PINProtectKeyFormat = pINProtectKeyFormat;
	}

	/**
	 * @return the pINProtectKeyIndex
	 */
	public String getPINProtectKeyIndex() {
		return PINProtectKeyIndex;
	}

	/**
	 * @param pINProtectKeyIndex
	 *            the pINProtectKeyIndex to set
	 */
	public void setPINProtectKeyIndex(String pINProtectKeyIndex) {
		PINProtectKeyIndex = pINProtectKeyIndex;
	}

}