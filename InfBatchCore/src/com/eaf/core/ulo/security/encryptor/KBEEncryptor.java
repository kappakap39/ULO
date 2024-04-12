package com.eaf.core.ulo.security.encryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.eaf.core.ulo.security.EncryptionHelper;
import com.eaf.core.ulo.security.exception.EncryptionException;

public class KBEEncryptor extends EncryptionHelper implements Encryptor {
	
	private String key;
    private String iv;
    private String chipherMode = "AES/CBC/PKCS5Padding";
    
    /**
     * 
     * Default chipherMode = "AES/CBC/PKCS5Padding"
     * 
     */
    public KBEEncryptor(String key, String initialVector){
    	this.key = key;
    	this.iv = initialVector;
    }
    
    public KBEEncryptor(String key, String initialVector, String chipherMode){
    	this.key = key;
    	this.iv = initialVector;
    	this.chipherMode = chipherMode;
    }

	@Override
	public String encrypt(String plainText) throws EncryptionException {
		
		try{
			byte[] ivBytes = iv.getBytes("UTF-8");
	
	        SecretKeySpec secret = new SecretKeySpec(new Base64().decode(key.getBytes()), "AES");
	
	        Cipher cipher = Cipher.getInstance(chipherMode);
	        cipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(ivBytes));
	
	        byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
	        return Base64.encodeBase64String(encryptedTextBytes);
		}catch(Exception e){
			throw new EncryptionException(e);
		}
	}

	@Override
	public String decrypt(String encryptedText)  throws EncryptionException {
		
		try{
			byte[] ivBytes = getBytesArray(iv);
	        byte[] encryptedTextBytes = Base64.decodeBase64(encryptedText);
	
	        SecretKeySpec secret = new SecretKeySpec(new Base64().decode(key.getBytes()), "AES");
	
	        // Decrypt the message, given derived key and initialization vector.
	        Cipher cipher = Cipher.getInstance(chipherMode);
	        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));
	
	        byte[] decryptedTextBytes = null;
	        decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
	        return new String(decryptedTextBytes);
		}catch(Exception e){
			throw new EncryptionException(e);
		}
	}

}
