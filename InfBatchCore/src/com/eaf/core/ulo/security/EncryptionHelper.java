package com.eaf.core.ulo.security;

import java.io.UnsupportedEncodingException;

public class EncryptionHelper {
	
	protected byte[] getBytesArray(String str) throws UnsupportedEncodingException{
		return str.getBytes("UTF-8");
	}

}
