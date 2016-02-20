package com.davidscompany.mainGroup.Sophia;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.wicket.util.crypt.Base64;

public class Sha256 implements Serializable {

	private static final long serialVersionUID = 1L;

	public String hash(String password) throws NoSuchAlgorithmException {
	    MessageDigest sha256 = MessageDigest.getInstance("SHA-256");        
	    byte[] passBytes = password.getBytes();
	    byte[] passHash = sha256.digest(passBytes);
	    byte[] passByte64 = Base64.encodeBase64(passHash);
	    String EncryptedPass = new String(passByte64);
	    return EncryptedPass;
	}
	/*
	public static void main(String[] args) {
		Sha256Encrypting sha = new Sha256Encrypting();
		String value = "";
		try {
			value = sha.hash("5678");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Chyba pri kryptovani" + e);
		}
		System.out.println(value);
	}
	*/
	
	// enconde by Base64
	//	byte[]   bytesEncoded = Base64.encodeBase64(test);
	//	System.out.println("ecncoded value is " + new String(bytesEncoded ));

}
