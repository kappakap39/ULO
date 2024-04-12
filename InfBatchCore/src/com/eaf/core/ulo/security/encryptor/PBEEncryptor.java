package com.eaf.core.ulo.security.encryptor;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.eaf.core.ulo.security.EncryptionHelper;
import com.eaf.core.ulo.security.exception.EncryptionException;

public class PBEEncryptor extends EncryptionHelper implements Encryptor{
	
	private String pw;
    private String iv;
    private String salt;
    private int iteration = 32;
    private int keySize = 256;
    private String chipherMode = "AES/CBC/PKCS5Padding";
    
    /**
     * 
     * Default chipherMode = "AES/CBC/PKCS5Padding"
     * Default iteration = 32;
     * Default keySize = 256;
     * 
     */
    public PBEEncryptor(String password, String initialVector, String salt){
    	this.pw = password;
    	this.iv = initialVector;
    	this.salt = salt;
    }
    
    public PBEEncryptor(String password, String initialVector, String salt, String chipherMode, int iteration, int keySize){
    	this.pw = password;
    	this.iv = initialVector;
    	this.salt = salt;
    	this.chipherMode = chipherMode;
    	this.iteration = iteration;
    	this.keySize = keySize;    	
    }

	@Override
	public String encrypt(String plainText) throws EncryptionException {

		byte[] ivBytes= null;
		byte[] encryptedTextBytes = null;

		try {
			ivBytes = getBytesArray(iv);
			
			SecretKeySpec secret = getSecretKeySpec();
	        Cipher cipher = Cipher.getInstance(chipherMode);
	        cipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(ivBytes));
	        encryptedTextBytes = cipher.doFinal(getBytesArray(plainText));
	        
		} catch (Exception e) {
			throw new EncryptionException(e);
		}

        return Base64.encodeBase64String(encryptedTextBytes);
	}

	@Override
	public String decrypt(String encryptedText) throws EncryptionException {
		byte[] decryptedTextBytes = null;
		
		try{
			
	        byte[] ivBytes = getBytesArray(iv);
	        byte[] encryptedTextBytes = Base64.decodeBase64(encryptedText);

	        SecretKeySpec secret = getSecretKeySpec();

	        Cipher cipher = Cipher.getInstance(chipherMode);
	        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));

	        decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
		} catch (Exception e){
			throw new EncryptionException(e);
		}
		
        return new String(decryptedTextBytes);
	}
	
	private SecretKeySpec getSecretKeySpec() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException{

		byte[] saltBytes = getBytesArray(salt);
		
		// Derive the key, given password and salt.
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(pw.toCharArray(), saltBytes, iteration, keySize);
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        
        return secret;

	}

}
