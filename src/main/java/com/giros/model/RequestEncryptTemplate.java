package com.giros.model;

import org.apache.commons.codec.binary.Hex;

import com.giros.service.ErrorsEnum;
import com.giros.service.HSMSecurityProviderException;
import com.giros.service.HSMUtil;

public class RequestEncryptTemplate extends HeaderTemplate {

	/**
	 * PIN Protect Key Format
	 */
	private String encryptProtectKeyFormat;

	/**
	 * PIN Protect Key Index
	 */
	private String encryptProtectKeyIndex;

	/**
	 * Command Length
	 */
	private String commandLength;

	/**
	 * Cipher Mode
	 */
	private String cipherMode;

	/**
	 * Input Chaining Value
	 */
	private String inputChainingValue = "00000000000000";

	/**
	 * the text to cipher
	 */
	private String cipherText;

	private String subCommand;

	private String dataLength;

	/**
	 * Metodo encargado de formatear las variables en la estructura requerida
	 * por el comando EE0800 del HSM
	 * 
	 * @return comando a enviar al HSM
	 * @throws HSMSecurityProviderException
	 */
	public String getCommand() throws HSMSecurityProviderException {

		try {
			super.setFunctionCode("EE0800");

			this.subCommand = super.functionCode + super.functionModifier
					+ this.encryptProtectKeyFormat
					+ this.encryptProtectKeyIndex + this.cipherMode
					+ this.inputChainingValue;

			String hexStringTC = Hex
					.encodeHexString(this.cipherText.getBytes()).toUpperCase();
			hexStringTC = HSMUtil.rpadMultipleOfEight(hexStringTC);

			this.dataLength = "0000"
					+ Integer.toHexString(hexStringTC.length() / 2);
			this.dataLength = this.dataLength.substring(
					this.dataLength.length() - 4, this.dataLength.length());

			this.subCommand = this.subCommand + this.dataLength + hexStringTC;

			this.commandLength = "0000"
					+ Integer.toHexString(this.subCommand.length() / 2);
			this.commandLength = this.commandLength.substring(
					this.commandLength.length() - 4,
					this.commandLength.length());

			return (super.getHeader() + this.commandLength + subCommand)
					.toUpperCase();
		} catch (Exception e) {
			HSMSecurityProviderException ex = new HSMSecurityProviderException(
					e);
			ex.setError(ErrorsEnum.HSM004);
			throw ex;
		}
	}

	public String getEncryptProtectKeyFormat() {
		return encryptProtectKeyFormat;
	}

	public void setEncryptProtectKeyFormat(String encryptProtectKeyFormat) {
		this.encryptProtectKeyFormat = encryptProtectKeyFormat;
	}

	public String getEncryptProtectKeyIndex() {
		return encryptProtectKeyIndex;
	}

	public void setEncryptProtectKeyIndex(String encryptProtectKeyIndex) {
		this.encryptProtectKeyIndex = encryptProtectKeyIndex;
	}

	public String getCipherMode() {
		return cipherMode;
	}

	public void setCipherMode(String cipherMode) {
		this.cipherMode = cipherMode;
	}

	public String getInputChainingValue() {
		return inputChainingValue;
	}

	public void setInputChainingValue(String inputChainingValue) {
		this.inputChainingValue = inputChainingValue;
	}

	public String getCipherText() {
		return cipherText;
	}

	public void setCipherText(String cipherText) {
		this.cipherText = cipherText;
	}

}
