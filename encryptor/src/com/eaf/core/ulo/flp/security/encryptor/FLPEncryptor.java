package com.eaf.core.ulo.flp.security.encryptor;

import com.eaf.core.ulo.flp.security.exception.FLPEncryptionException;

public interface FLPEncryptor {
	public String encrypt(String str) throws FLPEncryptionException;
	public String decrypt(String encryptedStr) throws FLPEncryptionException;
}
