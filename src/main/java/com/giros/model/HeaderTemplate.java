package com.giros.model;

import com.giros.service.ErrorsEnum;
import com.giros.service.HSMSecurityProviderException;

/**
 * Clase que encapsula los componentes genericos del encabezado de un comando al
 * HSM <br>
 * esta clase por defecto asume:<br>
 * <ol>
 * <li>El inicio de la cabecera = 01
 * <li>La version = 01
 * <li>La secuencia del comando = 0000
 * </ol>
 * 
 * @author julian.coronado@arkangroupco.com
 * @see 
 *      "http://192.168.2.109:4989/610-012761-003/LunaEFT2.0.6/START_HERE.htm#Network
 *      i n g Guide/SafeNet Message Header.htm"
 */
public class HeaderTemplate {

	/**
	 * Start of Header
	 */
	private String SOH = "01";
	/**
	 * Version number
	 */
	private String versionNumber = "01";
	/**
	 * Sequence number
	 */
	private String sequenceNumber = "0000";

	/**
	 * Function code
	 */
	protected String functionCode;

	/**
	 * Function modifier
	 */
	protected String functionModifier = "00";

	/**
	 * Metodo encargado de recibir una cadena de caractreres (mensaje de
	 * respuesta del HSM) y extraer el componente correspondiente a la cabecera
	 * del comando
	 * 
	 * @param subCommand
	 *            respuesta del HSM
	 * @throws HSMSecurityProviderException
	 * 
	 */
	public void setHeader(String subCommand)
			throws HSMSecurityProviderException {

		try {
			this.SOH = subCommand.substring(0, 2);
			this.versionNumber = subCommand.substring(2, 4);
			this.sequenceNumber = subCommand.substring(4, 8);
			this.functionCode = subCommand.substring(12, 18);
			this.functionModifier = null;
		} catch (Exception e) {
			HSMSecurityProviderException ex = new HSMSecurityProviderException(
					e);
			ex.setError(ErrorsEnum.HSM002);
			throw ex;
		}
	}

	/**
	 * Metodo encargado de generar la cabecera del comando a ser enviado al HSM
	 * 
	 * @return String cabecera del comando
	 */
	public String getHeader() {
		return this.SOH + this.versionNumber + this.sequenceNumber;
	}

	/**
	 * @return the sOH
	 */
	public String getSOH() {
		return SOH;
	}

	/**
	 * @param sOH
	 *            the sOH to set
	 */
	public void setSOH(String sOH) {
		SOH = sOH;
	}

	/**
	 * @return the versionNumber
	 */
	public String getVersionNumber() {
		return versionNumber;
	}

	/**
	 * @param versionNumber
	 *            the versionNumber to set
	 */
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	/**
	 * @return the sequenceNumber
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber
	 *            the sequenceNumber to set
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return the functionCode
	 */
	public String getFunctionCode() {
		return functionCode;
	}

	/**
	 * @param functionCode
	 *            the functionCode to set
	 */
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	/**
	 * @return the functionModifier
	 */
	public String getFunctionModifier() {
		return functionModifier;
	}

	/**
	 * @param functionModifier
	 *            the functionModifier to set
	 */
	public void setFunctionModifier(String functionModifier) {
		this.functionModifier = functionModifier;
	}

}