package com.eaf.core.ulo.security.encryptor;

import com.eaf.core.ulo.common.cache.InfBatchProperty;

public class EncryptorFactory {
	
	private static final String PROP_PASSWORD = "_PASSWORD";
	private static final String PROP_IV = "_IV";
	private static final String PROP_SALT = "_SALT";
	private static final String PROP_CHIPHER_MODE = "_CHIPHER_MODE";
	private static final String PROP_ITERATION = "_ITERATION";
	private static final String PROP_KEY_SIZE = "_KEY_SIZE";
	private static final String PROP_KEY = "_KEY";
	
	public static final String SYSTEM_DIH = "DIH";
	public static final String SYSTEM_KMALERT = "KM";
	public static final String SYSTEM_APP = "APP";
	
	public static Encryptor getPBEEncryptor(String password, String initialVector, String salt) {
		return new PBEEncryptor(password, initialVector, salt);
	}
	
	public static Encryptor getKBEEncryptor(String key, String initialVector) {
		return new KBEEncryptor(key, initialVector);
	}
	
	public static Encryptor getEncryptor(String system){
		Encryptor encryptor = null;
		
		switch (system) {
		case SYSTEM_DIH:
			encryptor = new PBEEncryptor(InfBatchProperty.getEncryptionConfig(system + EncryptorFactory.PROP_PASSWORD), 
					InfBatchProperty.getEncryptionConfig(system + EncryptorFactory.PROP_IV), 
					InfBatchProperty.getEncryptionConfig(system + EncryptorFactory.PROP_SALT),
					InfBatchProperty.getEncryptionConfig(system + EncryptorFactory.PROP_CHIPHER_MODE),
					Integer.parseInt(InfBatchProperty.getEncryptionConfig(system + EncryptorFactory.PROP_ITERATION)), 
					Integer.parseInt(InfBatchProperty.getEncryptionConfig(system + EncryptorFactory.PROP_KEY_SIZE)));
			break;
		case SYSTEM_APP:
			encryptor = new PBEEncryptor(InfBatchProperty.getEncryptionConfig(system + EncryptorFactory.PROP_PASSWORD), 
					InfBatchProperty.getEncryptionConfig(system + EncryptorFactory.PROP_IV), 
					InfBatchProperty.getEncryptionConfig(system + EncryptorFactory.PROP_SALT),
					InfBatchProperty.getEncryptionConfig(system + EncryptorFactory.PROP_CHIPHER_MODE),
					Integer.parseInt(InfBatchProperty.getEncryptionConfig(system + EncryptorFactory.PROP_ITERATION)), 
					Integer.parseInt(InfBatchProperty.getEncryptionConfig(system + EncryptorFactory.PROP_KEY_SIZE)));
			break;
		case SYSTEM_KMALERT:
			encryptor = new KBEEncryptor(InfBatchProperty.getEncryptionConfig(system + EncryptorFactory.PROP_KEY), 
					InfBatchProperty.getEncryptionConfig(system + EncryptorFactory.PROP_IV),
					InfBatchProperty.getEncryptionConfig(system + EncryptorFactory.PROP_CHIPHER_MODE));
			break;

		default:
			break;
		}
		return encryptor;
	}
	
	public static Encryptor getDIHEncryptor() {
		return new PBEEncryptor(InfBatchProperty.getEncryptionConfig(SYSTEM_DIH + EncryptorFactory.PROP_PASSWORD), 
				InfBatchProperty.getEncryptionConfig(SYSTEM_DIH + EncryptorFactory.PROP_IV), 
				InfBatchProperty.getEncryptionConfig(SYSTEM_DIH + EncryptorFactory.PROP_SALT));
	}
	public static Encryptor getAPPEncryptor() {
		return new PBEEncryptor(InfBatchProperty.getEncryptionConfig(SYSTEM_APP + EncryptorFactory.PROP_PASSWORD), 
				InfBatchProperty.getEncryptionConfig(SYSTEM_APP + EncryptorFactory.PROP_IV), 
				InfBatchProperty.getEncryptionConfig(SYSTEM_APP + EncryptorFactory.PROP_SALT));
	}
	public static Encryptor getKmAlertEncryptor() {
		return new KBEEncryptor(InfBatchProperty.getEncryptionConfig(SYSTEM_KMALERT + EncryptorFactory.PROP_KEY), 
				InfBatchProperty.getEncryptionConfig(SYSTEM_KMALERT + EncryptorFactory.PROP_IV));
	}

}
