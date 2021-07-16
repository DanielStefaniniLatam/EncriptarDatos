package com.giros.service;

import com.giros.model.RequestDecryptTemplate;
import com.giros.model.RequestEncryptTemplate;
import com.giros.model.RequestPINBLOCKTemplate;
import com.giros.model.ResponseDecryptTemplate;
import com.giros.model.ResponseEncryptTemplate;
import com.giros.model.ResponsePINBLOCKTemplate;

public class HSMSecurityProvider {

	public static void main(String[] args) {
		System.out.println("<data>");
		String test = HSMSecurityProvider.passwordManager(
				"useraplgf",
				"6Xh7*pLb", 
				"5359550755466190", 
				"1212", 
				"6969", 
				"29360303",
				"02", 
				"0101", 
				"02", 
				"0101", 
				"192.168.2.168", 
				"2221");
		System.out.println(test);
/*
		test = HSMSecurityProvider.passwordManager("useraplgf", "6Xh7*pLb",
				"5359550755466190", "6969", null, "1144149688", "02", "0101",
				"02", "0101", "192.168.2.168", 2221);
		System.out.println(test);

		test = HSMSecurityProvider.genericOperationsManager(
		        "useraplgf",
				"6Xh7*pLb", 
				"5359550755466190", 
				"02", 
				"0101", 
				"192.168.2.168",
				2221);
		System.out.println(test);

		test = HSMSecurityProvider.cipherText("useraplgf", "01", "02", "0101",
				"192.168.2.168", 2221);
		System.out.println(test);
*/
		test = HSMSecurityProvider.decipherText(
				"E200BDC42F49B126FD3FACFAAC1AAFFB", "01", "02", "0101",
				"192.168.2.168", "2221");
		System.out.println(test);
/*
		test = HSMSecurityProvider.decipherText("55EF2D07501B279D", "01", "02",
				"0101", "192.168.2.168", 2221);
		System.out.println(test);
*/
		System.out.println("</data>");
	}

