package com.eaf.core.ulo.security;

import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.core.ulo.security.exception.EncryptionException;
import com.eaf.core.ulo.security.hash.Hasher;
import com.eaf.core.ulo.security.hash.HashingFactory;

/*
 * Update file local_policy.jar to this file to path "C:\IBM\SDP\jdk\jre\lib\security" to support AES256 Encryption
 * 
 */

public class EncryptTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {			
			String plainText = "1234567890123456";
			String encryptedText = "";
			String decryptedText = "";
			String hashedText = "";
			
			//DIH
			Encryptor dihEnc = EncryptorFactory.getDIHEncryptor();
			encryptedText = dihEnc.encrypt(plainText);
			decryptedText = dihEnc.decrypt(encryptedText);
			
			System.out.println(">>>Encrypted DIH = "+encryptedText);
			System.out.println(">>>Decrypted DIH = "+decryptedText);
			
			//KmAlert
			Encryptor kmEnc = EncryptorFactory.getKmAlertEncryptor();
			encryptedText = kmEnc.encrypt(plainText);
			decryptedText = kmEnc.decrypt(encryptedText);
			
			System.out.println(">>>Encrypted KmAlert = "+encryptedText);
			System.out.println(">>>Decrypted KmAlert = "+decryptedText);
			
			//Hashing
			Hasher hash = HashingFactory.getSHA256Hasher();
			hashedText = hash.getHashCode(plainText);
			
			System.out.println(">>>hashedText SHA256 = "+hashedText);
			
			
		} catch(EncryptionException e){
			e.printStackTrace();
		}

	}

}
