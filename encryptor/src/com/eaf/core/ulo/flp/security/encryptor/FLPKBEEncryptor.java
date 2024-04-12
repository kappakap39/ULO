package com.eaf.core.ulo.flp.security.encryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.eaf.core.ulo.flp.security.FLPEncryptionHelper;
import com.eaf.core.ulo.flp.security.exception.FLPEncryptionException;

public class FLPKBEEncryptor extends FLPEncryptionHelper implements FLPEncryptor {
	
	private String key;
    private String iv;
    private String chipherMode = "AES/CBC/PKCS5Padding";
    
    /**
     * 
     * Default chipherMode = "AES/CBC/PKCS5Padding"
     * 
     */
    public FLPKBEEncryptor(String key, String initialVector){
    	this.key = key;
    	this.iv = initialVector;
    }
    
    public FLPKBEEncryptor(String key, String initialVector, String chipherMode){
    	this.key = key;
    	this.iv = initialVector;
    	this.chipherMode = chipherMode;
    }

	@Override
	public String encrypt(String plainText) throws FLPEncryptionException {
		
		try{
			byte[] ivBytes = iv.getBytes("UTF-8");
	
	        SecretKeySpec secret = new SecretKeySpec(new Base64().decode(key.getBytes()), "AES");
	
	        Cipher cipher = Cipher.getInstance(chipherMode);
	        cipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(ivBytes));
	
	        byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
	        return Base64.encodeBase64String(encryptedTextBytes);
		}catch(Exception e){
			throw new FLPEncryptionException(e);
		}
	}

	@Override
	public String decrypt(String encryptedText)  throws FLPEncryptionException {
		
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
			throw new FLPEncryptionException(e);
		}
	}

}
