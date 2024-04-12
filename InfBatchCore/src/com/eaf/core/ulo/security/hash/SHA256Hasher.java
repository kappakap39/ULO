package com.eaf.core.ulo.security.hash;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;

import com.eaf.core.ulo.security.EncryptionHelper;
import com.eaf.core.ulo.security.exception.EncryptionException;

public class SHA256Hasher extends EncryptionHelper implements Hasher{
	
	public String getHashCode(String plainText) throws EncryptionException{
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
			md.update(getBytesArray(plainText));
			byte[] digest = md.digest();
			
			return Hex.encodeHexString(digest);
		} catch (Exception e) {
			throw new EncryptionException(e);
		}
	}

}
