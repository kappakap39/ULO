package com.eaf.core.ulo.security.encryptor;

import com.eaf.core.ulo.security.exception.EncryptionException;

public interface Encryptor {
	
	public String encrypt(String str) throws EncryptionException;
	public String decrypt(String encryptedStr) throws EncryptionException;

}
