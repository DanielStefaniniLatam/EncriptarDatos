package com.giros.configuration;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class BloqueoTarjetaEncrypt {
	
	private static String KEY_ENCRIPT = "HG58YZ3CR9";
	
	 public static String encrypt(String message) throws Exception {
	        final MessageDigest md = MessageDigest.getInstance("md5");
	        final byte[] digestOfPassword = md.digest(KEY_ENCRIPT.getBytes("utf-8"));
	        final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
	        for (int j = 0, k = 16; j < 8;) {
	            keyBytes[k++] = keyBytes[j++];
	        }

	        final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
	        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
	        final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

	        final byte[] plainTextBytes = message.getBytes("utf-8");
	        final byte[] cipherText = cipher.doFinal(plainTextBytes);
	        //final String encodedCipherText = new sun.misc.BASE64Encoder().encode(cipherText);

	        return encodeHexString(cipherText);
	    }
	 
	 public static String encodeHexString(byte[] byteArray) {
		    StringBuffer hexStringBuffer = new StringBuffer();
		    for (int i = 0; i < byteArray.length; i++) {
		        hexStringBuffer.append(byteToHex(byteArray[i]));
		    }
		    return hexStringBuffer.toString();
		}
	 
	 public static String byteToHex(byte num) {
		    char[] hexDigits = new char[2];
		    hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
		    hexDigits[1] = Character.forDigit((num & 0xF), 16);
		    return new String(hexDigits);
		}

}
