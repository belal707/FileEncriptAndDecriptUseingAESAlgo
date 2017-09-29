package com.criptography;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class AESCripto {
	private static String ALGO = "AES";
	public byte[] keyValue;

	public AESCripto(String keyValue) {
		this.keyValue = keyValue.getBytes();
	}

	public String encript(String data) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(data.getBytes());
		String encriptedValue = new BASE64Encoder().encode(encVal);
		return encriptedValue;
	}

	public String decript(String encriptedData) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decodedValue = new BASE64Decoder().decodeBuffer(encriptedData);
		byte[] encVal = c.doFinal(decodedValue);
		String str  = new String(encVal);
		return str;
	}

	public Key generateKey() {
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}
	public static void main(String args[]) throws Exception
	{
		AESCripto aes1 = new AESCripto("belal@1234343434");
		AESCripto aes2 = new AESCripto("belal@1234343434");
		String encodedData = aes1.encript("my name is belal ansari").toString();
		System.out.println("Encripted value is : "+encodedData);
		System.out.println("Decripted Value is : "+aes2.decript(encodedData));
	}
}
