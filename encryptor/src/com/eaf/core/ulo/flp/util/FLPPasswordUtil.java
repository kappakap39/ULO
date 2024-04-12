package com.eaf.core.ulo.flp.util;

import com.eaf.core.ulo.flp.security.encryptor.FLPEncryptorFactory;

public class FLPPasswordUtil {
	public static String encrypt(String str){
		try{
			if(null==str)return "";
			return FLPEncryptorFactory.getFLPEncryptor().encrypt(str);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public static String decrypt(String encryptedStr){
		try{
			if(null==encryptedStr)return "";
			return FLPEncryptorFactory.getFLPEncryptor().decrypt(encryptedStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
