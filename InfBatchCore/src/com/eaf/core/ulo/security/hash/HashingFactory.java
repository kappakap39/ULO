package com.eaf.core.ulo.security.hash;


public class HashingFactory {
	
	public static Hasher getSHA256Hasher() {
		return new SHA256Hasher();
	}

}