	/**
	 * Metodo encargado de realizar todas las operaciones de seguridad sobre los
	 * datos para peticiones de cambio o reinicio de clave de tarjeta de credito
	 * LA14MasterCard
	 * 
	 * @param user usuario del WS a cifrar
	 * @param password contraseña del usuario de WS a cifrar
	 * @param creditCard numero de tarjeta de credito 16 digitos
	 * @param newPin nuevo numero de pin 4 digitos
	 * @param oldPin anterior numero de pin 4 digitos, este parametro es opcional y se debe enviar nulo si no se requiere
	 * @param customerId numero de identificacion del cliente
	 * @param ppkformat formato de llave de generacion de PINBLOCK
	 * @param ppkIndex indice de la llave de generacion de PINBLOCK
	 * @param epkformat formato de la llave de cifrado de datos
	 * @param epkIndex indice de la llave de cifrado de datos
	 * @param host direccion IP o nonmbre de host del HSM
	 * @param port puesto de escucha del HSM
	 * @return String en formato xml con los datos requeridos o el error en su defecto
	 */
	public static String passwordManager(String user, String password,
			String creditCard, String newPin, String oldPin, String customerId,
			String ppkformat, String ppkIndex, String epkformat,
			String epkIndex, String host, String port) {
		String newPINBLOCK;
		String oldPINBLOCK = "";
		String customerIdCiphered;
		String userCiphered;
		String passwdCiphered;
		String creditCardCiphered;
		String response = "";
		HSMServices hsm = new HSMServices(host, Integer.valueOf(port).intValue());

		try {
			hsm.OpenConnection();

			RequestPINBLOCKTemplate template = new RequestPINBLOCKTemplate();
			ResponsePINBLOCKTemplate respTemplate = new ResponsePINBLOCKTemplate();
			RequestEncryptTemplate templateEncrypt = new RequestEncryptTemplate();
			ResponseEncryptTemplate responseEncryptTemplate = new ResponseEncryptTemplate();

			// se formatea el nuevo pin
			template.setCreditCardNumber(creditCard);
			template.setClearPIN(newPin);
			template.setPINProtectKeyFormat(ppkformat);
			template.setPINProtectKeyIndex(ppkIndex);
			respTemplate
					.parseResponse(hsm.executeCommand(template.getCommand()));
			newPINBLOCK = respTemplate.getPINBLOCK();

			// se formatea el anterior pin
			if (oldPin != null) {
				template.setClearPIN(oldPin);
				respTemplate.parseResponse(hsm.executeCommand(template
						.getCommand()));
				oldPINBLOCK = respTemplate.getPINBLOCK();
			}

			// con los pines formateados se procede a cifrar todos los datos
			templateEncrypt.setCipherMode("01"); // CBC
			templateEncrypt.setEncryptProtectKeyFormat(epkformat);
			templateEncrypt.setEncryptProtectKeyIndex(epkIndex);

			// identificacion del cliente
			templateEncrypt.setCipherText(customerId);
			responseEncryptTemplate.parseResponse(hsm
					.executeCommand(templateEncrypt.getCommand()));
			customerIdCiphered = responseEncryptTemplate.getData();

			response = "<customerIdCiphered>" + customerIdCiphered
					+ "</customerIdCiphered>";

			// tarjeta de credito
			templateEncrypt.setCipherText(creditCard);
			responseEncryptTemplate.parseResponse(hsm
					.executeCommand(templateEncrypt.getCommand()));
			creditCardCiphered = responseEncryptTemplate.getData();

			response += "<creditCardCiphered>" + creditCardCiphered.substring(0,creditCardCiphered.length()-16)
					+ "</creditCardCiphered>";

			// usuario
			templateEncrypt.setCipherText(user);
			responseEncryptTemplate.parseResponse(hsm
					.executeCommand(templateEncrypt.getCommand()));
			userCiphered = responseEncryptTemplate.getData();

			response += "<userCiphered>" + userCiphered + "</userCiphered>";

			// password
			templateEncrypt.setCipherText(password);
			responseEncryptTemplate.parseResponse(hsm
					.executeCommand(templateEncrypt.getCommand()));
			passwdCiphered = responseEncryptTemplate.getData();

			response += "<passwdCiphered>" + passwdCiphered
					+ "</passwdCiphered>";

			// newPINBLOCK
			templateEncrypt.setCipherText(newPINBLOCK);
			responseEncryptTemplate.parseResponse(hsm
					.executeCommand(templateEncrypt.getCommand()));
			newPINBLOCK = responseEncryptTemplate.getData();

			response += "<newPINBLOCK>" + newPINBLOCK.substring(0,newPINBLOCK.length()-16) + "</newPINBLOCK>";

			// oldPINBLOCK
			if (oldPin != null) {
				templateEncrypt.setCipherText(oldPINBLOCK);
				responseEncryptTemplate.parseResponse(hsm
						.executeCommand(templateEncrypt.getCommand()));
				oldPINBLOCK = responseEncryptTemplate.getData();

				response += "<oldPINBLOCK>" + oldPINBLOCK.substring(0,oldPINBLOCK.length()-16) + "</oldPINBLOCK>";
			}
			// se finaliza la coneccion con el HSM
			hsm.closeConnection();

			return "<securityResponse><security>" + response
					+ "</security></securityResponse>";
		} catch (HSMSecurityProviderException e) {
			return HSMSecurityProvider.setErrorXml(e.getError());
		} catch (Exception e) {
			return HSMSecurityProvider.setGeneralErrorXml(e);
		} finally {
			// se finaliza la coneccion con el HSM
			hsm.closeConnection();
		}
	}

	/**
	 * Metodo encargado de realizar las operaciones de seguridad sobre los datos de peticiones generales a los WS de tarjeta de credito LA14MasterCard 
	 * @param user usuario del WS a cifrar
	 * @param password contraseña del usuario de WS a cifrar
	 * @param extraData dato a cifrar
	 * @param epkformat formato de la llave de cifrado de datos
	 * @param epkIndex indice de la llave de cifrado de datos
	 * @param host direccion IP o nonmbre de host del HSM
	 * @param port puesto de escucha del HSM
	 * @return String en formato xml con los datos requeridos o el error en su defecto
	 */
	public static String genericOperationsManager(String user, String password,
			String extraData, String epkformat, String epkIndex, String host,
			String port) {

		String extraDataCiphered;
		String userCiphered;
		String passwdCiphered;
		String response = "";
		HSMServices hsm = new HSMServices(host,  Integer.valueOf(port).intValue());
		try {
			hsm.OpenConnection();
			RequestEncryptTemplate templateEncrypt = new RequestEncryptTemplate();
			ResponseEncryptTemplate responseEncryptTemplate = new ResponseEncryptTemplate();
			templateEncrypt.setCipherMode("01"); // CBC
			templateEncrypt.setEncryptProtectKeyFormat(epkformat);
			templateEncrypt.setEncryptProtectKeyIndex(epkIndex);

			// identificacion del cliente
			templateEncrypt.setCipherText(extraData);
			responseEncryptTemplate.parseResponse(hsm
					.executeCommand(templateEncrypt.getCommand()));
			extraDataCiphered = responseEncryptTemplate.getData();

			response = "<extraDataCiphered>" + extraDataCiphered
					+ "</extraDataCiphered>";

			// usuario
			templateEncrypt.setCipherText(user);
			responseEncryptTemplate.parseResponse(hsm
					.executeCommand(templateEncrypt.getCommand()));
			userCiphered = responseEncryptTemplate.getData();

			response += "<userCiphered>" + userCiphered + "</userCiphered>";

			// password
			templateEncrypt.setCipherText(password);
			responseEncryptTemplate.parseResponse(hsm
					.executeCommand(templateEncrypt.getCommand()));
			passwdCiphered = responseEncryptTemplate.getData();

			response += "<passwdCiphered>" + passwdCiphered
					+ "</passwdCiphered>";
			return "<securityResponse><security>" + response
					+ "</security></securityResponse>";

		} catch (HSMSecurityProviderException e) {
			return HSMSecurityProvider.setErrorXml(e.getError());

		} catch (Exception e) {
			return HSMSecurityProvider.setGeneralErrorXml(e);
		} finally {
			// se finaliza la coneccion con el HSM
			hsm.closeConnection();
		}
	}
	
