package com.eaf.core.ulo.flp.security;

import java.io.UnsupportedEncodingException;

public class FLPEncryptionHelper{
	protected byte[] getBytesArray(String str) throws UnsupportedEncodingException{
		return str.getBytes("UTF-8");
	}
}
