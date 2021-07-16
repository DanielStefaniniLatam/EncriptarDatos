package com.giros.service;

import com.giros.model.RequestDecryptTemplate;
import com.giros.model.RequestEncryptTemplate;
import com.giros.model.RequestPINBLOCKTemplate;
import com.giros.model.ResponseDecryptTemplate;
import com.giros.model.ResponseEncryptTemplate;
import com.giros.model.ResponsePINBLOCKTemplate;

public class Test {
	
	/**
	 * @param args
	 * @throws HSMSecurityProviderException
	 */
	/*
	public static void main(String[] args) throws HSMSecurityProviderException {

		HSMServices hsm = new HSMServices("192.168.2.168", 2221);
		hsm.OpenConnection();

		RequestPINBLOCKTemplate template = new RequestPINBLOCKTemplate();
		template.setCreditCardNumber("5359550755466190");
		template.setClearPIN("6969");
		template.setPINProtectKeyFormat("02");
		template.setPINProtectKeyIndex("0001");
		System.out.println("<-- PINBLOCK --> ");
		System.out.println("TC --> " + template.getCreditCardNumber());
		System.out.println("PIN --> " + template.getClearPIN());
		System.out.println("Cmd PIN --> " + template.getCommand());
		String HSMResponse = hsm.executeCommand(template.getCommand());
		ResponsePINBLOCKTemplate respTemplate = new ResponsePINBLOCKTemplate();
		respTemplate.parseResponse(HSMResponse);
		System.out.println("Cmd PIN Response Code --> " + respTemplate.getResponseCode());
		System.out.println("Cmd PINBLOCK Response --> " + respTemplate.getPINBLOCK());

		RequestDecryptTemplate templateDencrypt = new RequestDecryptTemplate();
		templateDencrypt.setCipherMode("01");
		templateDencrypt.setEncryptProtectKeyFormat("02");
		templateDencrypt.setEncryptProtectKeyIndex("0001");
		templateDencrypt.setCipherText("2F24D00DCC69C69C8B7DA91685D0B11B");
		System.out.println("<-- DECRIPT --> ");
		System.out.println("Target --> " + templateDencrypt.getCipherText());
		System.out.println("Cmd Decript -->" + templateDencrypt.getCommand());
		HSMResponse = hsm.executeCommand(templateDencrypt.getCommand());
		ResponseDecryptTemplate respDecryptTemplate = new ResponseDecryptTemplate();
		respDecryptTemplate.parseResponse(HSMResponse);
		System.out.println("Cmd Decript Response Code --> " + respDecryptTemplate.getResponseCode());
		System.out.println("Cmd Decript Response data--> " + respDecryptTemplate.getData());
		System.out.println("Cmd Decript Response OCV--> "+ respDecryptTemplate.getOutputChainingValue());
		
		templateDencrypt.setCipherText("302D906B36BC8239566DFF8923B7267B");
		respDecryptTemplate.parseResponse(hsm.executeCommand(templateDencrypt.getCommand()));
		System.out.println("Cmd Decript Response data--> " + respDecryptTemplate.getData());
		
		templateDencrypt.setCipherText("55EF2D07501B279D");
		respDecryptTemplate.parseResponse(hsm.executeCommand(templateDencrypt.getCommand()));
		System.out.println("Cmd Decript Response data--> " + respDecryptTemplate.getData());
		
		System.out.println("<-- ENCRIPT --> ");
		RequestEncryptTemplate templateEncrypt = new RequestEncryptTemplate();
		templateEncrypt.setCipherMode("01");
		templateEncrypt.setEncryptProtectKeyFormat("02");
		templateEncrypt.setEncryptProtectKeyIndex("0101");
		templateEncrypt.setCipherText("1144149688");
		System.out.println("Target --> " + templateEncrypt.getCipherText());
		System.out.println("Cmd Encript --> " + templateEncrypt.getCommand());
		HSMResponse = hsm.executeCommand(templateEncrypt.getCommand());
		ResponseEncryptTemplate responseEncryptTemplate = new ResponseEncryptTemplate();
		responseEncryptTemplate.parseResponse(HSMResponse);
		System.out.println("Cmd Encript Response data--> " + responseEncryptTemplate.getData());

		hsm.closeConnection();

	}

*/
}
