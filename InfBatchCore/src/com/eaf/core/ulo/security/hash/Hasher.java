package com.eaf.core.ulo.security.hash;

import com.eaf.core.ulo.security.exception.EncryptionException;


public interface Hasher {
	
	public String getHashCode(String plainText) throws EncryptionException;

}