	public static String genericOperationsManagerTC(String user, String password,
			String creditCard, String epkformat, String epkIndex, String host,
			String port) {

		String extraDataCiphered;
		String userCiphered;
		String passwdCiphered;
		String response = "";
		HSMServices hsm = new HSMServices(host,  Integer.valueOf(port).intValue());
		try {
			hsm.OpenConnection();
			RequestEncryptTemplate templateEncrypt = new RequestEncryptTemplate();
			ResponseEncryptTemplate responseEncryptTemplate = new ResponseEncryptTemplate();
			templateEncrypt.setCipherMode("01"); // CBC
			templateEncrypt.setEncryptProtectKeyFormat(epkformat);
			templateEncrypt.setEncryptProtectKeyIndex(epkIndex);

			// identificacion del cliente
			templateEncrypt.setCipherText(creditCard);
			responseEncryptTemplate.parseResponse(hsm
					.executeCommand(templateEncrypt.getCommand()));
			extraDataCiphered = responseEncryptTemplate.getData();

			response = "<creditCardCiphered>" + extraDataCiphered.substring(0,extraDataCiphered.length()-16)
					+ "</creditCardCiphered>";

			// usuario
			templateEncrypt.setCipherText(user);
			responseEncryptTemplate.parseResponse(hsm
					.executeCommand(templateEncrypt.getCommand()));
			userCiphered = responseEncryptTemplate.getData();

			response += "<userCiphered>" + userCiphered + "</userCiphered>";

			// password
			templateEncrypt.setCipherText(password);
			responseEncryptTemplate.parseResponse(hsm
					.executeCommand(templateEncrypt.getCommand()));
			passwdCiphered = responseEncryptTemplate.getData();

			response += "<passwdCiphered>" + passwdCiphered
					+ "</passwdCiphered>";
			return "<securityResponse><security>" + response
					+ "</security></securityResponse>";

		} catch (HSMSecurityProviderException e) {
			return HSMSecurityProvider.setErrorXml(e.getError());

		} catch (Exception e) {
			return HSMSecurityProvider.setGeneralErrorXml(e);
		} finally {
			// se finaliza la coneccion con el HSM
			hsm.closeConnection();
		}
	}

	/**
	 * Metodo encargado de decifrar datos usando el HSM y la llave proporcionada
	 * @param cipheredData dato cfrado a decifrar
	 * @param cipherMode Specifies the mode of operation for the encipherment: <br> <ol><li>00 - Electronic Code Book (ECB) <li>01 - Cipher Block Chaining (CBC)</ol>
	 * @param epkformat formato de la llave de decifrado de datos
	 * @param epkIndex indice de la llave de decifrado de datos
	 * @param host direccion IP o nonmbre de host del HSM
	 * @param port puesto de escucha del HSM
	 * @return String en formato xml con los datos requeridos o el error en su defecto
	 */
	public static String decipherText(String cipheredData, String cipherMode,
			String epkFormat, String epkIndex, String host, String port) {
		String clearData;
		String response = "";
		HSMServices hsm = new HSMServices(host,  Integer.valueOf(port).intValue());

		try {
			hsm.OpenConnection();
			RequestDecryptTemplate template = new RequestDecryptTemplate();
			template.setCipherMode(cipherMode);
			template.setEncryptProtectKeyFormat(epkFormat);
			template.setEncryptProtectKeyIndex(epkIndex);
			template.setCipherText(cipheredData);
			ResponseDecryptTemplate responseEncryptTemplate = new ResponseDecryptTemplate();
			responseEncryptTemplate.parseResponse(hsm.executeCommand(template
					.getCommand()));
			clearData = responseEncryptTemplate.getData();
			response += "<clearData>" + clearData + "</clearData>";
			return "<securityResponse><security>" + response
					+ "</security></securityResponse>";

		} catch (HSMSecurityProviderException e) {
			return HSMSecurityProvider.setErrorXml(e.getError());

		} catch (Exception e) {
			return HSMSecurityProvider.setGeneralErrorXml(e);
		} finally {
			// se finaliza la coneccion con el HSM
			hsm.closeConnection();
		}
	}

