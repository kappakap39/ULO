package com.eaf.core.ulo.flp.security.encryptor;



public class FLPEncryptorFactory {	
	
	public static String FLP_PASSWORD="516f40ad791937e1ee692f7f2865440009ad6dd10d0de82ff78db122d11ff056";
	public static String FLP_IV="AAAAAAAAAAAAAAAA";
	public static String FLP_SALT="FLP-OPEN";
	public static String FLP_CHIPHER_MODE="AES/CBC/PKCS5Padding";
	public static int FLP_ITERATION = 32;
	public static int FLP_KEY_SIZE = 256;
	
	public static FLPEncryptor getPBEEncryptor(String password, String initialVector, String salt) {
		return new FLPPBEEncryptor(password, initialVector, salt);
	}
	
	public static FLPEncryptor getKBEEncryptor(String key, String initialVector) {
		return new FLPKBEEncryptor(key, initialVector);
	}
	
	public static FLPEncryptor getFLPEncryptor(){
		return new FLPPBEEncryptor(FLP_PASSWORD,FLP_IV,FLP_SALT);
	}
}
