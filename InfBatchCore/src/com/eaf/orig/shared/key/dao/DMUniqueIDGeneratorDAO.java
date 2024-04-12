package com.eaf.orig.shared.key.dao;

import com.eaf.orig.shared.key.dao.exception.DMUniqueIDGeneratorException;

public interface DMUniqueIDGeneratorDAO {
	public String getUniqueBySequence(String seqId) throws DMUniqueIDGeneratorException;
	public String getUniqueBySequence(String seqId,int dbType) throws DMUniqueIDGeneratorException;
}