	/**
	 * Metodo encargado de cifrar datos usando el HSM y la llave proporcionada
	 * @param clearData dato en claro a cifrar
	 * @param cipherMode Specifies the mode of operation for the encipherment: <br> <ol><li>00 - Electronic Code Book (ECB) <li>01 - Cipher Block Chaining (CBC)</ol>
	 * @param dpkFormat formtao de la llave de cifrado de datos
	 * @param dpkIndex indice de la llave de decifrado de datos
	 * @param host direccion IP o nonmbre de host del HSM
	 * @param port puesto de escucha del HSM
	 * @return String en formato xml con los datos requeridos o el error en su defecto
	 */
	public static String cipherText(String clearData, String cipherMode,
			String dpkFormat, String dpkIndex, String host, String port) {

		String cipheredText;
		String response = "";
		HSMServices hsm = new HSMServices(host,  Integer.valueOf(port).intValue());

		try {
			hsm.OpenConnection();
			RequestEncryptTemplate templateEncrypt = new RequestEncryptTemplate();
			templateEncrypt.setCipherMode(cipherMode);
			templateEncrypt.setEncryptProtectKeyFormat(dpkFormat);
			templateEncrypt.setEncryptProtectKeyIndex(dpkIndex);
			templateEncrypt.setCipherText(clearData);
			ResponseEncryptTemplate responseEncryptTemplate = new ResponseEncryptTemplate();
			responseEncryptTemplate.parseResponse(hsm
					.executeCommand(templateEncrypt.getCommand()));
			cipheredText = responseEncryptTemplate.getData();

			response += "<cipheredText>" + cipheredText + "</cipheredText>";
			return "<securityResponse><security>" + response
					+ "</security></securityResponse>";

		} catch (HSMSecurityProviderException e) {
			return HSMSecurityProvider.setErrorXml(e.getError());

		} catch (Exception e) {
			return HSMSecurityProvider.setGeneralErrorXml(e);
		} finally {
			// se finaliza la coneccion con el HSM
			hsm.closeConnection();
		}

	}
	
	
	public static String cipherCreditCard(String creditCard, String dpkFormat, String dpkIndex, String host, String port) {

		String cipheredText;
		String response = "";
		HSMServices hsm = new HSMServices(host,  Integer.valueOf(port).intValue());

		try {
			hsm.OpenConnection();
			RequestEncryptTemplate templateEncrypt = new RequestEncryptTemplate();
			templateEncrypt.setCipherMode("01");
			templateEncrypt.setEncryptProtectKeyFormat(dpkFormat);
			templateEncrypt.setEncryptProtectKeyIndex(dpkIndex);
			templateEncrypt.setCipherText(creditCard);
			ResponseEncryptTemplate responseEncryptTemplate = new ResponseEncryptTemplate();
			responseEncryptTemplate.parseResponse(hsm
					.executeCommand(templateEncrypt.getCommand()));
			cipheredText = responseEncryptTemplate.getData();
			
			response += "<creditCardCiphered>" + cipheredText.substring(0,cipheredText.length()-16) + "</creditCardCiphered>";
			return "<securityResponse><security>" + response
					+ "</security></securityResponse>";

		} catch (HSMSecurityProviderException e) {
			return HSMSecurityProvider.setErrorXml(e.getError());

		} catch (Exception e) {
			return HSMSecurityProvider.setGeneralErrorXml(e);
		} finally {
			// se finaliza la coneccion con el HSM
			hsm.closeConnection();
		}

	}

	private static String setErrorXml(ErrorsEnum enumErr) {
		String error = "<codError>"
				+ enumErr.getCode()
				+ "</codError><codTypeError>ERROR</codTypeError><codTypeSeverity>BUSINESS</codTypeSeverity><descError>"
				+ enumErr.getDesc() + "</descError>";
		return "<securityResponse><fault>" + error
				+ "</fault></securityResponse>";

	}

	private static String setGeneralErrorXml(Exception enumErr) {
		String error = "<codError>HMSMNG</codError><codTypeError>ERROR</codTypeError><codTypeSeverity>BUSINESS</codTypeSeverity><descError>"
				+ enumErr.getMessage() + "</descError>";
		return "<securityResponse><fault>" + error
				+ "</fault></securityResponse>";

	}

}
