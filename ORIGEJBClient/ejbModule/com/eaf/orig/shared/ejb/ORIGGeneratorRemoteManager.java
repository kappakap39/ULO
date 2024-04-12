package com.eaf.orig.shared.ejb;

import javax.ejb.Remote;

@Remote
public interface ORIGGeneratorRemoteManager {
	public String generateUniqueIDByName(String name);
	public String generateUniqueIDByName(String name, int dbType);
}
